package org.codepath.team10.charitychallenger.adapters;

import java.util.List;

import org.codepath.team10.charitychallenger.ParseData;
import org.codepath.team10.charitychallenger.R;
import org.codepath.team10.charitychallenger.activities.InvitationDetails;
import org.codepath.team10.charitychallenger.clients.ParseJsonHttpResponseHandler;
import org.codepath.team10.charitychallenger.clients.ParseRestClient;
import org.codepath.team10.charitychallenger.models.Challenge;
import org.codepath.team10.charitychallenger.models.Invitation;
import org.codepath.team10.charitychallenger.models.User;
import org.codepath.team10.charitychallenger.utils.FancyTimeUtil;
import org.codepath.team10.charitychallenger.utils.RoundTransform;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class InvitationsAdapter extends ArrayAdapter<Invitation> {	
	private ParseData parseData =null;
	
	public InvitationsAdapter(Context context, boolean isSentView, List<Invitation> objects) {
		super(context, R.layout.item_invite, objects);
		parseData = ParseData.getInstance();
	}
	
	public static class ViewHolder{
		private ImageView	ivFriend;
		private TextView	tvSender;
		private TextView	tvName;
		private TextView 	tvInvitationSubject;
		private TextView 	tvTimeAgo;
	}

	@Override
	public View getView(final int position, View convertView, final ViewGroup parent) {
		// Get the data from position.
		Invitation invitation = getItem(position);
		
		ViewHolder viewHolder =  null;
		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_invite_received, parent, false);
			viewHolder.ivFriend = (ImageView)convertView.findViewById(R.id.ivFriend);
			viewHolder.tvSender = (TextView)convertView.findViewById(R.id.tvSender);
			viewHolder.tvTimeAgo = (TextView)convertView.findViewById(R.id.tvTimeAgo);
			viewHolder.tvInvitationSubject = (TextView) convertView.findViewById(R.id.tvInviteSubject);
			//viewHolder.tvName = (TextView)convertView.findViewById(R.id.tvName); // challenge name.
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}	
				
		if( invitation.getCreatedAt() != null ){
			viewHolder.tvTimeAgo.setText("");
			String relativeCreationTime = FancyTimeUtil.getRelativeTimeAgo(invitation.getCreatedAt().toString());
			viewHolder.tvTimeAgo.setText(relativeCreationTime);
		}
		
		if( viewHolder.tvInvitationSubject != null){
			
			if( invitation.getSubject() != null ){
				viewHolder.tvInvitationSubject.setText( invitation.getMessage());
			}else{
				viewHolder.tvInvitationSubject.setText( "");
			}
		}
		updateVew(viewHolder, invitation.getSender());
		setInvitationListener(convertView, position, invitation);
		
		return convertView;
	}

	private void setInvitationListener(View view, int position,
										final Invitation invitation) {
		
		view.setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ParseRestClient client = ParseRestClient.getInstance();
				client.getChallengeById(invitation.getChallengeId(), new ParseJsonHttpResponseHandler(){
					@Override
					public void onSuccess(int status, JSONObject json) {
						if( status == 200 && json != null ){
							if( !json.isNull("results")){
								try {
									JSONArray array = json.getJSONArray("results");
									// it should only 1 in length
									if( array.length() > 0 ){
										JSONObject j = array.getJSONObject(0);
										Challenge c = Challenge.fromJson(j);
										
										Intent intent = new Intent(getContext(), InvitationDetails.class);
										intent.putExtra("challenge", c);
										intent.putExtra("invitation", invitation);
										getContext().startActivity(intent);
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

	private void updateVew(final ViewHolder viewHolder, String  userid) {
	
		if( ParseData.getInstance().getFriendByFacebookId(userid) != null ){
			User friend = ParseData.getInstance().getFriendByFacebookId(userid);
			if( friend.getImageUrl() != null ){
				viewHolder.ivFriend.setImageResource(android.R.color.transparent);
				Picasso.with(getContext()).load(friend.getImageUrl())
				 							.error(R.drawable.ic_launcher)
				 							.transform(new RoundTransform())
				 							.into(viewHolder.ivFriend);
			}
			
			Log.d("InvitationsAdapter: name - ", friend.getName());
			viewHolder.tvSender.setText("");
			viewHolder.tvSender.setText(friend.getName());
		}else{
	
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
									Picasso.with(getContext()).load(user.getImageUrl())
									 .error(R.drawable.ic_launcher)
									 .transform(new RoundTransform()).into(viewHolder.ivFriend);
								}
								Log.d("InvitationsAdapter: name - ", user.getName());
								viewHolder.tvSender.setText("");
								viewHolder.tvSender.setText(user.getName());
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}
			}
		});
		}
		
	}
}
