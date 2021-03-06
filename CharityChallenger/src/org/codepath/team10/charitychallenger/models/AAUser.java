package org.codepath.team10.charitychallenger.models;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "users")
public class AAUser extends Model implements Parcelable{

	private static final long serialVersionUID = 4124610137957134742L;
	
	@Column(name="name")
	private String name;
	
	@Column(name="uid", index=true)
	private long uid;
	
	@Column(name="screen_name", index=true, unique=true)
	private String screenName;
	
	@Column(name="profile_image_url")
	private String profileImageUrl;
	
	@Column(name="description")
	private String descrption;
	
	@Column(name="following")
	private int following;
	
	@Column(name="followers")
	private int followers;
	
	public AAUser(){
		super();
	}
	
	public static AAUser fromJson(JSONObject jsonObject) {
		
		AAUser user = null;
		
		try {
			
			user = new AAUser();
			
			user.name = jsonObject.getString("name");
			user.uid = jsonObject.getLong("id");
			user.screenName = jsonObject.getString("screen_name");
			user.profileImageUrl = jsonObject.getString("profile_image_url");
			user.descrption = jsonObject.getString("description");
			user.followers =jsonObject.getInt("followers_count");
			user.following = jsonObject.getInt("friends_count");
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long id) {
		this.uid = id;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}
	
	public String getTag(){
		return descrption;
	}
	public void setTag( String tag){
		descrption = tag;
	}
	public int getFollowing(){
		return following;
	}
	public void setFollowing( int following ){
		this.following = following;
	}
	public int getFollowers(){
		return followers;
	}
	public void setFollowers( int followers ){
		this.followers = followers;
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(getUid());
		dest.writeString( getName() );
		dest.writeString(getProfileImageUrl());
		dest.writeString(getScreenName());
		dest.writeString(getTag());
		dest.writeInt( getFollowing());
		dest.writeInt( getFollowers() );
	}

	public static final Parcelable.Creator<AAUser> CREATOR = new Parcelable.Creator<AAUser>() {

		@Override
		public AAUser createFromParcel(Parcel source) {
			return new AAUser(source);
		}

		@Override
		public AAUser[] newArray(int size) {
			return new AAUser[size];
		}
	};
	
	private AAUser( Parcel in ){
		super();
		setUid(in.readLong());
		setName(in.readString());
		setProfileImageUrl(in.readString());
		setScreenName(in.readString());
		setTag(in.readString());
		setFollowing( in.readInt() );
		setFollowers( in.readInt() );
	}
}
