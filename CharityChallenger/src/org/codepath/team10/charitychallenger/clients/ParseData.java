package org.codepath.team10.charitychallenger.clients;

import java.util.ArrayList;
import java.util.List;

import org.codepath.team10.charitychallenger.models.Invitation;
import org.codepath.team10.charitychallenger.models.User;

public class ParseData {
	
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
