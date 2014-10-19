package org.codepath.team10.charitychallenger.listeners;

import org.codepath.team10.charitychallenger.models.User;

public interface UserSynchedListener {
	public void onSync( User user);
}
