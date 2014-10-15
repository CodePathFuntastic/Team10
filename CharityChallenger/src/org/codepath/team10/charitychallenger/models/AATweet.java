package org.codepath.team10.charitychallenger.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "tweets")
public class AATweet extends Model implements Parcelable {

	@Column(name="body")
	private String body;
	
	@Column(name="created_at")
	private String createdAt;
	
	@Column(name="uid", index=true, unique=true)
	private long uid;
	
	@Column(name="user", index=true)
	private AAUser user;
	
	public AATweet(){
		super();
	}
	
	public AATweet(String body, String created, long id, AAUser usr){
		this.body = body;
		this.createdAt = created;
		this.uid = id;
		this.user = user;
	}
	
	public static AATweet fromJSON( JSONObject jsonObject ){
		AATweet tweet = new AATweet();
		
		try {
			tweet.body = jsonObject.getString("text");
			tweet.createdAt = jsonObject.getString("created_at");
			tweet.uid = jsonObject.getLong("id");
			tweet.user = AAUser.fromJson( jsonObject.getJSONObject("user"));
			
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		
		return tweet;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public AAUser getUser() {
		return user;
	}

	public void setUser(AAUser user) {
		this.user = user;
	}
	
	public String toString(){
		return getBody() + " -- " + getUser().getScreenName();
	}

	public static ArrayList<AATweet> fromJSONArray(JSONArray jsonArray) {
		
		ArrayList<AATweet> tweets = new ArrayList<AATweet>();
		
		for( int i=0; i<jsonArray.length(); i++ ){
			AATweet t=null;
			try {
				
				JSONObject json = (JSONObject) jsonArray.get(i);
				t = AATweet.fromJSON(json);
				
			} catch (JSONException e) {
				e.printStackTrace();
				continue;
			}
			
			if( t != null ){
				tweets.add(t);			
			}

		}
		return tweets;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(getBody());
		dest.writeString(getCreatedAt() );
		dest.writeLong( getUid());
		
		user.writeToParcel(dest, flags);
	}
	
	public static final Parcelable.Creator<AATweet> CREATOR = new Parcelable.Creator<AATweet>() {

		@Override
		public AATweet createFromParcel(Parcel source) {
			return new AATweet(source);
		}

		@Override
		public AATweet[] newArray(int size) {
			return new AATweet[size];
		}
	};
	
	private AATweet( Parcel in ){
		super();
		setBody(in.readString());
		setCreatedAt(in.readString());
		setUid(in.readLong());
		setUser(  AAUser.CREATOR.createFromParcel(in) );
	}
}
