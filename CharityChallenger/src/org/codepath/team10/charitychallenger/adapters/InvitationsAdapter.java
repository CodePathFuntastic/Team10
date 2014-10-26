package org.codepath.team10.charitychallenger.adapters;

import java.util.List;

import org.codepath.team10.charitychallenger.ParseData;
import org.codepath.team10.charitychallenger.R;
import org.codepath.team10.charitychallenger.activities.ChallengeDetailsActivity;
import org.codepath.team10.charitychallenger.activities.InvitationDetails;
import org.codepath.team10.charitychallenger.clients.ParseJsonHttpResponseHandler;
import org.codepath.team10.charitychallenger.clients.ParseRestClient;
import org.codepath.team10.charitychallenger.models.Challenge;
import org.codepath.team10.charitychallenger.models.Invitation;
import org.codepath.team10.charitychallenger.models.User;
import org.codepath.team10.charitychallenger.utils.FancyTimeUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

public class InvitationsAdapter extends ArrayAdapter<Invitation> {	
	
	private boolean isSentView = false;
	private ParseData parseData =null;
	
	public InvitationsAdapter(Context context, boolean isSentView, List<Invitation> objects) {
		super(context, R.layout.item_invite, objects);
		this.isSentView = isSentView;
		parseData = ParseData.getInstance();
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
		
		setListener(viewHolder.btnInvite, position);
		
//		if(invitation.getStatus() == InvitationStatusEnum.OPEN.ordinal() ){
//			viewHolder.btnInvite.setVisibility(View.VISIBLE);
//			setListener(viewHolder.btnInvite, position);
//		} else {
//			viewHolder.btnInvite.setVisibility(View.GONE);
//			setListener(convertView, position);
//		}
		
		if( invitation.getCreatedAt() != null ){
			String relativeCreationTime = FancyTimeUtil.getRelativeTimeAgo(invitation.getCreatedAt().toString());
			viewHolder.tvFriendLocation.setText(relativeCreationTime);
		}
		
		
		
		if( isSentView == true){
			User friend = parseData.getFriendByFacebookId(invitation.getReceiver());
			viewHolder.tvFriendName.setText( invitation.getReceiver());
			if( friend == null ){
				updateVew(viewHolder, invitation.getReceiver());
			}else{
				updateView(friend, viewHolder);
			}

		}else{
			User friend = parseData.getFriendByFacebookId(invitation.getSender());
			viewHolder.tvFriendName.setText( invitation.getSender());
			if( friend == null ){
				updateVew(viewHolder, invitation.getSender());
			}else{
				updateView(friend, viewHolder);
			}
		}
		
		
		return convertView;
	}

	private void setListener(View view, final int position){
		view.setOnClickListener(new OnClickListener() {
		    
			@Override
		    public void onClick(View v) {
		    	final Intent intent = new Intent(getContext(), InvitationDetails.class);
		    	
		    	final Invitation invitation = getItem(position);
		    	
		    	
		    	ParseRestClient.getInstance().getInvitationDetail(invitation.getChallengeId(), 
		    			invitation.getSender(), invitation.getReceiver(),
		    			new ParseJsonHttpResponseHandler(){
					@Override
					public void onSuccess(int status, JSONObject json) {
						if( status == 200 && json != null){
							if( !json.isNull("results")){
								try {
									JSONArray array = json.getJSONArray("results");
									int size = array.length();
									//Log.d("Challenge: ", array.getJSONObject(0));
									// we only care about the first result
									if (size > 0) {
										for(int i=0 ; i < 1; i++ ){
											JSONObject j = array.getJSONObject(i);
											Challenge challenge = Challenge.fromJson(j);	
											Intent intent = new Intent(getContext(), ChallengeDetailsActivity.class);
											intent.putExtra("challenge", challenge);
											getContext().startActivity(intent);
										}
									}
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
						}
					}
				});
		    		
		    }
		});
	}
	
	private void updateView( User user, ViewHolder viewHolder){
		if(user.getImageUrl() != null){
			viewHolder.ivFriend.setImageResource(android.R.color.transparent);
			ImageLoader.getInstance().displayImage(user.getImageUrl(), viewHolder.ivFriend);
		}
		Log.d("InvitationsAdapter: name - ", user.getName());
		viewHolder.tvFriendName.setText(user.getName());
	}
	
	private void updateVew(final ViewHolder viewHolder, String  userid) {
		
		ParseRestClient.getInstance().getUserDetails(userid, new ParseJsonHttpResponseHandler(){
			@Override
			public void onSuccess(int status, JSONObject json) {
				if( status == 200 && json != null){
					if( !json.isNull("results")){
						try {
							JSONArray array = json.getJSONArray("results");
							
							// we only care about the first result
							for(int i=0 ; i < 1; i++ ){
								JSONObject j = array.getJSONObject(i);
								User user = User.fromJson(j);
								// save the user in memory
								ParseData.getInstance().addFriend(user);
								if(user.getImageUrl() != null){
									viewHolder.ivFriend.setImageResource(android.R.color.transparent);
									ImageLoader.getInstance().displayImage(user.getImageUrl(), viewHolder.ivFriend);
								}
								Log.d("InvitationsAdapter: name - ", user.getName());
								viewHolder.tvFriendName.setText(user.getName());
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}
			}
		});
		
		/*
		UserQuery.getUserBySenderId(userid, 
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
							Log.d("InvitationsAdapter: name - ", user.getName());
							viewHolder.tvFriendName.setText(user.getName());
						}
					}
				}
		});
		*/
	}
}
