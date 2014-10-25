package org.codepath.team10.charitychallenger.adapters;

import java.util.List;

import org.codepath.team10.charitychallenger.R;
import org.codepath.team10.charitychallenger.models.Challenge;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.parse.ParseObject;
import com.parse.ParseQueryAdapter;

public class ChallengesViewAdapter extends ParseQueryAdapter<Challenge> {

	public static class ViewHolder{
		ImageView ivCharityChallenge;
		TextView tvCharityName;
		TextView tvTargetAmount;
		TextView tvRaised;
		
	}
	public ChallengesViewAdapter(Context context, final String tableName) {
//		super(context, new ParseQueryAdapter.QueryFactory<Challenge>() {
//			
//			public ParseQuery<Challenge> create() {
//				Log.d("Constructor: ", tableName);
//				ParseQuery<Challenge> query = ParseQuery.getQuery(Challenge.class);
//				query.whereExists("raised");
//				query.whereExists("target");
//				return query;
//			}
//		});
		
		//ParseObject po = null;
		super(context, tableName);
		


	}

	// Customize the layout by overriding getItemView
	@Override
	public View getItemView(Challenge challenge, View v, ViewGroup parent) {
		
		ViewHolder viewHolder = null;
		if (v == null) {
			viewHolder = new ViewHolder();
			v = View.inflate(getContext(), R.layout.item_challenge, null);
			viewHolder.ivCharityChallenge = (ImageView)v.findViewById(R.id.ivCharityChallenge);
			viewHolder.tvCharityName = (TextView) v.findViewById(R.id.tvCharityName);
			viewHolder.tvTargetAmount = (TextView) v.findViewById(R.id.tvTargetAmount);
			viewHolder.tvRaised = (TextView) v.findViewById(R.id.tvRaised);
			v.setTag(viewHolder);
			
		} else {
			viewHolder = (ViewHolder)v.getTag();
		}

		super.getItemView(challenge, v, parent);

		viewHolder.tvCharityName.setText(challenge.getString("name"));
		viewHolder.tvTargetAmount.setText("$ " + challenge.getInt("target"));
		viewHolder.tvRaised.setText("$ " + challenge.getInt("raised"));
		List<String> url = challenge.getList("challenge_pic_urls");
		ImageLoader.getInstance().displayImage(url.get(0), viewHolder.ivCharityChallenge);
		
		return v;
	}

}
