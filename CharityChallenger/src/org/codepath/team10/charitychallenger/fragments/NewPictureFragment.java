package org.codepath.team10.charitychallenger.fragments;

import org.codepath.team10.charitychallenger.CharityChallengerApplication;
import org.codepath.team10.charitychallenger.R;
import org.codepath.team10.charitychallenger.activities.DonateActivity;
import org.codepath.team10.charitychallenger.activities.FunActivity;
import org.codepath.team10.charitychallenger.activities.NewPictureActivity;
import org.codepath.team10.charitychallenger.models.Challenge;
import org.codepath.team10.charitychallenger.models.Invitation;
import org.codepath.team10.charitychallenger.models.InvitationStatusEnum;
import org.codepath.team10.charitychallenger.models.Picture;
import org.codepath.team10.charitychallenger.utils.InvitationMessageUtils;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParsePush;
import com.parse.SaveCallback;
import com.parse.SendCallback;

public class NewPictureFragment extends Fragment {

	private ImageButton photoButton;
	private Button saveButton;
	private Button cancelButton;
	private Button donateButton;
	private TextView pictureName;
	private Spinner pictureRating;
	private ParseImageView picturePreview;
	private Challenge challenge;
	private Invitation invitation;
	
	private Picture picture;

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

		pictureRating = ((Spinner) v.findViewById(R.id.rating_spinner));
		
		picture = ((NewPictureActivity) getActivity()).getCurrentPicture();
		
		ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter
				.createFromResource(getActivity(), R.array.ratings_array,
						android.R.layout.simple_spinner_dropdown_item);
		
		pictureRating.setAdapter(spinnerAdapter);

		photoButton = ((ImageButton) v.findViewById(R.id.photo_button));
		photoButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				InputMethodManager imm = (InputMethodManager) getActivity()
												.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(pictureName.getWindowToken(), 0);
				startCamera();
			}
		});

//		donateButton = ((Button) v.findViewById(R.id.donate_button));
//		donateButton.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view){
//				Intent intent = new Intent(getActivity(), DonateActivity.class);
//				intent.putExtra("invitation", invitation);
//				intent.putExtra("challenge", challenge);
//				startActivityForResult(intent, 120);
//			}
//		});
		
		saveButton = ((Button) v.findViewById(R.id.save_button));
		saveButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				// TODO: create a listener
				final Picture picture = ((NewPictureActivity) getActivity()).getCurrentPicture();
				if(picture==null || picture.getPhotoFile() == null){
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

		picturePreview = (ParseImageView) v.findViewById(R.id.picture_preview_image);
		picturePreview.setVisibility(View.INVISIBLE);

		return v;
	}
	
	public void refreshFromParse(){
		
		// refresh the invitation and challenge
		invitation.fetchInBackground( new GetCallback<Invitation>(){

			@Override
			public void done(Invitation invitation,
					ParseException e) {
				
				if( e == null ){
					
					// refresh the challenge
					challenge.fetchInBackground( new GetCallback<Challenge>() {
						@Override
						public void done(Challenge c,
								ParseException e) {
							if( e == null ){
								saveInParse();
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
	
	public void saveInParse(){
		
		// at this point the picture should have the URL.
		ParseFile photo = picture.getPhotoFile();
		String photourl = photo.getUrl();
		
		invitation.addPhoto(photourl);
		invitation.setStatus(InvitationStatusEnum.PIC_SENT.ordinal());
		
		// update the invitation and challenge
		invitation.saveInBackground( new SaveCallback() {
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
				}
			}
		});
		
		// start to fun activity
//		Intent data = new Intent();
//		data.putExtra("invitation", invitation);
//		data.putExtra("challenge", challenge);
//		data.putExtra("newPhotoUrl", picture.getPhotoFile().getUrl());
//
//		
//		getActivity().setResult(Activity.RESULT_OK, data);
//		getActivity().finish();
		
		Intent intent = new Intent( getActivity(), FunActivity.class);
		intent.putExtra("invitation", invitation);
		intent.putExtra("challenge", challenge);
		
		startActivity(intent);
	}

	public void startCamera() {
		Fragment cameraFragment = new CameraFragment();
		FragmentTransaction transaction = getActivity().getFragmentManager()
				.beginTransaction();
		transaction.replace(R.id.fragmentContainer, cameraFragment);
		transaction.addToBackStack("NewPictureFragment");
		transaction.commit();
	}

	@Override
	public void onResume() {
		super.onResume();
		ParseFile photoFile = ((NewPictureActivity) getActivity())
				.getCurrentPicture().getPhotoFile();
		if (photoFile != null) {
			picturePreview.setParseFile(photoFile);
			picturePreview.loadInBackground(new GetDataCallback() {
				@Override
				public void done(byte[] data, ParseException e) {
					picturePreview.setVisibility(View.VISIBLE);
				}
			});
		}
	}
}
