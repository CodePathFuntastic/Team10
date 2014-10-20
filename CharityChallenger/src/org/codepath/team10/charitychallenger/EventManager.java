package org.codepath.team10.charitychallenger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.codepath.team10.charitychallenger.listeners.InvitationCompletedListener;
import org.codepath.team10.charitychallenger.listeners.InvitationReceivedListener;
import org.codepath.team10.charitychallenger.listeners.UserSynchedListener;
import org.codepath.team10.charitychallenger.models.Invitation;
import org.codepath.team10.charitychallenger.models.User;

public class EventManager implements Serializable {
	
	private static final long serialVersionUID = -1330586372238451214L;
	
	// maintain a list of listeners to send events system wide.
	// these listeners registered by various activities during onCreate
	private List<UserSynchedListener> userSyncedListeners = new ArrayList<UserSynchedListener>();
	private List<InvitationCompletedListener> invitationCompletedListeners = new ArrayList<InvitationCompletedListener>();
	private List<InvitationReceivedListener> invitationReceivedListeners = new ArrayList<InvitationReceivedListener>();

	private ParseData parseData = ParseData.getInstance();
	private static EventManager SINGLETON = null;
	
	public static EventManager getInstance(){
		
		if( SINGLETON == null ){
			SINGLETON = new EventManager();
		}
		
		return SINGLETON;
	}
	
	////////////////////
	// process events
	////////////////////
	public void processUserSyncEvent( User user){
		if( user != null ){
			parseData.setUser(user);
			for( UserSynchedListener listener : userSyncedListeners ){
				listener.onSync(user);
			}
		}
	}
	
	public void processCompletedInvitations( Invitation invitation){
		if( invitation != null ){
			for( InvitationCompletedListener listener : invitationCompletedListeners){
				listener.onComplete(invitation);
			}
		}
	}
	
	public void processReceivedInvitations( Invitation invitation){
		if( invitation != null ){
			for( InvitationReceivedListener listener : invitationReceivedListeners){
				listener.onReceive(invitation);
			}
		}
	}
	
	////////////////////
	// register and unregister handlers
	////////////////////
	public void registerUserSyncedListener(UserSynchedListener listener){
		if( listener != null){
			userSyncedListeners.add(listener);
		}
	}
	public void unregisterUserSyncedListener( UserSynchedListener listener ){
		if( listener != null){
			for( UserSynchedListener l : userSyncedListeners ){
				if( l == listener){
					userSyncedListeners.remove(listener);
				}
			}
		}
	}
	public void registerInvitationCompletedListener(InvitationCompletedListener listener){
		if( listener != null){
			invitationCompletedListeners.add(listener);
		}
	}
	public void unregisterInvitationCompletedListener( InvitationCompletedListener listener ){
		if( listener != null ){
			for( InvitationCompletedListener l : invitationCompletedListeners ){
				if( listener == l ){
					invitationCompletedListeners.remove(listener);
					break;
				}
			}
		}
	}
	public void registerInvitationReceivedListener(InvitationReceivedListener listener){
		if( listener != null){
			invitationReceivedListeners.add(listener);
		}
	}
	public void unregisterInvitationReceivedListener( InvitationReceivedListener listener ){
		if( listener != null ){
			for( InvitationReceivedListener l: invitationReceivedListeners){
				if( l == listener){
					invitationReceivedListeners.remove(listener);
				}
			}
		}
	}
}
