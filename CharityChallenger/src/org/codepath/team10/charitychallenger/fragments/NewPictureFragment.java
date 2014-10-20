package org.codepath.team10.charitychallenger.fragments;

import org.codepath.team10.charitychallenger.R;
import org.codepath.team10.charitychallenger.activities.NewPictureActivity;
import org.codepath.team10.charitychallenger.helper.ParseProxyObject;
import org.codepath.team10.charitychallenger.models.Challenge;
import org.codepath.team10.charitychallenger.models.Invitation;
import org.codepath.team10.charitychallenger.models.Picture;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.SaveCallback;

public class NewPictureFragment extends Fragment {

	private ImageButton photoButton;
	private Button saveButton;
	private Button cancelButton;
	private TextView pictureName;
	private Spinner pictureRating;
	private ParseImageView picturePreview;
	private Challenge challenge;
	private Invitation invitation;

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

		saveButton = ((Button) v.findViewById(R.id.save_button));
		saveButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				// TODO: create a listener
				Picture picture = ((NewPictureActivity) getActivity()).getCurrentPicture();

				picture.setTitle(pictureName.getText().toString());
				//picture.setAuthor(ParseUser.getCurrentUser());
				//picture.setRating(pictureRating.getSelectedItem().toString());
				picture.saveInBackground(new SaveCallback() {

					@Override
					public void done(ParseException e) {
						if (e == null) {
							Intent data = new Intent();
							data.putExtra("invitation", invitation);
							data.putExtra("challenge", challenge);
							getActivity().setResult(Activity.RESULT_OK, data);
							getActivity().finish();
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
