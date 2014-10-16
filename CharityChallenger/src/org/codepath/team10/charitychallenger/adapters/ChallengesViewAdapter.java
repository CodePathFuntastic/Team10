package org.codepath.team10.charitychallenger.adapters;

import org.codepath.team10.charitychallenger.R;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

public class ChallengesViewAdapter extends ParseQueryAdapter<ParseObject> {

	public static class ViewHolder{
		ParseImageView ivCharityChallenge;
		TextView tvCharityName;
		TextView tvTargetAmount;
		TextView tvRaised;
		
	}
	public ChallengesViewAdapter(Context context, final String tableName) {
		super(context, new ParseQueryAdapter.QueryFactory<ParseObject>() {
			
			public ParseQuery<ParseObject> create() {
				Log.d("Constructor: ", tableName);
				ParseQuery query = new ParseQuery(tableName);
				query.whereExists("raised");
				query.whereExists("target");
				return query;
			}
		});
	}

	// Customize the layout by overriding getItemView
	@Override
	public View getItemView(ParseObject challenge, View v, ViewGroup parent) {
		
		ViewHolder viewHolder = null;
		if (v == null) {
			viewHolder = new ViewHolder();
			v = View.inflate(getContext(), R.layout.item_challenge, null);
			viewHolder.ivCharityChallenge = (ParseImageView) v.findViewById(R.id.ivCharityChallenge);
			viewHolder.tvCharityName = (TextView) v.findViewById(R.id.tvCharityName);
			viewHolder.tvTargetAmount = (TextView) v.findViewById(R.id.tvTargetAmount);
			viewHolder.tvRaised = (TextView) v.findViewById(R.id.tvRaised);
			v.setTag(viewHolder);
			
		} else {
			viewHolder = (ViewHolder)v.getTag();
		}

		super.getItemView(challenge, v, parent);

		//ParseImageView challangeImage = (ParseImageView) viewHolder.ivCharityChallenge;
//		ParseFile photoFile = challenge.getParseFile("");
//		if (photoFile != null) {
//			viewHolder.ivCharityChallenge.setParseFile(photoFile);
//			viewHolder.ivCharityChallenge.loadInBackground();//new GetDataCallback() {
////				@Override
////				public void done(byte[] data, ParseException e) {
////					// nothing to do
////				}
////			});
//		}
		
		viewHolder.tvCharityName.setText(challenge.getString("name"));
		viewHolder.tvTargetAmount.setText("$ " + challenge.getInt("target"));
		viewHolder.tvRaised.setText("$ " + challenge.getInt("raised"));
		return v;
	}

}
