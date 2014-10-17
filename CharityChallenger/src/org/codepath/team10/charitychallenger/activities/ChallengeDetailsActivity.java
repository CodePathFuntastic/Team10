package org.codepath.team10.charitychallenger.activities;

import java.util.ArrayList;
import java.util.Collection;

import org.codepath.team10.charitychallenger.CharityChallengerApplication;
import org.codepath.team10.charitychallenger.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;

public class ChallengeDetailsActivity extends FragmentActivity {

    private static final int PICK_FRIENDS_ACTIVITY = 1;
    private Button pickFriendsButton;
    private TextView resultsTextView;
    private UiLifecycleHelper lifecycleHelper;
    boolean pickFriendsWhenSessionOpened;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_challenge_details);
		
        resultsTextView = (TextView) findViewById(R.id.tvSelectedFriends);
        pickFriendsButton = (Button) findViewById(R.id.btnInvite);
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
	}
	
	   public void onActivityResult(int requestCode, int resultCode, Intent data) {
	        switch (requestCode) {
	            case PICK_FRIENDS_ACTIVITY:
	                displaySelectedFriends(resultCode);
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

	    private void displaySelectedFriends(int resultCode) {
	        String results = "";
	        CharityChallengerApplication application = (CharityChallengerApplication) getApplication();

	        Collection<GraphUser> selection = application.getSelectedUsers();
	        if (selection != null && selection.size() > 0) {
	            ArrayList<String> names = new ArrayList<String>();
	            for (GraphUser user : selection) {
	                names.add(user.getName());
	            }
	            results = TextUtils.join(", ", names);
	        } else {
	            results = "<No friends selected>";
	        }

	        resultsTextView.setText(results);
	    }

	    private void onClickPickFriends() {
	        startPickFriendsActivity();
	    }

	    private void startPickFriendsActivity() {
	        if (ensureOpenSession()) {
	        	CharityChallengerApplication application = (CharityChallengerApplication) getApplication();
	            application.setSelectedUsers(null);

	            Intent intent = new Intent(this, InviteFriendsActivity.class);

	            InviteFriendsActivity.populateParameters(intent, null, true, true);
	            startActivityForResult(intent, PICK_FRIENDS_ACTIVITY);
	        } else {
	            pickFriendsWhenSessionOpened = true;
	        }
	    }
	
	public void onClickInvite( View view){
		Intent intent = new Intent(this, InviteFriendsActivity.class);
		startActivity(intent);
	}
	
	public void onClickDonate(View view){
		Intent intent = new Intent(this, DonateActivity.class);
		startActivity(intent);
	}
}
