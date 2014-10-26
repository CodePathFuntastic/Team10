package org.codepath.team10.charitychallenger.models;

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

@ParseClassName(value="Challenge")
public class Challenge extends ParseObject implements Parcelable{

	//private static final long serialVersionUID = -8485164036593158312L;


	/**
	 * Data needed for a challenge
	 * 
	 * <ol>
	 * 	<li>id:number</li>
	 * 
	 *  <li>name:String</li>
	 *  <li>description:String</li>
	 *  <li>organization_id:number</li>
	 *  <li>TargetAmount:int</li>
	 *  <li>AmountRaised:int</li>
	 *  
	 *  <li>openChallenges:int</li>
	 *  <li>completedChallenges:int</li>
	 *  <li>paidChallenges:int</li>
	 *  
	 *  <li>challenge_pictures:List</li>
	 * </ol>
	 */
	public Challenge(){
	}
	
	public void setChallengeId( int id){
		put("challenge_id", id);
	}
	public int getChallengeId(){
		return getInt("challenge_id");
	}
	public void setName(String name){
		put("name", name);
	}
	public String getName(){
		return getString("name");
	}
	public void setDescription(String desc){
		put("description", desc);
	}
	public String getDescription(){
		return getString("description");
	}
	public void setOrganization( int orgId){
		put("orgId", orgId);
	}
	public int getOrganization(){
		return getInt("orgId");
	}
	public void setTargetAmount(double target){
		put("target", target);
	}
	public double getTargetAmount(){
		return getDouble("target");
	}
	public void setAmountRaised(double raised){
		put("raised", raised);
	}
	public double getAmountRaised(){
		return getDouble("raised");
	}
	public void setOpenInvitation( int invitations){
		put("open_invitations", invitations);
	}
	public int getOpenInvitations(){
		return getInt("open_invitations");
	}
	public void setClosedInvitations(int invitations){
		put("closed_invitations", invitations);
	}
	public int getClosedInvitations(){
		return getInt("closed_invitations");
	}
	public void setPaidInvitations( int invitations){
		put("paid_invitations", invitations);
	}
	public int getPaidInvitations(){
		return getInt("paid_invitations");
	}
	public void addChallengePictureUrls( List<String> urls){
		addAllUnique("challenge_pic_urls", urls);
	}
	public void addChallengePictureUrl( String url){
		addUnique("challenge_pic_urls", url);
	}
	public List<String> getChallengesPictureUrls(){
		return getList("challenge_pic_urls");
	}

	public static String createJsonQuery(int challengeId, String sender, String recevier) {
		
		String result=null;
		JSONObject json = new JSONObject();
		try {
			json.put("challengeId", challengeId);
			json.put("sender", sender);
			json.put("receiver", recevier);
			result = json.toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	// methods needed for json marshalling and unmarshalling
	public static Challenge fromJson(JSONObject json) {
		Challenge challenge = null;
		
		try {
		
			if( !json.isNull("objectId")){
				challenge = Challenge.createWithoutData(Challenge.class, json.getString("objectId"));
			}
			
			challenge.setChallengeId(json.getInt("challenge_id") );
			
			if( !json.isNull("challenge_pic_urls" ) ){
				JSONArray array = json.getJSONArray("challenge_pic_urls");
				for( int i=0 ; i<array.length() ; i++ ){
					String a = array.getString(i);
					challenge.addChallengePictureUrl(a);
				}
			}
			challenge.setClosedInvitations(json.getInt("closed_invitations"));
			if( !json.isNull("description")){
				challenge.setDescription(json.getString("description") );
			}
			if( !json.isNull("name")){
				challenge.setName(json.getString("name"));
			}
			challenge.setOpenInvitation(json.getInt("open_invitations"));
			challenge.setOpenInvitation(json.getInt("orgId"));
			challenge.setPaidInvitations(json.getInt("paid_invitations"));
			challenge.setAmountRaised(json.getDouble("raised"));
			challenge.setTargetAmount(json.getDouble("target"));
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return challenge;
	}

	
	// methods and classes needed for parcelable
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString( getObjectId());
		pinInBackground();
	}
	
	public static final Parcelable.Creator<Challenge> CREATOR = new Parcelable.Creator<Challenge>() {

		@Override
		public Challenge createFromParcel(Parcel source) {
			
			Challenge c = ParseObject.createWithoutData(Challenge.class, source.readString());
			
			c.fetchFromLocalDatastoreInBackground(new GetCallback<Challenge>() {
				@Override
				public void done(Challenge challenge,
						ParseException e) {				
					if( e == null ){
						
					}else{
						Log.e(BaseActivity.LOG_TAG, "Error while retreiving the object from local DB", e);
					}
				}
			});
			
			c.unpinInBackground();

			return c;
		}

		@Override
		public Challenge[] newArray(int size) {
			return new Challenge[size];
		}
	};

}