package org.codepath.team10.charitychallenger.fragments;

import java.util.List;

import org.codepath.team10.charitychallenger.R;
import org.codepath.team10.charitychallenger.activities.BaseActivity;
import org.codepath.team10.charitychallenger.listeners.InvitationsLoadedListener;
import org.codepath.team10.charitychallenger.models.Invitation;
import org.codepath.team10.charitychallenger.queries.InvitationQuery;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;


public class ReceivedInvitationsFragment extends BaseInvitationsListFragment implements InvitationsLoadedListener{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		populateReceivedInvitations();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, 
			@Nullable Bundle savedInstanceState) {
		
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
			// fire a network call to load the sent invitations for the "user"
			eventManager.getReceivedInvitations();
//			String userId = parseData.getUser().getFacebookId();
//			if( userId != null){
//				
//				ParseQuery<Invitation> query = ParseQuery.getQuery(Invitation.class);
//				query.whereEqualTo("receiver", userId);
//				
//				try {
//					List<Invitation> invites = query.find();
//					if( invites != null ){
//						addAllInvitations(invites);
//					}
//				} catch (ParseException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				//query.clearCachedResult();
//				query.findInBackground( new FindCallback<Invitation>(){
//
//					@Override
//					public void done(List<Invitation> list,
//										ParseException e) {
//						if( e == null ){
//							//parseData.getReceivedInvitations().addAll(list);
//							addAllInvitations(list);
//						}
//					}
//				});
//			}
		}else{
			//addAllInvitations(parseData.getReceivedInvitations());
			invitations.addAll(parseData.getReceivedInvitations());
		}
	}

	@Override
	public void onSuccess() {
		invitations.addAll(parseData.getSentInvitations());
		invitationsAdapter.notifyDataSetChanged();
	}

	@Override
	public void onFailure(String message, Throwable error) {
		// TODO Auto-generated method stub
		
	}
}
