package org.codepath.team10.charitychallenger.utils;

import org.codepath.team10.charitychallenger.models.Challenge;
import org.codepath.team10.charitychallenger.models.Invitation;
import org.json.JSONException;
import org.json.JSONObject;

public class InvitationMessageUtils {

	public static String generateInvitationCompleteMsg( Invitation invitation, Challenge challenge ){
		
		JSONObject json = new JSONObject();
		
		try {
			json.put("invitation_id", invitation.getInviteId());
			json.put("invitation_obj_id", invitation.getObjectId() );
			json.put("challenge_id", challenge.getChallengeId() );
			json.put("challenge_obj_id", challenge.getObjectId());
			json.put("invitation_status", invitation.getStatus());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return json.toString();
	} 
}
