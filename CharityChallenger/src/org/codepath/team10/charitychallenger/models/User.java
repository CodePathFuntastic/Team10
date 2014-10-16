package org.codepath.team10.charitychallenger.models;

import java.util.List;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseRelation;

@ParseClassName(value="User")
public class User extends ParseObject {
	
	/**
	 * <ol>
	 * 	<li>name</li>
	 * 	<li>facebookId</li>
	 * 	<li>twitterId</li>
	 * 	<li>location</li>
	 * 	<li>email</li>
	 * 	<li>friends</li>
	 * </ol>
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
	public List<User> getFriends(){
		return getList("friends");
	}
	public void addFriend(String friend){
		addUnique("friends", friend);
	}
	public void addFriends( List<String> friends){
		addAllUnique("friends", friends);
	}
}
