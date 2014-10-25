package org.codepath.team10.charitychallenger.fragments;

<<<<<<< HEAD
import java.util.List;

import org.codepath.team10.charitychallenger.R;
import org.codepath.team10.charitychallenger.listeners.InvitationsLoadedListener;
import org.codepath.team10.charitychallenger.models.Invitation;
=======
import org.codepath.team10.charitychallenger.R;
import org.codepath.team10.charitychallenger.listeners.InvitationsLoadedListener;
>>>>>>> FETCH_HEAD

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
<<<<<<< HEAD

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
=======
>>>>>>> FETCH_HEAD


public class ReceivedInvitationsFragment extends BaseInvitationsListFragment implements InvitationsLoadedListener{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		populateReceivedInvitations();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
<<<<<<< HEAD
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
=======
			@Nullable ViewGroup container, 
			@Nullable Bundle savedInstanceState) {
		
>>>>>>> FETCH_HEAD
		super.onCreateView(inflater, container, savedInstanceState);
		
		eventManager.registerInvitationLoadedListener(this);
		
		super.onCreateView(inflater, container, savedInstanceState);
		
		View view = inflater.inflate(R.layout.fragment_all_invitations, container, false); 
		ListView lvInvitations = (ListView) view.findViewById(R.id.lvFragAllInvitations);
		lvInvitations.setAdapter(invitationsAdapter);
		
		return view;
<<<<<<< HEAD
		
		
=======
>>>>>>> FETCH_HEAD
	}

	private void populateReceivedInvitations() {
		if( parseData.getReceivedInvitations().size() == 0 ){
			// fire a network call to load the sent invitations for the "user"
			eventManager.getReceivedInvitations();
<<<<<<< HEAD
			
=======
>>>>>>> FETCH_HEAD
		}else{
			invitations.addAll(parseData.getReceivedInvitations());
		}
	}

	@Override
	public void onSuccess() {
<<<<<<< HEAD
		// TODO Auto-generated method stub
		invitations.addAll(parseData.getReceivedInvitations());
=======
		invitations.addAll( parseData.getSentInvitations());
>>>>>>> FETCH_HEAD
		invitationsAdapter.notifyDataSetChanged();
	}

	@Override
	public void onFailure(String message, Throwable error) {
		// TODO Auto-generated method stub
		
	}
}
