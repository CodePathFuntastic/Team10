package org.codepath.team10.charitychallenger.fragments;

import org.codepath.team10.charitychallenger.R;
import org.codepath.team10.charitychallenger.adapters.InvitationsAdapter;
import org.codepath.team10.charitychallenger.listeners.InvitationsLoadedListener;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ReceivedInvitationsFragment extends BaseInvitationsListFragment implements InvitationsLoadedListener{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		invitationsAdapter = new InvitationsAdapter( getActivity(),false, invitations);
		populateReceivedInvitations();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		eventManager.registerInvitationLoadedListener(this);
		
		super.onCreateView(inflater, container, savedInstanceState);
		
		View view = inflater.inflate(R.layout.fragment_all_invitations, container, false); 
		ListView lvInvitations = (ListView) view.findViewById(R.id.lvFragAllInvitations);
		lvInvitations.setAdapter(invitationsAdapter);
		return view;
	}

	private void populateReceivedInvitations() {
		if( parseData.getReceivedInvitations().size() == 0 ){
			eventManager.getReceivedOpenInvitations();
		}else{
			// TODO: filter only open invitations
			invitations.addAll(parseData.getReceivedInvitations());
		}
	}

	@Override
	public void onSuccess() {
		// TODO: filter only open invitations
		invitations.addAll(parseData.getReceivedInvitations());
		invitationsAdapter.notifyDataSetChanged();
	}

	@Override
	public void onFailure(String message, Throwable error) {
	}
}
