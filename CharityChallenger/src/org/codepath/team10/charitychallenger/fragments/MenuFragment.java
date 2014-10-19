package org.codepath.team10.charitychallenger.fragments;

import java.util.List;

import org.codepath.team10.charitychallenger.R;
import org.codepath.team10.charitychallenger.activities.AllInvitationsActivity;
import org.codepath.team10.charitychallenger.activities.BaseActivity;
import org.codepath.team10.charitychallenger.activities.InvitationDetails;
import org.codepath.team10.charitychallenger.models.Invitation;

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

public class MenuFragment extends Fragment {
	
	BaseActivity activity;
	TextView mTvNotificationsBadge;

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
	        		Intent intent = null;
	        		
	        		// if only one invitation is available, directly open that invitation
	        		if( numInvitations == 1){
	        			
	        			intent = new Intent(  getActivity(), InvitationDetails.class);
	        			int id = activity.getInvitations().get(0).getInt("challengeId");
	        			intent.putExtra("challengeId", id);
	        		
	        		}else if( numInvitations > 1 ){
	        			
	            		// if more that invitations are available, show all invitations, 
	        			// so that user can pick one
	        			intent = new Intent( getActivity(), AllInvitationsActivity.class);
	        		}
	                startActivity(intent);
        		}
        	}
        });
        
        // fire the background task to get the list of invitations for the user.
        // this should happen only once, when the activity loads for first time.
        if( activity.getInvitations() == null || 
        		activity.getInvitations().size() == 0 ){
        	
          ParseQuery<Invitation> query = ParseQuery.getQuery(Invitation.class);
          
          // TODO : get the current from base activity
          query.whereEqualTo("receiver", "syed");
          query.whereEqualTo("status", 1);
    
          query.findInBackground( new FindCallback<Invitation>(){

			@Override
			public void done(List<Invitation> invites,
								ParseException e) {
				if( e == null){
					if( invites != null && invites.size() > 0){
						activity.getInvitations().addAll(invites);
						int numberofInvites = activity.getInvitations().size();
						if( numberofInvites > 0 ){
							mTvNotificationsBadge.setVisibility(View.VISIBLE);
		                	mTvNotificationsBadge.setText("" + numberofInvites);
						}
					}
				}else{
					Log.d( activity.LOG_TAG , e.getMessage());
				}
			}
        	  
          });
 
        }

    }

}
