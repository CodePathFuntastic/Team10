package org.codepath.team10.charitychallenger.adapters;

import org.codepath.team10.charitychallenger.R;
import org.codepath.team10.charitychallenger.models.Challenge;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

public class ChallengesViewAdapter extends ParseQueryAdapter<Challenge> {

	public static class ViewHolder{
		ImageView ivCharityChallenge;
		TextView tvChallengeTitle;
		TextView tvTargetAmount;
		TextView tvAmountRaised;
		
	}
	public ChallengesViewAdapter(Context context) {
		super(context, new ParseQueryAdapter.QueryFactory<Challenge>() {
			public ParseQuery<Challenge> create() {
				ParseQuery query = new ParseQuery("Challenge");
				return query;
			}
		});
	}

	@Override
	public View getItemView(Challenge challenge, View v, ViewGroup parent) {
		
		ViewHolder viewHolder = null;
		if (v == null) {
			viewHolder = new ViewHolder();
			v = View.inflate(getContext(), R.layout.item_challenge, null);
			viewHolder.ivCharityChallenge = (ImageView) v.findViewById(R.id.ivCharityChallenge);
			viewHolder.tvChallengeTitle = (TextView) v.findViewById(R.id.tvCharityChallengeName);
			viewHolder.tvTargetAmount = (TextView) v.findViewById(R.id.tvTargetAmount);
			viewHolder.tvAmountRaised = (TextView) v.findViewById(R.id.tvAmountRaised);
			v.setTag(viewHolder);
			
		} else {
			viewHolder = (ViewHolder)v.getTag();
		}

		super.getItemView(challenge, v, parent);

		ParseImageView pictureImage = (ParseImageView) v.findViewById(R.id.icon);
		ParseFile photoFile = challenge.getParseFile("photo");
		if (photoFile != null) {
			pictureImage.setParseFile(photoFile);
			pictureImage.loadInBackground(new GetDataCallback() {
				@Override
				public void done(byte[] data, ParseException e) {
					// nothing to do
				}
			});
		}
		
		viewHolder.ivCharityChallenge.setImageResource(0);
		viewHolder.tvChallengeTitle.setText(challenge.getName());
		viewHolder.tvTargetAmount.setText("$2000.00");
		viewHolder.tvAmountRaised.setText("$$625.00");
		return v;
	}

}
