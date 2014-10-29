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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

public class InvitationsParseAdapter extends ParseQueryAdapter<Invitation> {

	public InvitationsParseAdapter(Context context, final String sender) {
		super(context,  new ParseQueryAdapter.QueryFactory<Invitation>() {

			@Override
			public ParseQuery<Invitation> create() {
				ParseQuery<Invitation> query = ParseQuery.getQuery(Invitation.class);
				query.whereEqualTo("sender",  sender);
				return query;
			}
		});
	}
	
	public static class ViewHolder{
		ImageView	ivFriend;
		TextView	tvFriendName;
		TextView	tvFriendLocation;
		Button		btnInvite;
	}
	
	public View getItemView(Invitation invitation, View convertview, ViewGroup parent) {
		
		
		ViewHolder holder = null;
		if( convertview == null ){
			convertview = LayoutInflater.from(getContext()).inflate(R.layout.item_invite, parent, false);
			super.getItemView(invitation, convertview, parent);
			
			holder = new ViewHolder();
			holder.ivFriend = (ImageView)convertview.findViewById(R.id.ivFriend);
			holder.tvFriendName = (TextView)convertview.findViewById(R.id.tvFriendName);
			holder.tvFriendLocation = (TextView)convertview.findViewById(R.id.tvTimeAgo);
			convertview.setTag(holder);
		}else{
			holder = (ViewHolder) convertview.getTag();
		}
		
		if(invitation.getStatus() == 1){
			holder.btnInvite.setVisibility(View.VISIBLE);
			setListener(holder.btnInvite, invitation);
		} else {
			holder.btnInvite.setVisibility(View.GONE);
			setListener(convertview, invitation);
		}
		
		String relativeCreationTime = FancyTimeUtil.getRelativeTimeAgo(invitation.getCreatedAt().toString());
		holder.tvFriendLocation.setText(relativeCreationTime);
		updateVew(holder, invitation);

		
		return convertview;
	}
	
	private void setListener(View view, final Invitation invitation){
		view.setOnClickListener(new OnClickListener() {
		    
			@Override
		    public void onClick(View v) {
				
		    	final Intent intent = new Intent(getContext(), InvitationDetails.class);
		    	
		    	//final Invitation invitation = getItem(position);
		    	
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
		    		
		    }
		});
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
							Log.d("InvitationsAdapter: name - ", user.getName());
							viewHolder.tvFriendName.setText(user.getName());
						}
					}
				}
		});

	}

}
