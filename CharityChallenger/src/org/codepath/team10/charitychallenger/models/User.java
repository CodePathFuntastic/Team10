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
		List<User> friends=null;
		
		ParseRelation<User> relation = getRelation("friends");
		try {
			friends = relation.getQuery().find();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return friends;
	}
	
	public void addFriend(User user){
		ParseRelation<User> relation = getRelation("friends");
		relation.add(user);
		this.saveInBackground();
	}
	
	public void addFriends( List<User> friends){
		ParseRelation<User> relation = getRelation("friends");
		for( User f : friends ){
			relation.add(f);
		}
		this.saveInBackground();
	}
}
