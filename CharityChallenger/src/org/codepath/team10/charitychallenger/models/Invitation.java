package org.codepath.team10.charitychallenger.models;

import java.util.List;

import org.codepath.team10.charitychallenger.activities.BaseActivity;

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

	// methods needed for json to and from
	
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
}
