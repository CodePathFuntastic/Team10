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

public class SentInvitationsFragment extends BaseInvitationsListFragment implements InvitationsLoadedListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		invitationsAdapter = new InvitationsAdapter( getActivity(),true, invitations);
		populateSentInvitations();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		
		eventManager.registerInvitationLoadedListener(this);
		
		super.onCreateView(inflater, container, savedInstanceState);
		
		View view = inflater.inflate(R.layout.fragment_all_invitations, container, false); 
		ListView lvInvitations = (ListView) view.findViewById(R.id.lvFragAllInvitations);
		lvInvitations.setAdapter(invitationsAdapter);
		
		return view;
	}
	
	@Override
	public void onDestroyView() {
		eventManager.unregisterInvitationLoadedListener(this);
		super.onDestroyView();
	}
	
	private void populateSentInvitations() {
		if( parseData.getSentInvitations().size() == 0 ){
			// fire a network call to load the sent invitations for the "user"
			eventManager.getSentInvitations();
			
		}else{
			invitations.addAll(parseData.getSentInvitations());
		}
	}

	@Override
	public void onSuccess() {
		invitations.addAll(parseData.getSentInvitations());
		invitationsAdapter.notifyDataSetChanged();
	}

	@Override
	public void onFailure(String message, Throwable error) {
		
	}
}
