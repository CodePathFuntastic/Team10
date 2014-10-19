package org.codepath.team10.charitychallenger.activities;

import java.util.List;

import org.codepath.team10.charitychallenger.R;
import org.codepath.team10.charitychallenger.adapters.ChallengesViewAdapter;
import org.codepath.team10.charitychallenger.helper.ParseProxyObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class HomeActivity extends Activity {
	
	private ListView mlvChallenges;
	private ChallengesViewAdapter mChallengesAdapter; 
	private TextView mTvNotificationsBadge;
	private List<ParseObject> invitationsList; 
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
        mlvChallenges = (ListView) findViewById(R.id.lvListOfChallenges);
        mChallengesAdapter = new ChallengesViewAdapter(this, "Challenge");
        mlvChallenges.setAdapter(mChallengesAdapter);
        
        //OnItemClickListener
        mlvChallenges.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                    int position, long id) {
                Intent intent = new Intent(HomeActivity.this, ChallengeDetailsActivity.class);
                ParseObject parseObject = mChallengesAdapter.getItem(position);
                ParseProxyObject ppo = new ParseProxyObject(parseObject);
                intent.putExtra("parseObject", ppo);
                startActivity(intent);
            }
        });
        
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Invitation");
		query.whereEqualTo("receiver", "syed");
		query.whereEqualTo("status", 1);

		query.findInBackground(new FindCallback<ParseObject>() {
        public void done(List<ParseObject> receivedNotifications, ParseException e) {
        	int numberOfInvites = receivedNotifications.size();
        	mTvNotificationsBadge.setTag(numberOfInvites);
            if (numberOfInvites > 0) {
            	mTvNotificationsBadge.setVisibility(View.VISIBLE);
            	mTvNotificationsBadge.setText("" + numberOfInvites);
            	invitationsList = receivedNotifications;
            } else {
                Log.d("Error: ", e.getMessage());
            }
        }
        });
        
    }
    
    public void onChallengeDetails(View view){
    	Intent intent = new Intent(this, ChallengeDetailsActivity.class);
    	startActivity(intent);	
    }
	
    public boolean onCreateOptionsMenu(Menu menu) {
    	
    	getMenuInflater().inflate(R.menu.home_activity_menu, menu);

        RelativeLayout badgeLayout = (RelativeLayout) menu.findItem(R.id.badge).getActionView();
        mTvNotificationsBadge = (TextView) badgeLayout.findViewById(R.id.actionbar_notifcation_textview);
        mTvNotificationsBadge.setVisibility(View.GONE);
        
        ImageView ivBadge = (ImageView ) MenuItemCompat.getActionView(menu.findItem(R.id.badge)).findViewById(R.id.ivReceivedChallenges);
        
       // ivBadge.setTag( numberOfinvites );
        
        ivBadge.setOnClickListener(new OnClickListener() {	
        	@Override 
        	public void onClick(View view ) {
        		
        		int numInvitations = (Integer)mTvNotificationsBadge.getTag();
        		if(numInvitations > 0)
        		{
	        		Intent intent = null;
	        		// if only one invitation is available, directly open that invitation
	        		if( numInvitations == 1){
	        			intent = new Intent(HomeActivity.this, InvitationDetails.class);
	        			int id = invitationsList.get(0).getInt("challengeId");
	        			intent.putExtra("challengeId", id);
	        		}else if( numInvitations > 1 ){
	            		// if more that invitations are available, show all invitations, so that user can pick one
	        			//intent.putExtra("challengeId", invitationsList.get(0).getInt("challenge_id"));
	        			intent = new Intent(HomeActivity.this, AllInvitationsActivity.class);
	        		}
	                startActivity(intent);
        		}
        	}
        });
		return true;
    }
    
    private ParseQuery<ParseObject> create() {
		Log.d("HomeActivity: ", "Invitation query");
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Invitation");
		query.whereEqualTo("receiver", "syed");
		query.whereEqualTo("status", 1);
		return query;
	}
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		int id = item.getItemId();
//		if (id == R.id.badge) {
//			return true;
//		}
//		return super.onOptionsItemSelected(item);
//	}
}
