package org.codepath.team10.charitychallenger.activities;

import org.codepath.team10.charitychallenger.R;
import org.codepath.team10.charitychallenger.fragments.ReceivedInvitationsFragment;
import org.codepath.team10.charitychallenger.fragments.SentInvitationsFragment;
import org.codepath.team10.charitychallenger.listeners.FragmentTabListener;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.os.Bundle;

public class AllInvitationsFragmentActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all_invitations_fragment);
		
		setupTabs();
	}

	private void setupTabs() {
		
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);
		
		Bundle bundle = new Bundle();
		bundle.putString("test", "test-string");
	
		Tab tab1 = actionBar
				.newTab()
				.setText("Received")
				//.setIcon(R.drawable.ic_home)
				.setTag("ReceivedInvitationsFragment")
				.setTabListener(
					new FragmentTabListener<ReceivedInvitationsFragment>(R.id.flContainer, this, "first",
							ReceivedInvitationsFragment.class, bundle));

			
			Tab tab2 = actionBar
				.newTab()
				.setText("Sent")
				//.setIcon(R.drawable.ic_mentions)
				.setTag("SentInvitationsFragment")
				.setTabListener(
				    new FragmentTabListener<SentInvitationsFragment>(R.id.flContainer, this, "second",
				    		SentInvitationsFragment.class, bundle ));
			
			actionBar.addTab(tab2);
			actionBar.selectTab(tab2);
			actionBar.addTab(tab1);
	}
}
