package org.codepath.team10.charitychallenger.queries;

import org.codepath.team10.charitychallenger.models.Invitation;
import org.codepath.team10.charitychallenger.models.InvitationStatusEnum;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.RefreshCallback;

public class InvitationQuery {

	
	public static void getInvitations(String receiverId, 
								InvitationStatusEnum status, 
								boolean isOpened,
								FindCallback<Invitation> callback){
		
		ParseQuery<Invitation> query = ParseQuery.getQuery(Invitation.class);
		
		query.whereEqualTo("receiverId", receiverId);
		query.whereEqualTo("state", status.ordinal() );
		query.whereEqualTo("opened_status", isOpened);
		
		query.findInBackground(callback);
	}
	
	public static void syncInvitation( Invitation invitation ){
		
		invitation.refreshInBackground( new RefreshCallback(){

			@Override
			public void done(ParseObject paramParseObject,
					ParseException paramParseException) {
				
			}});
	}
	
}
