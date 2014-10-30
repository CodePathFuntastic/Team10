package org.codepath.team10.charitychallenger.fragments;

import java.io.ByteArrayOutputStream;
import java.io.File;

import org.codepath.team10.charitychallenger.CharityChallengerApplication;
import org.codepath.team10.charitychallenger.R;
import org.codepath.team10.charitychallenger.activities.BaseActivity;
import org.codepath.team10.charitychallenger.activities.ChallengesHomeSwipeActivity;
import org.codepath.team10.charitychallenger.activities.FunActivity;
import org.codepath.team10.charitychallenger.activities.NewPictureActivity;
import org.codepath.team10.charitychallenger.clients.PutPhotoAsyncTask;
import org.codepath.team10.charitychallenger.models.Challenge;
import org.codepath.team10.charitychallenger.models.Invitation;
import org.codepath.team10.charitychallenger.models.InvitationStatusEnum;
import org.codepath.team10.charitychallenger.models.Picture;
import org.codepath.team10.charitychallenger.utils.InvitationMessageUtils;
import org.json.JSONArray;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParsePush;
import com.parse.SaveCallback;
import com.parse.SendCallback;

public class NewPictureFragment extends Fragment {

	private ImageButton photoButton;
	private Button saveButton;
	private Button cancelButton;

	private TextView pictureName;

	private ImageView ivPreview;
	private Challenge challenge;
	private Invitation invitation;
	public final String APP_TAG = "MyCustomApp";
	public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
	public String photoFileName = "photo.jpg";
	private Picture picture;
	private ParseFile photoFile;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle SavedInstanceState) {
		Bundle arguments = getArguments();
		
		challenge = (Challenge)arguments.get("challenge");
		invitation = (Invitation)arguments.get("invitation");

		View v = inflater.inflate(R.layout.fragment_new_picture, parent, false);

		pictureName = ((EditText) v.findViewById(R.id.picture_name));
		
		picture = ((NewPictureActivity) getActivity()).getCurrentPicture();

		photoButton = ((ImageButton) v.findViewById(R.id.photo_button));
		photoButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
			    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			    intent.putExtra(MediaStore.EXTRA_OUTPUT, getPhotoFileUri(photoFileName));
			    // Start the image capture intent to take photo
			    startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);				
			}
		});
		
		saveButton = ((Button) v.findViewById(R.id.save_button));
		saveButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				// TODO: create a listener
				final Picture picture = ((NewPictureActivity) getActivity()).getCurrentPicture();
				if(picture==null || picture.getPhotoFile() == null){
					Toast.makeText(
							getActivity().getApplicationContext(),
							"Please take the picture first",
							Toast.LENGTH_SHORT).show();
					Log.e("Error: ", "Please take the picture first");
					return;
				}
				
				picture.setTitle(pictureName.getText().toString());
				picture.saveInBackground(new SaveCallback() {
					@Override
					public void done(ParseException e) {
						if (e == null) {
							refreshFromParse();
						} else {
							Toast.makeText(
									getActivity().getApplicationContext(),
									"Error saving: " + e.getMessage(),
									Toast.LENGTH_SHORT).show();
						}
					}
				});
			}
		});

		cancelButton = ((Button) v.findViewById(R.id.cancel_button));
		cancelButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				getActivity().setResult(Activity.RESULT_CANCELED);
				getActivity().finish();
			}
		});

		ivPreview = (ImageView) v.findViewById(R.id.picture_preview_image);
		ivPreview.setVisibility(View.INVISIBLE);
		return v;
	}
	
	private void addPhotoToPictureAndReturn(ParseFile photoFile) {
		((NewPictureActivity) getActivity()).getCurrentPicture().setPhotoFile(
				photoFile);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
	         Uri takenPhotoUri = getPhotoFileUri(photoFileName);
	         // by this point we have the camera photo on disk
	         Bitmap takenImage = BitmapFactory.decodeFile(takenPhotoUri.getPath());
	         
	 		// Override Android default landscape orientation and save portrait
	 		Matrix matrix = new Matrix();
	 		matrix.postRotate(90);
	 		Bitmap rotatedScaledpictureImage = Bitmap.createBitmap(takenImage, 0,
	 				0, takenImage.getWidth(), takenImage.getHeight(),
	 				matrix, true);
	         
	         ivPreview.setImageBitmap(takenImage);  
	         ivPreview.setVisibility(View.VISIBLE);

	         ByteArrayOutputStream stream = new ByteArrayOutputStream();
	         takenImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
	         byte[] byteArray = stream.toByteArray();
	         
	         
	 		//photoFile = new ParseFile("picture_photo.jpg", byteArray);
	 		//addPhotoToPictureAndReturn(photoFile);
	         saveScaledPhoto(byteArray);
	    }
	}
	
	private void saveScaledPhoto(byte[] data) {

		// Resize photo from camera byte array
		Bitmap pictureImage = BitmapFactory.decodeByteArray(data, 0, data.length);
		Bitmap pictureImageScaled = Bitmap.createScaledBitmap(pictureImage, 200, 200
				* pictureImage.getHeight() / pictureImage.getWidth(), false);

		// Override Android default landscape orientation and save portrait
		Matrix matrix = new Matrix();
		matrix.postRotate(90);
		Bitmap rotatedScaledpictureImage = Bitmap.createBitmap(pictureImageScaled, 0,
				0, pictureImageScaled.getWidth(), pictureImageScaled.getHeight(),
				matrix, true);

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		rotatedScaledpictureImage.compress(Bitmap.CompressFormat.JPEG, 100, bos);

		byte[] scaledData = bos.toByteArray();

		// Save the scaled image to Parse
		photoFile = new ParseFile("picture_photo.jpg", scaledData);
		addPhotoToPictureAndReturn(photoFile);
//		photoFile.saveInBackground(new SaveCallback() {
//
//			public void done(ParseException e) {
//				if (e != null) {
//					Toast.makeText(getActivity(),
//							"Error saving: " + e.getMessage(),
//							Toast.LENGTH_LONG).show();
//				} else {
//					addPhotoToPictureAndReturn(photoFile);
//				}
//			}
//		});
	}
	
	// Returns the Uri for a photo stored on disk given the fileName
	public Uri getPhotoFileUri(String fileName) {
	    // Get safe storage directory for photos
	    File mediaStorageDir = new File(
	        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), APP_TAG);

	    if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
	        Log.d(APP_TAG, "failed to create directory");
	    }

	    return Uri.fromFile(new File(mediaStorageDir.getPath() + File.separator + fileName));
	}
	
	public void refreshFromParse(){
		
		// refresh the invitation and challenge
		invitation.fetchInBackground( new GetCallback<Invitation>(){

			@Override
			public void done(final Invitation qInvitation,
					ParseException e) {
				
				if( e == null ){
					// refresh the challenge
					challenge.fetchInBackground( new GetCallback<Challenge>() {
						@Override
						public void done(Challenge c,
								ParseException e) {
							if( e == null ){
								saveInParse(qInvitation);
							}else{
								// handle exception
							}
						}
					});
					
				}else{
					// handle the error
				}
			}
		} );
	}
	
	public void saveInParse(Invitation qInvitation){
		
		// at this point the picture should have the URL.
		ParseFile photo = picture.getPhotoFile();
		String photourl = photo.getUrl();
		JSONArray photos = qInvitation.getJSONArray("photos");
		JSONArray newPhotos;
		if (photos == null) {
			photos = new JSONArray();
		} 
		photos.put(photourl);
		//qInvitation.add("photos", photos);
		//qInvitation.setStatus("status",InvitationStatusEnum.PIC_SENT.ordinal());
		invitation.addPhoto(photourl);
		invitation.setStatus(InvitationStatusEnum.PIC_SENT.ordinal());
		
        setPictureForInvitation( qInvitation);
        
 //       sendPushNotification();

		
//		// update the invitation and challenge
		qInvitation.saveInBackground( new SaveCallback() {
			@Override
			public void done(ParseException e) {
				if( e == null ){
					
					int oi = challenge.getOpenInvitations();
					oi--;
					int ci = challenge.getClosedInvitations();
					ci++;
					
					challenge.setOpenInvitation(oi);
					challenge.setClosedInvitations(ci);
					
					challenge.saveInBackground( new SaveCallback() {
						
						@Override
						public void done(ParseException e) {
							if( e == null ){
								sendPushNotification();
							}else{
								// TODO : handle exception
							}
						}
					});
				}else{
					// TODO: handle exception
				}
			}
		});
	}
	
	private void setPictureForInvitation(Invitation qInvitation) {
		PutPhotoAsyncTask async= new PutPhotoAsyncTask( qInvitation, new PutPhotoAsyncTask.InvitationUpdateCallBack() {
			@Override
			public void onUpdate() {
				sendPushNotification();
			}
		});

		//async.setInvitation(qInvitation);
		String[] ids = { invitation.getObjectId() };
		async.execute(ids);	
	}

	private void sendPushNotification() {
		
		// send push notification
		ParsePush push = new ParsePush();
		
		String msg = InvitationMessageUtils.generateInvitationCompleteMsg(invitation, challenge);
		push.setMessage(msg);
		
		push.setChannel(CharityChallengerApplication.INVITATION_COMPLETE);
		push.sendInBackground( new SendCallback(){

			@Override
			public void done(ParseException e) {
				if( e == null ){
					
				}else{
					// handle error
					Log.e(BaseActivity.LOG_TAG, "unable to update", e);
				}
			}
		});
		
		// start to fun activity
		Intent intent = new Intent( getActivity(), ChallengesHomeSwipeActivity.class);
		intent.putExtra("invitation", invitation);
		intent.putExtra("challenge", challenge);
		
		startActivity(intent);
	}
}
