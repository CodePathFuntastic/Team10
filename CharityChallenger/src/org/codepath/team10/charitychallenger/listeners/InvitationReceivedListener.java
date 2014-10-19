package org.codepath.team10.charitychallenger.listeners;

import org.codepath.team10.charitychallenger.models.Invitation;

public interface InvitationReceivedListener {
	public void onReceive( Invitation invitation );
}
