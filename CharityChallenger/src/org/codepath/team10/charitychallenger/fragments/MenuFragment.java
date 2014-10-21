package org.codepath.team10.charitychallenger.fragments;

import java.util.List;

import org.codepath.team10.charitychallenger.R;
import org.codepath.team10.charitychallenger.activities.AllInvitationsActivity;
import org.codepath.team10.charitychallenger.activities.AllInvitationsFragmentActivity;
import org.codepath.team10.charitychallenger.activities.BaseActivity;
import org.codepath.team10.charitychallenger.activities.InvitationDetails;
import org.codepath.team10.charitychallenger.listeners.InvitationCompletedListener;
import org.codepath.team10.charitychallenger.listeners.InvitationReceivedListener;
import org.codepath.team10.charitychallenger.listeners.UserSynchedListener;
import org.codepath.team10.charitychallenger.models.Challenge;
import org.codepath.team10.charitychallenger.models.Invitation;
import org.codepath.team10.charitychallenger.models.InvitationStatusEnum;
import org.codepath.team10.charitychallenger.models.User;
import org.codepath.team10.charitychallenger.queries.ChallengeQueries;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

public class MenuFragment extends Fragment implements UserSynchedListener, InvitationReceivedListener, InvitationCompletedListener {
	
	BaseActivity activity;
	TextView mTvNotificationsBadge;
	String username;
	boolean hasNewInvitationArrived = false;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        
        Activity a = getActivity();
        if( a instanceof BaseActivity ){
        	activity = (BaseActivity) a;
        }
        
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    	super.onCreateOptionsMenu(menu, inflater);
    	
    	inflater.inflate(R.menu.home_activity_menu, menu);

        RelativeLayout badgeLayout = (RelativeLayout) menu.findItem(R.id.badge).getActionView();
        mTvNotificationsBadge = (TextView) badgeLayout.findViewById(R.id.actionbar_notifcation_textview);
        mTvNotificationsBadge.setVisibility(View.GONE);
        
        ImageView ivBadge = (ImageView ) MenuItemCompat.getActionView(menu.findItem(R.id.badge)).findViewById(R.id.ivReceivedChallenges);
       
        
        ivBadge.setOnClickListener(new OnClickListener() {
        	
        	@Override 
        	public void onClick(View view ) {
        		
        		//int numInvitations = (Integer) mTvNotificationsBadge.getTag();
        		int numInvitations = activity.getInvitations().size();
        		if(numInvitations > 0)
        		{
	        		// if only one invitation is available, directly open that invitation
	        		if( numInvitations == 1){
	        			
//	        			intent = new Intent(getActivity(), InvitationDetails.class);
//	        			Invitation invitation = (Invitation)activity.getInvitations().get(0);
//	        			intent.putExtra("invitation", invitation);
	        			final Intent intent = new Intent(getActivity(), InvitationDetails.class);
	        	        Invitation invitation = (Invitation)activity.getInvitations().get(0);
	        	        intent.putExtra("invitation", invitation);

	        	        ChallengeQueries.getChallengeById( invitation.getChallengeId(), 
	        	        		new FindCallback<Challenge>(){
	        	        		@Override
	        	        		public void done(
	        	        				List<Challenge> challenges,
	        	        				ParseException e) {
	        	        				if( e == null ){
	        	        					// it should be only one
	        	        					if( challenges.size()>0 ){
	        	        						Challenge challenge = challenges.get(0);
	        	        						intent.putExtra("challenge", challenge);
	        	        						getActivity().startActivity(intent);
	        	        					}
	        	        				}
	        	        		}
	        	        });
	        		}else if( numInvitations > 1 ){
	        			
	            		// if more that invitations are available, show all invitations, 
	        			// so that user can pick one
	        			//intent = new Intent( getActivity(), AllInvitationsFragmentActivity.class);
	        			Intent intent = new Intent( getActivity(), AllInvitationsActivity.class);
	        			//intent = new Intent( getActivity(), AllInvitationsFragmentActivity.class);	
	        			startActivity(intent);
	        		}
        		}
        	}
        });
        
        // fire the background task to get the list of invitations for the user.
        // this should happen only once, when the activity loads for first time.
        if( activity.getInvitations().size() == 0 &&
        	username != null ){
        	updateInvitationBadge();
        }
        
        if( activity.getInvitations().size() > 0){
        	displayInvitaionBadge();
        }
    }

    public void displayInvitaionBadge(){
    	int numberofInvites = activity.getInvitations().size();
		if( numberofInvites > 0 ){
			mTvNotificationsBadge.setVisibility(View.VISIBLE);
        	mTvNotificationsBadge.setText("" + numberofInvites);
		}
    }
    
    public void updateInvitationBadge(){
        ParseQuery<Invitation> query = ParseQuery.getQuery(Invitation.class);
        
        String userId = activity.getUser().getFacebookId();
        query.whereEqualTo("receiver", userId);
        query.whereEqualTo("status", InvitationStatusEnum.OPEN.ordinal() );
  
        query.findInBackground( new FindCallback<Invitation>(){

			@Override
			public void done(List<Invitation> invites,
								ParseException e) {
				if( e == null){
					if( invites != null && invites.size() > 0){
						activity.getInvitations().addAll(invites);
						displayInvitaionBadge();
					}
				}else{
					Log.d( activity.LOG_TAG , e.getMessage());
				}
			}
        });
    }
    
    // TODO: display RED for new invites, BLUE for old invites
	private void updateInvitationBadge( Invitation invite) {
		List<Invitation> invites = activity.getInvitations();
		if( !invites.contains(invite) ){
			invites.add(invite);
		}
		
		int newinvites =0;
		for( Invitation i : invites ){
			if( i.isOpened() == false ){
				newinvites++;
			}
		}
		mTvNotificationsBadge.setVisibility(View.VISIBLE);
    	mTvNotificationsBadge.setText("" + newinvites);
	}

    
	@Override
	public void onSync(User user) {
		if( username == null ){
			username = user.getName();
		}
		
		if( activity.getInvitations().size() == 0 ){
			updateInvitationBadge();
		}
	}

	@Override
	public void onReceive(Invitation invitation) {
		updateInvitationBadge(invitation);
	}

	@Override
	public void onComplete(Invitation invitation) {
		updateInvitationBadge(invitation);
	}


}
