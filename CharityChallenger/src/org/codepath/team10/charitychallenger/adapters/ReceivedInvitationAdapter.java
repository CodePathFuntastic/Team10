package org.codepath.team10.charitychallenger.adapters;

import java.util.List;

import org.codepath.team10.charitychallenger.R;
import org.codepath.team10.charitychallenger.models.Invitation;
import org.codepath.team10.charitychallenger.models.User;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
;

public class ReceivedInvitationAdapter extends ArrayAdapter<User> {
	public ReceivedInvitationAdapter(Context context, List<Invitation> objects) {
		super(context, R.layout.item_invite);
	}
	
	public static class ViewHolder{
		private ImageView	ivFriend;
		private TextView	tvFriendName;
		public TextView		tvFriendLocation;
		public Button		btnInvite;
	}

	@Override
	public View getView(final int position, View convertView, final ViewGroup parent) {
		// Get the data from position.
		User user = getItem(position);
		
		ViewHolder viewHolder =  null;
		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_invite, parent, false);
			viewHolder.ivFriend = (ImageView)convertView.findViewById(R.id.ivFriend);
			viewHolder.tvFriendName = (TextView)convertView.findViewById(R.id.tvFriendName);
			viewHolder.tvFriendLocation = (TextView)convertView.findViewById(R.id.tvFriendLocation);
			viewHolder.btnInvite = (Button) convertView.findViewById(R.id.btnInviteFriend);
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}	
		
//		viewHolder.btnInvite.setOnClickListener(new OnClickListener() {
//		    @Override
//		    public void onClick(View v) {
//		        ((Object) parent).performItemClick(v, position, 0);
//		    }
//		});
		updateVew(viewHolder, user);
		
		return convertView;
	}

	private void updateVew(ViewHolder viewHolder, User user) {
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
