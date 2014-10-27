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

@ParseClassName(value="User")
public class User extends ParseObject implements Parcelable{
	
	/**
	 * <ol>
	 * 	<li>name</li>
	 * 	<li>facebookId</li>
	 * 	<li>twitterId</li>
	 * 	<li>location</li>
	 * 	<li>email</li>
	 * 	<li>phone_number</li>
	 * 	<li>image_url</li>
	 * 	<li>friends</li>
	 * </ol>
	 * 
	 */

	public User(){
	}
	
	public void setName( String name){
		put("name", name);
	}
	public String getName(){
		return getString("name");
	}
	public void setFacebookId(String id){
		put("facebookId", id);
	}
	public void setPhoneNumber(String phone){
		put("phone", phone);
	}
	public String getPhoneNumber(){
		return getString("phone");
	}
	public String getFacebookId(){
		return getString("facebookId");
	}
	public void setTwitterId( String id){
		put("twitterId", id);
	}
	public String getTwitterId(){
		return getString("twitterId");
	}
	public void setLocation(String loc){
		put("location", loc);
	}
	public String getLocation(){
		return getString("location");
	}
	public void setEmail(String email){
		put("email", email);
	}
	public String getEmail(){
		return getString("email");
	}
	public void setImageUrl( String imageUrl){
		put("image_url", imageUrl);
	}
	public String getImageUrl(){
		return getString("image_url");
	}
	public List<User> getFriends(){
		return getList("friends");
	}
	public void addFriend(String friend){
		addUnique("friends", friend);
	}
	public void addFriends( List<String> friends){
		addAllUnique("friends", friends);
	}

	
	// Queries
	public static String createJsonQuery(String facebookId) {
		
		String result=null;
		JSONObject json = new JSONObject();
		try {
			json.put("facebookId", facebookId);
			result = json.toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	// JSON methods
	public static User fromJson( JSONObject json ){
		User user=null;
		if( json != null ){
			try {
				if( !json.isNull("objectId")){
					user = User.createWithoutData(User.class, json.getString("objectId"));
				}
				if( !json.isNull("facebookId")){
					user.setFacebookId(json.getString("facebookId"));
				}
				if( !json.isNull("friends")){
					JSONArray array = json.getJSONArray("friends");
					for( int i=0; i<array.length(); i++){
						String a = array.getString(i);
						user.addFriend(a);
					}
				}
				if( !json.isNull("image_url")){
					user.setImageUrl( json.getString("image_url"));
				}
				if( !json.isNull("location")){
					user.setLocation(json.getString("location"));
				}
				if( !json.isNull("name")){
					user.setName( json.getString("name"));
				}
			
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		return user;
	}
	
	// methods related parcelable
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(getObjectId());
		pinInBackground();
	}
	
	public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {

		@Override
		public User createFromParcel(Parcel source) {
			User user = ParseObject.createWithoutData(User.class, source.readString());
			user.fetchFromLocalDatastoreInBackground( new GetCallback<User>(){

				@Override
				public void done(User user,
									ParseException e) {
					if( e == null ){
						
					}else{
						Log.e(BaseActivity.LOG_TAG, "Unable to retrieve user from localDB", e);
					}
				}
			});
			return user;
		}

		@Override
		public User[] newArray(int size) {
			return new User[size];
		}
	};
	
	
	@Override
	public int hashCode() {
		int result = 0;
		if( getObjectId() != null ){
			result = getObjectId().hashCode();
		}else{
			result = super.hashCode();
		}
		return result;
	}
	
	@Override
	public boolean equals(Object o) {
		boolean result=false;
		if( hashCode() == o.hashCode() ){
			result = true;
		}
		return result;
	}

}

