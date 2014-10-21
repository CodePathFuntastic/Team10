package org.codepath.team10.charitychallenger.fragments;

import java.util.List;

import org.codepath.team10.charitychallenger.activities.BaseActivity;
import org.codepath.team10.charitychallenger.models.Invitation;
import org.codepath.team10.charitychallenger.queries.InvitationQuery;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;


public class ReceivedInvitationsFragment extends BaseInvitationsListFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		populateReceivedInvitations();
	}
	
//	@Override
//	public View onCreateView(LayoutInflater inflater,
//			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//		return super.onCreateView(inflater, container, savedInstanceState);
//	}

	private void populateReceivedInvitations() {
		//if( parseData.getReceivedInvitations().size() == 0 ){
			// fire a network call to load the sent invitations for the "user"
			String userId = parseData.getUser().getFacebookId();
			if( userId != null){
				
				ParseQuery<Invitation> query = ParseQuery.getQuery(Invitation.class);
				query.whereEqualTo("receiver", userId);
				
				try {
					List<Invitation> invites = query.find();
					if( invites != null ){
						addAllInvitations(invites);
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//query.clearCachedResult();
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
			}
//		}else{
//			//addAllInvitations(parseData.getReceivedInvitations());
//		}
	}
}
