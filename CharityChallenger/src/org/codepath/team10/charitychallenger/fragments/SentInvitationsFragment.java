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
		//if( parseData.getSentInvitations().size() == 0 ){
			// fire a network call to load the sent invitations for the "user"
			String userId = parseData.getUser().getFacebookId();
			if( userId != null ){
								
				InvitationQuery.getSentInvitations(userId, new FindCallback<Invitation>(){

					@Override
					public void done(List<Invitation> list, ParseException e) {
						if( e == null ){
								// save the data in the data cache
								//parseData.getReceivedInvitations().addAll(list);
								addAllInvitations( list);
						}else{
							Log.e(BaseActivity.LOG_TAG, "Unable to retrive sent invites", e);
						}
					}
				});
			}

//		}else{
//			addAllInvitations( parseData.getSentInvitations());
//		}
	}
}
