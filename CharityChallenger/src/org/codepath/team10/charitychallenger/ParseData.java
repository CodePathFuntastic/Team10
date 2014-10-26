package org.codepath.team10.charitychallenger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.codepath.team10.charitychallenger.models.Invitation;
import org.codepath.team10.charitychallenger.models.User;

/**
 * This class is used for caching data from parse directly.
 * 
 * @author nsankaran
 */
public class ParseData implements Serializable{
	
	private static final long serialVersionUID = -5916211427380606124L;
	
	private List<Invitation> receivedInvitations = new ArrayList<Invitation>();
	private List<Invitation> sentInvitations = new ArrayList<Invitation>();
	private List<User> friends = new ArrayList<User>();
	
	private User user;
	
	private static ParseData SINGLETON = null;
	
	public static ParseData getInstance(){
		if( SINGLETON == null ){
			SINGLETON = new ParseData();
		}
		
		return SINGLETON;
	}
	
	public void setUser(User u){
		user = u;
	}
	public User getUser(){
		return user;
	}
	public List<Invitation> getReceivedInvitations(){
		return receivedInvitations;
	}
	public List<Invitation> getSentInvitations(){
		return sentInvitations;
	}
	
	public void addFriend( User user){
		boolean match = false;
		for( User u : friends ){
			if( u.equals(user) ){
				match =true;
				break;
			}
		}
		
		if( match == false ){
			friends.add(user);
		}
	}
	
	public User getFriendByFacebookId( String facebookId){
		User user=null;
		for( User u : friends ){
			if( u.getFacebookId().equals(facebookId) ){
				user = u;
				break;
			}
		}
		return user;
	}
}
