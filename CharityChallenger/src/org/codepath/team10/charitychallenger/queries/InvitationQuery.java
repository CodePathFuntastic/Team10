package org.codepath.team10.charitychallenger.queries;

import org.codepath.team10.charitychallenger.models.Invitation;
import org.codepath.team10.charitychallenger.models.InvitationStatusEnum;

import com.parse.FindCallback;
import com.parse.ParseQuery;

public class InvitationQuery {

	
	public static void getInvitations(String receiver, 
								InvitationStatusEnum status, 
								boolean isOpened,
								FindCallback<Invitation> callback){
		
		ParseQuery<Invitation> query = ParseQuery.getQuery(Invitation.class);
		
		query.whereEqualTo("receiver", receiver);
		query.whereEqualTo("state", status.ordinal() );
		query.whereEqualTo("opened_status", isOpened);
		
		query.findInBackground(callback);
	}
	
	public static void getAcceptedInvitations(String sender, 
			InvitationStatusEnum status, 
			boolean isOpened,
			FindCallback<Invitation> callback){

		ParseQuery<Invitation> query = ParseQuery.getQuery(Invitation.class);

		query.whereEqualTo("sender", sender);
		query.whereEqualTo("status", status.ordinal());
		//query.whereEqualTo("opened_status", isOpened);

		query.findInBackground(callback);
	}
	
	
	
	public static void getSentInvitations( String sentUser, FindCallback<Invitation> callback ){
		
		ParseQuery<Invitation> query = ParseQuery.getQuery(Invitation.class);
		
		query.whereEqualTo("sender", sentUser);
		//query.clearCachedResult();
		
		query.findInBackground(callback);
	}

	public static void getReceivedInvitations( String receiver, FindCallback<Invitation> callback ){
		
		ParseQuery<Invitation> query1 = ParseQuery.getQuery(Invitation.class);
		query1.whereEqualTo("receiver", receiver);
		//query1.clearCachedResult();
		
		query1.findInBackground(callback);
	}

}
