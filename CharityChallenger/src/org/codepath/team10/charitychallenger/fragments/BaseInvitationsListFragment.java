package org.codepath.team10.charitychallenger.fragments;

import java.util.ArrayList;
import java.util.List;

import org.codepath.team10.charitychallenger.EventManager;
import org.codepath.team10.charitychallenger.ParseData;
import org.codepath.team10.charitychallenger.adapters.InvitationsAdapter;
import org.codepath.team10.charitychallenger.models.Invitation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.ListView;

public class BaseInvitationsListFragment extends Fragment{
	
	protected InvitationsAdapter invitationsAdapter;
	protected List<Invitation> invitations = new ArrayList<Invitation>();
	protected ParseData parseData = ParseData.getInstance();
	protected EventManager eventManager = EventManager.getInstance();
	
	protected ListView lvInvitations;
	
	public BaseInvitationsListFragment(){
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//invitationsAdapter = new InvitationsAdapter( getActivity(), invitations);
	}
		
	public void addInvitation(Invitation invitation){
		if( invitation != null ){
			invitations.add(invitation);
			invitationsAdapter.notifyDataSetChanged();
		}
	}
	public void addInvitationAt(int position, Invitation invitation){
		if( invitation != null && position >= 0 ){
			invitations.add(position, invitation);
			invitationsAdapter.notifyDataSetChanged();
		}
	}
	
	// make sure duplicates are not added
	public void addAllInvitations( List<Invitation> invites ){
		
		if( invites != null){
			for( Invitation invite : invites ){
				if( !invitations.contains(invite) ){
					invitations.add( invite);
				}
			}
			
			invitationsAdapter.notifyDataSetChanged();
		}
	}
}
