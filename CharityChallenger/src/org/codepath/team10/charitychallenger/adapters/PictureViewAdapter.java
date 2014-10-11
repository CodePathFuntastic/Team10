package org.codepath.team10.charitychallenger.adapters;

import java.util.Arrays;

import org.codepath.team10.charitychallenger.R;
import org.codepath.team10.charitychallenger.models.Picture;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

public class PictureViewAdapter extends ParseQueryAdapter<Picture> {

	public PictureViewAdapter(Context context) {
		super(context, new ParseQueryAdapter.QueryFactory<Picture>() {
			public ParseQuery<Picture> create() {
				ParseQuery query = new ParseQuery("Picture");
				query.whereContainedIn("rating", Arrays.asList("5", "4"));
				query.orderByDescending("rating");
				return query;
			}
		});
	}

	@Override
	public View getItemView(Picture picture, View v, ViewGroup parent) {

		if (v == null) {
			v = View.inflate(getContext(), R.layout.activity_picture_list, null);
		}

		super.getItemView(picture, v, parent);

		ParseImageView pictureImage = (ParseImageView) v.findViewById(R.id.icon);
		ParseFile photoFile = picture.getParseFile("photo");
		if (photoFile != null) {
			pictureImage.setParseFile(photoFile);
			pictureImage.loadInBackground(new GetDataCallback() {
				@Override
				public void done(byte[] data, ParseException e) {
					// nothing to do
				}
			});
		}

		TextView titleTextView = (TextView) v.findViewById(R.id.text1);
		titleTextView.setText(picture.getTitle());
		TextView ratingTextView = (TextView) v
				.findViewById(R.id.favorite_picture_rating);
		ratingTextView.setText(picture.getRating());
		return v;
	}

}
