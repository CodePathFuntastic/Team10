package org.codepath.team10.charitychallenger.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codepath.team10.charitychallenger.activities.BaseActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.parse.GetCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;

@ParseClassName(value="Invitation")
public class Invitation extends ParseObject implements Parcelable {

	/**
	 * <ol>
	 * 		<li>id:int</li>
	 * 		<li>challengeId:int</li>
	 * 		<li>senderId:id</li>
	 * 		<li>receiver:id</li>
	 * 		<li>status:int</li>
	 * 		<li>isOpened:boolean</li>
	 * 		<li>message:String</li>
	 * 		<li>amount:float</li>
	 * 		<li>photos</li>
	 * 		
	 * </ol>
	 * 
	 */
	Date created;
	Date update;
	
	public Invitation(){
//		setOpened(false);
//		setStatus(InvitationStatusEnum.OPEN.ordinal());
//		increment("inviteId");
		
	}
	
	
	public int getInviteId(){
		return getInt("inviteId");
	}
	public void setInviteId( int id){
		put("inviteId", id);
	}
	public void setChallengeId( int id){
		put("challengeId", id);
	}
	public int getChallengeId(){
		return getInt("challengeId");
	}
	public void setSender(String user){
		put("sender", user);
	}
	public String getSender(){
		return getString("sender");
	}
	public void setReceiver( String id){
		put("receiver", id);
	}
	public String getReceiver(){
		return getString("receiver");
	}
	public void setSubject(String subject){
		put("subject", subject);
	}
	public String getSubject(){
		return getString("subject");
	}
	public void setMessage(String msg){
		put("message", msg);
	}
	public String getMessage(){
		return getString("message");
	}
	public void setAmount(double amt){
		put("amount", amt);
	}
	public double getAmount(){
		return getDouble("amount");
	}
	public void setOpened(boolean status){
		put("opened_status", status);
	}
	public boolean isOpened(){
		return getBoolean("opened_status");
	}
	public void setStatus(int status){
		put("status", status);
	}
	public int getStatus(){
		return getInt("status");
	}
	public void addPhoto(String photoUrl){
		addUnique("photos",photoUrl);
	}
	public List<String> getPhotos(){
		return getList("photos");
	}
	
	@Override
	public int hashCode() {
		if( getObjectId() != null ){
			return getObjectId().hashCode();
		}else{
			return super.hashCode();
		}
	}
	
	@Override
	public boolean equals(Object o) {
		boolean result=false;
		if( o instanceof Invitation){
			Invitation compared = (Invitation) o;
			if( getObjectId() != null && compared.getObjectId() != null ){
				if( hashCode() == compared.hashCode() ){
					result =true;
				}
			}
		}else{
			result= super.equals(o);
		}
		return result;
	}
	
	public String toString(){
		
		StringBuilder sb = new StringBuilder();
		sb.append("objectId=");
		sb.append( getObjectId());
		sb.append(", sender=");
		sb.append( getSender());
		sb.append(", receiver=");
		sb.append( getReceiver() );
		sb.append(", message=");
		sb.append( getMessage());
		sb.append(", amount=");
		sb.append( getAmount());
		sb.append(", challenge=");
		sb.append( getChallengeId());
		sb.append(", status=");
		sb.append( getStatus());
		return sb.toString();
	}
	
	

	// methods needed for json to and from

	public static JSONObject toJson( Invitation invitation){
		JSONObject json = new JSONObject();
		
		if( invitation != null ){
			
				try {
					
					if( invitation.getObjectId() != null ){
						json.put("objectId", invitation.getObjectId());
					}
					
					json.put("amount", invitation.getAmount());
					json.put("challengeId", invitation.getChallengeId());
					json.put("inviteId", invitation.getInviteId());
					json.put("opened_status", invitation.isOpened());
					
					if( invitation.getPhotos().size() > 0 ){
						JSONArray array = new JSONArray();
						for( String photo : invitation.getPhotos() ){
							array.put(photo);
						}
						json.put("photos", array);
					}
					
					if( invitation.getSender() != null ){
						json.put("sender",  invitation.getSender());
					}
					if( invitation.getReceiver() != null ){
						json.put("receiver", invitation.getReceiver());
					}
					
					json.put("status",  invitation.getStatus());
					if( invitation.getCreatedAt() != null ){
						json.put("createdAt", invitation.getCreatedAt());
					}
					if( invitation.getUpdatedAt() != null ){
						json.put("updatedAt", invitation.getUpdatedAt());
					}
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
		}
		
		return json;
	}
	
	
	public static Invitation fromJson( JSONObject json ){
		
		Invitation invitation=null;
		
		try {
		
			String objectId = json.getString("objectId");
			invitation = Invitation.createWithoutData(Invitation.class, objectId);
			
			if( !json.isNull("amount")){
				invitation.setAmount( json.getDouble("amount"));
			}
			if( !json.isNull("challengeId")){
				invitation.setChallengeId( json.getInt("challengeId") );
			}

			if( !json.isNull("inviteId")){
				invitation.setInviteId(json.getInt("inviteId"));
			}

			if( !json.isNull("opened_status")){
				invitation.setOpened(json.getBoolean("opened_status"));
			}
			
			if( !json.isNull("photos")){
				JSONArray array = json.getJSONArray("photos");
				for(int i=0 ; i< array.length(); i++){
					String photo = array.getString(i);
					invitation.addPhoto(photo);
				}
			}
			
			if( !json.isNull("receiver")){
				invitation.setReceiver(json.getString("receiver"));
			}
			
			if( !json.isNull("sender") ){
				invitation.setSender( json.getString("sender"));
			}
			
			if( !json.isNull("status")){
				invitation.setStatus(json.getInt("status"));
			}
			
			// following two may fail, in that case store in a different field
			if( !json.isNull("createdAt")){
				invitation.add("createdAt", json.getString("createdAt"));
				
			}
			if( !json.isNull("updatedAt")){
				invitation.add("updatedAt", json.getString("updatedAt"));
			}
			
						
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return invitation;
	}
	
	public static List<Invitation> fromJsonArray( JSONArray array){
		
		List<Invitation> invitations = new ArrayList<Invitation>();
		
		if( array != null ){
			for( int i=0; i<array.length() ; i++){
				try {
					
					JSONObject json = array.getJSONObject(i);
					Invitation invitation = Invitation.fromJson(json);
					invitations.add(invitation);
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		
		return invitations;
	}
	
	
	// methods and classes needed for Parcelable
	
	@Override
	public int describeContents() {
		return 0;
	}


	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(getObjectId());
		pinInBackground();
	}
	public static final Parcelable.Creator<Invitation> CREATOR = new Parcelable.Creator<Invitation>() {

		@Override
		public Invitation createFromParcel(Parcel source) {
			Invitation invitation = ParseObject.createWithoutData(Invitation.class, source.readString());
			invitation.fetchFromLocalDatastoreInBackground( new GetCallback<Invitation>(){

				@Override
				public void done(Invitation invitation,
									ParseException e) {
					if( e == null ){
						
					}else{
						Log.e(BaseActivity.LOG_TAG, "Unable to retrieve invitation from localDB", e);
					}
				}
			});
			return invitation;
		}

		@Override
		public Invitation[] newArray(int size) {
			return new Invitation[size];
		}
	};	
	
	
	
	// Queries for Parse REST api
	
	public static String createJsonQuery(String sender, String receiver){

		JSONObject json = new JSONObject();
		try {
				if( sender != null ){
					json.put("sender", sender);
				}
				if( receiver != null ){
					json.put("receiver", receiver);
				}

		} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		
		return json.toString();
	}
	
	public static String createJsonQuery(String sender, String receiver, int challengeId){
		
		JSONObject json = new JSONObject();
		try {
				if( sender != null ){
					json.put("sender", sender);
				}
				if( receiver != null ){
					json.put("receiver", receiver);
				}
				json.put("challengeId", challengeId);

		} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		
		return json.toString();		
	}
	
	public static String createJsonQueryStatus(String sender, String receiver, int status){
		
		JSONObject json = new JSONObject();
		try {
				if( sender != null ){
					json.put("sender", sender);
				}
				if( receiver != null ){
					json.put("receiver", receiver);
				}
				json.put("status", status);

		} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		
		return json.toString();		
	}
	
	public static String createJsonQueryOpenedStatus(String sender, String receiver, boolean isOpened){
		
		JSONObject json = new JSONObject();
		try {
				if( sender != null ){
					json.put("sender", sender);
				}
				if( receiver != null ){
					json.put("receiver", receiver);
				}
				json.put("opened_status", isOpened);

		} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		
		return json.toString();		
	}


}
