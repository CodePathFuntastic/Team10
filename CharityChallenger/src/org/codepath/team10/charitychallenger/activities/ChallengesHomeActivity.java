package org.codepath.team10.charitychallenger.activities;

import org.codepath.team10.charitychallenger.R;
import org.codepath.team10.charitychallenger.fragments.ChallengesHomeFragment;
import org.codepath.team10.charitychallenger.fragments.ReceivedInvitationsFragment;
import org.codepath.team10.charitychallenger.fragments.SentInvitationsFragment;
import org.codepath.team10.charitychallenger.listeners.FragmentTabListener;


import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.os.Bundle;
import android.util.Log;

public class ChallengesHomeActivity extends BaseActivity {

	private ChallengesHomeFragment mChallengesHomeFragment;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_challenges);
		
		setupTabs();
		
		getSupportFragmentManager().executePendingTransactions();
		mChallengesHomeFragment = (ChallengesHomeFragment) getSupportFragmentManager().findFragmentByTag("home"); // fragment name is "home" here. be careful....
		Log.d("Fragment TAG: ", mChallengesHomeFragment.getTag());
	}

	private void setupTabs() {
		
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);
		
		Bundle bundle = new Bundle();
		bundle.putString("test", "test-string");
	
		Tab receivedTab = actionBar
				.newTab()
				.setText("Invitations")
				//.setIcon(R.drawable.ic_home)
				.setTag("ReceivedInvitationsFragment")
				.setTabListener(
					new FragmentTabListener<ReceivedInvitationsFragment>(R.id.flContainer, this, "received",
							ReceivedInvitationsFragment.class, bundle));

			
			Tab sentTab = actionBar
				.newTab()
				.setText("Sent")
				//.setIcon(R.drawable.ic_mentions)
				.setTag("SentInvitationsFragment")
				.setTabListener(
				    new FragmentTabListener<SentInvitationsFragment>(R.id.flContainer, this, "sent",
				    		SentInvitationsFragment.class, bundle ));
			
			Tab homeTab = actionBar
					.newTab()
					.setText("Home")
					//.setIcon(R.drawable.ic_mentions)
					.setTag("ChallengesHomeFragment")
					.setTabListener(
					    new FragmentTabListener<ChallengesHomeFragment>(R.id.flContainer, this, "home",
					    		ChallengesHomeFragment.class, bundle ));
			
			actionBar.addTab(homeTab);
			actionBar.addTab(receivedTab);
			actionBar.addTab(sentTab);
			actionBar.selectTab(homeTab);
			
	}
	
	@Override
	public void onBackPressed() {
	    // should not go back....
	    return;
	}
}
