package org.codepath.team10.charitychallenger.adapters;

import java.util.List;

import org.codepath.team10.charitychallenger.R;
import org.codepath.team10.charitychallenger.activities.InvitationDetails;
import org.codepath.team10.charitychallenger.models.Challenge;
import org.codepath.team10.charitychallenger.models.Invitation;
import org.codepath.team10.charitychallenger.models.User;
import org.codepath.team10.charitychallenger.queries.ChallengeQueries;
import org.codepath.team10.charitychallenger.queries.UserQuery;
import org.codepath.team10.charitychallenger.utils.FancyTimeUtil;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.parse.FindCallback;
import com.parse.ParseException;

public class InvitationsAdapter extends ArrayAdapter<Invitation> {	
	
	public InvitationsAdapter(Context context, List<Invitation> objects) {
		super(context, R.layout.item_invite, objects);
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
		Invitation invitation = getItem(position);
		
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

		viewHolder.btnInvite.setOnClickListener(new OnClickListener() {
		    
			@Override
		    public void onClick(View v) {
		    	final Intent intent = new Intent(getContext(), InvitationDetails.class);
		    	
		    	final Invitation invitation = getItem(position);
		    	
		    	ChallengeQueries.getChallengeById( invitation.getChallengeId(), 
		    						new FindCallback<Challenge>(){
										@Override
										public void done(
												List<Challenge> challenges,
												ParseException e) {
												
											if( e == null ){
												// it should be only one
												if( challenges.size()>0 ){
													Challenge challenge = challenges.get(0);
													intent.putExtra("challenge", challenge);
													intent.putExtra("invitation", invitation);
													getContext().startActivity(intent);
												}
											}
										}
		    			});
		    		
		    	
		    	
		    	
//		    		ParseQuery<ParseObject> query = ParseQuery.getQuery("Challenge");
//					query.whereEqualTo("challenge_id", getItem(position).getInt("challengeId"));
//					query.findInBackground(new FindCallback<ParseObject>() {
//			        public void done(List<ParseObject> records, ParseException e) {
//			        	if(records.size()>0){
//			        		ParseProxyObject ppo = new ParseProxyObject(records.get(0));
//			        		intent.putExtra("challenge", ppo);
//			        		getContext().startActivity(intent);
//			        	} else {
//			        		Log.d("Error:", "no challenge found for challangeId - " + getItem(position).getInt("challengeId"));
//			        	}
//			        }
//				});
		    }
		});
		
		String relativeCreationTime = FancyTimeUtil.getRelativeTimeAgo(invitation.getCreatedAt().toString());
		viewHolder.tvFriendLocation.setText(relativeCreationTime + " ago");
		updateVew(viewHolder, invitation);
		
		return convertView;
	}

	private void updateVew(final ViewHolder viewHolder, Invitation invitation) {
		UserQuery.getUserBySenderId( invitation.getSender(), 
			new FindCallback<User>(){
				@Override
				public void done(
						List<User> users,
						ParseException e) {
						
					if( e == null ){
						// it should be only one
						if( users.size()>0 ){
							User user = users.get(0);
							if(user.getImageUrl() != null){
								viewHolder.ivFriend.setImageResource(android.R.color.transparent);
								ImageLoader.getInstance().displayImage(user.getImageUrl(), viewHolder.ivFriend);
							}
							viewHolder.tvFriendName.setText(user.getName());
						}
					}
				}
		});

	}
}
