package org.codepath.team10.charitychallenger.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SentInvitationsFragment extends BaseInvitationsListFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		populateSentInvitations();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	private void populateSentInvitations() {
		if( parseData.getSentInvitations().size() == 0 ){
			// fire a network call to load the sent invitations for the "user"
			eventManager.getSentInvitations();
		}else{
			addAllInvitations( parseData.getSentInvitations());
		}
	}
}
