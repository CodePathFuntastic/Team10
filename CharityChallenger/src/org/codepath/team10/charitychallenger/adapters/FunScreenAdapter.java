package org.codepath.team10.charitychallenger.adapters;

import java.util.List;

import org.codepath.team10.charitychallenger.R;
import org.codepath.team10.charitychallenger.models.Challenge;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

public class FunScreenAdapter extends ParseQueryAdapter<Challenge> {

	public static class ViewHolder{
		ImageView ivImage;
	}
	
	public FunScreenAdapter(Context context) {
		super(context, new ParseQueryAdapter.QueryFactory<Challenge>() {
			
			public ParseQuery<Challenge> create() {
				ParseQuery<Challenge> query = ParseQuery.getQuery(Challenge.class);
				query.whereExists("challenge_pic_urls");
				return query;
			}
		});
	}

	// Customize the layout by overriding getItemView
	@Override
	public View getItemView(Challenge challenge, View v, ViewGroup parent) {
		
		ViewHolder viewHolder = null;
		if (v == null) {
			viewHolder = new ViewHolder();
			v = View.inflate(getContext(), R.layout.item_fun, null);
			viewHolder.ivImage = (ImageView)v.findViewById(R.id.ivImage);
			v.setTag(viewHolder);
			
		} else {
			viewHolder = (ViewHolder)v.getTag();
		}

		super.getItemView(challenge, v, parent);
		List<String> url = challenge.getList("challenge_pic_urls");
		ImageLoader.getInstance().displayImage(url.get(0), viewHolder.ivImage);
		
		return v;
	}

}

