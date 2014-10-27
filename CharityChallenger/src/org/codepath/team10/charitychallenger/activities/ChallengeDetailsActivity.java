package org.codepath.team10.charitychallenger.activities;

import java.util.ArrayList;
import java.util.List;

import org.codepath.team10.charitychallenger.CharityChallengerApplication;
import org.codepath.team10.charitychallenger.R;
import org.codepath.team10.charitychallenger.models.Challenge;
import org.codepath.team10.charitychallenger.models.Invitation;
import org.codepath.team10.charitychallenger.models.InvitationStatusEnum;
import org.codepath.team10.charitychallenger.models.User;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.squareup.picasso.Picasso;

public class ChallengeDetailsActivity extends BaseActivity {

    private static final int PICK_FRIENDS_ACTIVITY = 1;
    private Button pickFriendsButton;
    private TextView resultsTextView;
    private TextView tvChallengeTitle;
    private UiLifecycleHelper lifecycleHelper;
    private ListView lvSelectedFriends;
    private Button btnInviteNow;
    
    boolean pickFriendsWhenSessionOpened;
    private ImageView ivChallengeImage;
    private Challenge challenge;
    
    private CharityChallengerApplication application;
    
    private ArrayAdapter<String> friendsAdapter;
    private ArrayList<String> friends;
    private ArrayList<User> friends1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_challenge_details);
		//getActionBar().setDisplayHomeAsUpEnabled(true);
		
		ivChallengeImage = (ImageView)findViewById(R.id.ivChallengeImage);
	    tvChallengeTitle = (TextView) findViewById(R.id.tvChallengeTitle);
	    lvSelectedFriends = (ListView) findViewById(R.id.lvSelectedFriends);
	    btnInviteNow = (Button) findViewById(R.id.btnInviewNow);
	    
	    if( getApplication() instanceof CharityChallengerApplication ){
	    	application = (CharityChallengerApplication) getApplication();
	    }
		
		Intent intent = getIntent();
		
		challenge = (Challenge) intent.getParcelableExtra("challenge");
		String name = challenge.getName();
		List<String> arrayList = challenge.getChallengesPictureUrls();
		
		Log.d(BaseActivity.LOG_TAG, "Challenge = " + challenge.toString() );
		Log.d(BaseActivity.LOG_TAG, "Testing");
		
		tvChallengeTitle.setText(name);
		if (arrayList != null && arrayList.size() > 0) {
	        Picasso.with(this).load(arrayList.get(0)).into(ivChallengeImage);
		}
		
		Log.v( BaseActivity.LOG_TAG, String.format("challenge name: %s", name ));
		
		resultsTextView = (TextView) findViewById(R.id.tvSelectedFriends);
        pickFriendsButton = (Button) findViewById(R.id.btnSelectFriends);
        pickFriendsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onClickPickFriends();
            }
        });

        lifecycleHelper = new UiLifecycleHelper(this, new Session.StatusCallback() {
            @Override
            public void call(Session session, SessionState state, Exception exception) {
                onSessionStateChanged(session, state, exception);
            }
        });
        lifecycleHelper.onCreate(savedInstanceState);

        ensureOpenSession();
        
        friends = new ArrayList<String>();
        friendsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, friends);
	}
	
	   public void onActivityResult(int requestCode, int resultCode, Intent data) {
	        switch (requestCode) {
	            case PICK_FRIENDS_ACTIVITY:
	                displaySelectedFriends(resultCode, data);
	                break;
	            default:
	                Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
	                break;
	        }
	    }

	    private boolean ensureOpenSession() {
	        if (Session.getActiveSession() == null ||
	                !Session.getActiveSession().isOpened()) {
	            Session.openActiveSession(this, true, new Session.StatusCallback() {
	                @Override
	                public void call(Session session, SessionState state, Exception exception) {
	                    onSessionStateChanged(session, state, exception);
	                }
	            });
	            return false;
	        }
	        return true;
	    }

	    private void onSessionStateChanged(Session session, SessionState state, Exception exception) {
	        if (pickFriendsWhenSessionOpened && state.isOpened()) {
	            pickFriendsWhenSessionOpened = false;

	            startPickFriendsActivity();
	        }
	    }

	    private void displaySelectedFriends(int resultCode, Intent data) {
	        String results = "";
	        	        
	        friends1 = data.getParcelableArrayListExtra("array");
	        if( friends1 != null && friends1.size() > 0){
	        	//Log.d(BaseActivity.LOG_TAG, "Users : " + array );
	        	
	        	friends.clear();
	        	
	        	for( User u : friends1){
	        		friends.add(u.getName());
	        	}
	        	
	        	// now display the selected friends in the list view
		        lvSelectedFriends.setAdapter(friendsAdapter);
		        friendsAdapter.notifyDataSetChanged();
		        
		        btnInviteNow.setEnabled(true);
	        }	        
	    }

	    private void onClickPickFriends() {
	        startPickFriendsActivity();
	    }

	    private void startPickFriendsActivity() {
	        if (ensureOpenSession()) {
	            application.setSelectedUsers(null);

	            Intent intent = new Intent(this, InviteFriendsActivity.class);
	            intent.putExtra("challenge", challenge);

	            InviteFriendsActivity.populateParameters(intent, null, true, true);
	            startActivityForResult(intent, PICK_FRIENDS_ACTIVITY);
	        } else {
	            pickFriendsWhenSessionOpened = true;
	        }
	    }
	
	public void onClickSelectFriends( View view){
		Intent intent = new Intent(this, InviteFriendsActivity.class);
		startActivity(intent);
	}
	
	public void onClickInvite(View view){
//		Intent intent = new Intent(this, DonateActivity.class);
//        intent.putExtra("challenge", challenge);
//		startActivity(intent);
		
		// send an invitation to selected friends
		List<Invitation> invitations = new ArrayList<Invitation>();
		if( friends != null && friends.size()>0 ){
			for( User f : friends1){
				Invitation i = new Invitation();
				i.setReceiver(f.getFacebookId());
				i.setSender( parseData.getUser().getFacebookId());
				
				// TODO: challenge should have an amount
				i.setAmount( 10.00);
				i.setStatus(InvitationStatusEnum.OPEN.ordinal());
				i.setOpened(false);
				i.setChallengeId(challenge.getChallengeId());
				
				invitations.add(i);
			}
			application.sendInvitations( invitations);
		}
		
		setResult(RESULT_OK);
		finish();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == android.R.id.home) {
			this.finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
