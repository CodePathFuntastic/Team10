package org.codepath.team10.charitychallenger.adapters;

import java.util.List;

import org.codepath.team10.charitychallenger.R;
import org.codepath.team10.charitychallenger.models.AAUser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class InviteFriendsAdapter extends ArrayAdapter<AAUser> {
	public InviteFriendsAdapter(Context context, List<AAUser> objects) {
		super(context, R.layout.item_invite, objects);
	}
	
	public static class ViewHolder{
		private ImageView	ivFriend;
		private TextView	tvFriendName;
		public TextView		tvFriendLocation;
	}

	@Override
	public View getView(final int position, View convertView, final ViewGroup parent) {
		// Get the data from position.
		AAUser user = getItem(position);
		
		ViewHolder viewHolder =  null;
		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_invite, parent, false);
			viewHolder.ivFriend = (ImageView)convertView.findViewById(R.id.ivFriend);
			viewHolder.tvFriendName = (TextView)convertView.findViewById(R.id.tvFriendName);
			viewHolder.tvFriendLocation = (TextView)convertView.findViewById(R.id.tvFriendLocation);
			
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}	
		
		updateTweetVew(viewHolder, user);
		
		return convertView;
	}

	private void updateTweetVew(ViewHolder viewHolder, AAUser user) {
		if(viewHolder.ivFriend != null){
			//viewHolder.ivFriend = "set Image here...";
		}
		
		if(viewHolder.tvFriendName != null){
			viewHolder.tvFriendName.setText(user.getName());
		}
		
		if(viewHolder.tvFriendLocation != null){
			viewHolder.tvFriendLocation.setText("San Jose, CA");
		}
	}
}
