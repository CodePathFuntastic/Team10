package org.codepath.team10.charitychallenger.receivers;

import org.codepath.team10.charitychallenger.CharityChallengerApplication;
import org.codepath.team10.charitychallenger.EventManager;
import org.codepath.team10.charitychallenger.ParseData;
import org.codepath.team10.charitychallenger.activities.BaseActivity;
import org.codepath.team10.charitychallenger.models.Invitation;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;

public class ParsePushReceiver extends BroadcastReceiver{

//	private List<InvitationCompletedListener> invitationCompletedListeners = new ArrayList<InvitationCompletedListener>();
//	private List<InvitationReceivedListener> invitationReceivedListeners = new ArrayList<InvitationReceivedListener>();
//	private User user;
	ParseData parseData;
	EventManager eventManager;

	public ParsePushReceiver(){
		super();
		parseData = ParseData.getInstance();
		eventManager = EventManager.getInstance();
	}
	
//	public void setUser( User user){
//		this.user = user;
//	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		
		if( intent != null ){
			if( !intent.getExtras().isEmpty() ){
				Bundle extras = intent.getExtras();
				
				try {
					String channel = extras.getString("com.parse.Channel");
					
					// if the APP doesn't know the logged in user, simply exit.
					// NO PROCESSING is Required
					if(parseData.getUser() == null ){
						return;
					}
					
					if( channel.equals(CharityChallengerApplication.MAIN_CHANNEL) ){
						
					}
					if( channel.equals(CharityChallengerApplication.INVITATION_COMPLETE) ){
						
						String data = intent.getExtras().getString("com.parse.Data");
						completedInvitationsFromPush(data);
						//Toast.makeText(context, "InvitComplete : "+ data, Toast.LENGTH_SHORT).show();
					}
					if( channel.equals(CharityChallengerApplication.INVITATION_RECEIVE) ){
						
						JSONObject json = new JSONObject(intent.getExtras().getString("com.parse.Data"));
						receiverInvitationFromPush(json);
						//Toast.makeText(context, "InviteReceived. channel :" + channel + ", data: "+json, Toast.LENGTH_SHORT).show();
					}
					if( channel.equals("")){
						
					}
						
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

		}else{
			//Toast.makeText(context, "Push Received without intent", Toast.LENGTH_SHORT).show();
		}
	}


//	public void registerInvitationCompleteListener(InvitationCompletedListener listener){
//		if( listener != null){
//			invitationCompletedListeners.add(listener);
//		}
//	}
//	public void unregisterInvitationCompleteListener( InvitationCompletedListener listener ){
//		if( listener != null ){
//			for( InvitationCompletedListener l : invitationCompletedListeners ){
//				if( listener == l ){
//					invitationCompletedListeners.remove(listener);
//					break;
//				}
//			}
//		}
//	}
//	public void registerInvitationReceivedListener(InvitationReceivedListener listener){
//		if( listener != null){
//			invitationReceivedListeners.add(listener);
//		}
//	}
//	public void unregisterInvitationReceivedListener( InvitationReceivedListener listener ){
//		if( listener != null ){
//			for( InvitationReceivedListener l: invitationReceivedListeners){
//				if( l == listener){
//					invitationReceivedListeners.remove(listener);
//				}
//			}
//		}
//	}
	
	public void receiverInvitationFromPush( JSONObject input){
		Invitation invitation = new Invitation();
		
		try {
			String str = input.getString("alert");
			JSONObject json = new JSONObject(str);
			invitation.setReceiver(json.getString("receiver"));
			if( !invitation.getReceiver().equals( parseData.getUser().getFacebookId() )){
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
			
			eventManager.processReceivedInvitations(invitation);
			
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
						eventManager.processCompletedInvitations(invitation);
						//Toast.makeText(context, "Push Received without intent", Toast.LENGTH_SHORT).show();
					}
				}
			});
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
//	private void processReceivedInvitations( Invitation invitation){
//		for( InvitationReceivedListener l : invitationReceivedListeners ){
//			l.onReceive(invitation);
//		}
//	}
//	private void processCompletedInvitations( Invitation invitation){
//		for( InvitationCompletedListener l : invitationCompletedListeners ){
//			l.onComplete(invitation);
//		}
//	}
//
//	@Override
//	public void onSync(User user) {
//		this.user = user;
//	}

}
