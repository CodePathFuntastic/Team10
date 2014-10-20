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
		if( parseData.getReceivedInvitations().size() == 0 ){
			// fire a network call to load the sent invitations for the "user"
			if( parseData.getUser().getFacebookId() != null ){
				String userId = parseData.getUser().getFacebookId() ;
				InvitationQuery.getSentInvitations(userId, new FindCallback<Invitation>(){

					@Override
					public void done(List<Invitation> invites, ParseException e) {
						if( e == null ){
							// save the data in the data cache
							parseData.getSentInvitations().addAll(invites);
							addAllInvitations( parseData.getSentInvitations());
						}else{
							Log.e(BaseActivity.LOG_TAG, "Unable to get sent invitations", e);
						}
					}});
			}

		}else{
			addAllInvitations( parseData.getSentInvitations());
		}
	}
}
