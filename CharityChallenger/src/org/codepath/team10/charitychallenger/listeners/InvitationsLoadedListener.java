package org.codepath.team10.charitychallenger.listeners;

public interface InvitationsLoadedListener {
	
	public void onSuccess();
	
	public void onFailure( String message, Throwable error);
}
