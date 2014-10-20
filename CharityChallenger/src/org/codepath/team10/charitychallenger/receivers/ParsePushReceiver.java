package org.codepath.team10.charitychallenger.receivers;

import java.util.ArrayList;
import java.util.List;

import org.codepath.team10.charitychallenger.CharityChallengerApplication;
import org.codepath.team10.charitychallenger.activities.BaseActivity;
import org.codepath.team10.charitychallenger.listeners.InvitationCompletedListener;
import org.codepath.team10.charitychallenger.listeners.InvitationReceivedListener;
import org.codepath.team10.charitychallenger.listeners.UserSynchedListener;
import org.codepath.team10.charitychallenger.models.Invitation;
import org.codepath.team10.charitychallenger.models.User;
import org.json.JSONException;
import org.json.JSONObject;

import com.parse.GetCallback;
import com.parse.ParseException;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class ParsePushReceiver extends BroadcastReceiver implements UserSynchedListener{

	private List<InvitationCompletedListener> invitationCompletedListeners = new ArrayList<InvitationCompletedListener>();
	private List<InvitationReceivedListener> invitationReceivedListeners = new ArrayList<InvitationReceivedListener>();
	private User user;

	public ParsePushReceiver(){
		super();
	}
	
	public void setUser( User user){
		this.user = user;
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		
		if( intent != null ){
			if( !intent.getExtras().isEmpty() ){
				Bundle extras = intent.getExtras();
				
				try {
					String channel = extras.getString("com.parse.Channel");
					
					if( channel.equals(CharityChallengerApplication.MAIN_CHANNEL) ){
						
					}
					if( channel.equals(CharityChallengerApplication.INVITATION_COMPLETE) ){
						String data = intent.getExtras().getString("com.parse.Data");
						completedInvitationsFromPush(data);
						Toast.makeText(context, "InvitComplete : "+ data, Toast.LENGTH_SHORT).show();
					}
					if( channel.equals(CharityChallengerApplication.INVITATION_RECEIVE) ){
						if(user == null ){
							return;
						}
						JSONObject json = new JSONObject(intent.getExtras().getString("com.parse.Data"));
						receiverInvitationFromPush(json);
						Toast.makeText(context, "InviteReceived. channel :" + channel + ", data: "+json, Toast.LENGTH_SHORT).show();
					}
					if( channel.equals("")){
						
					}
						
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

		}else{
			Toast.makeText(context, "Push Received without intent", Toast.LENGTH_SHORT).show();
		}
	}


	public void registerInvitationCompleteListener(InvitationCompletedListener listener){
		if( listener != null){
			invitationCompletedListeners.add(listener);
		}
	}
	public void unregisterInvitationCompleteListener( InvitationCompletedListener listener ){
		if( listener != null ){
			for( InvitationCompletedListener l : invitationCompletedListeners ){
				if( listener == l ){
					invitationCompletedListeners.remove(listener);
					break;
				}
			}
		}
	}
	public void registerInvitationReceivedListener(InvitationReceivedListener listener){
		if( listener != null){
			invitationReceivedListeners.add(listener);
		}
	}
	public void unregisterInvitationReceivedListener( InvitationReceivedListener listener ){
		if( listener != null ){
			for( InvitationReceivedListener l: invitationReceivedListeners){
				if( l == listener){
					invitationReceivedListeners.remove(listener);
				}
			}
		}
	}
	
	public void receiverInvitationFromPush( JSONObject input){
		Invitation invitation = new Invitation();
		
		try {
			String str = input.getString("alert");
			JSONObject json = new JSONObject(str);
			invitation.setReceiver(json.getString("receiver"));
			if( !invitation.getReceiver().equals( user.getFacebookId() )){
				// simply ignore the message
				return;
			}
			invitation.setSender(json.getString("sender"));
			invitation.setChallengeId(json.getInt("challengeId"));
			invitation.setStatus(json.getInt("status"));
			//invitation.setObjectId(json.getString("objectId"));
			invitation.setAmount(json.getInt("amount"));
			invitation.setSubject(json.getString("subject"));
			invitation.setMessage( json.getString("message"));
			
			processReceivedInvitations(invitation);
			
		} catch (JSONException e) {
			e.printStackTrace();
			Log.e(BaseActivity.LOG_TAG,"unable to process invite ", e);
		}
		
	}

	public void completedInvitationsFromPush(String data) {
		
		try {
			JSONObject jsondata = new JSONObject(data);
			String str =jsondata.getString("alert");
			JSONObject json = new JSONObject(str);
			
			String objectId = json.getString("invitation_obj_id");
			Invitation invitation = Invitation.createWithoutData(Invitation.class, objectId);
			
			invitation.fetchInBackground(new GetCallback<Invitation>() {

				@Override
				public void done(Invitation invitation,
						ParseException e) {
					if(e == null ){
						processCompletedInvitations(invitation);
					}
				}
			});
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	private void processReceivedInvitations( Invitation invitation){
		for( InvitationReceivedListener l : invitationReceivedListeners ){
			l.onReceive(invitation);
		}
	}
	private void processCompletedInvitations( Invitation invitation){
		for( InvitationCompletedListener l : invitationCompletedListeners ){
			l.onComplete(invitation);
		}
	}

	@Override
	public void onSync(User user) {
		this.user = user;
	}

}
