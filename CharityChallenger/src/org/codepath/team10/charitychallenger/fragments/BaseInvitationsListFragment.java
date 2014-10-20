package org.codepath.team10.charitychallenger.fragments;

import java.util.ArrayList;
import java.util.List;

import org.codepath.team10.charitychallenger.adapters.InvitationsAdapter;
import org.codepath.team10.charitychallenger.models.Invitation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

public class BaseInvitationsListFragment extends Fragment{
	
	protected InvitationsAdapter invitationsAdapter;
	protected List<Invitation> invitations = new ArrayList<Invitation>();
	
	public BaseInvitationsListFragment(){
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		invitationsAdapter = new InvitationsAdapter( getActivity(), invitations);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	}
}
