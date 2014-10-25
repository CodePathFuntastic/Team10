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
}
