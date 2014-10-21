package org.codepath.team10.charitychallenger.activities;

import java.util.List;

import org.codepath.team10.charitychallenger.CharityChallengerApplication;
import org.codepath.team10.charitychallenger.EventManager;
import org.codepath.team10.charitychallenger.ParseData;
import org.codepath.team10.charitychallenger.R;
import org.codepath.team10.charitychallenger.fragments.MenuFragment;
import org.codepath.team10.charitychallenger.models.Invitation;
import org.codepath.team10.charitychallenger.models.User;
import org.codepath.team10.charitychallenger.receivers.ParsePushReceiver;

import android.app.Application;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

public class BaseActivity extends FragmentActivity {
	
	public static final String LOG_TAG = "org.codepath.team10.charitychallenger";
	private CharityChallengerApplication application;
	private ParsePushReceiver pushReceiver;
	private MenuFragment menufragment;
	
	EventManager eventManager;
	ParseData parseData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if( application == null ){
			Application app = getApplication();
			if( app instanceof CharityChallengerApplication){
				application = (CharityChallengerApplication) app;
			}
		}
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		eventManager = EventManager.getInstance();
		parseData = ParseData.getInstance();
		
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		
		menufragment = new MenuFragment();
		fragmentTransaction.add(menufragment, "menu");
		fragmentTransaction.commit();
		
		
		//application.registerUserSyncedListener(menufragment);
		eventManager.registerUserSyncedListener(menufragment);
		eventManager.registerInvitationCompletedListener(menufragment);
		eventManager.registerInvitationReceivedListener(menufragment);
		
		pushReceiver = new ParsePushReceiver();
				
		registerReceiver(pushReceiver, new IntentFilter("com.parse.push.intent.RECEIVE"));
		registerReceiver(pushReceiver, new IntentFilter("com.parse.push.intent.DELETE"));
		registerReceiver(pushReceiver, new IntentFilter("com.parse.push.intent.OPEN"));
		registerReceiver(pushReceiver, new IntentFilter("SENDPUSH"));	
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		eventManager.unregisterInvitationCompletedListener(menufragment);
		eventManager.unregisterInvitationReceivedListener(menufragment);
		unregisterReceiver(pushReceiver);
	}
	
	public List<Invitation> getInvitations(){
		return application.getAllInvitations();
	}
	
	public List<Invitation> getAcceptedInvitations(){
		return application.getAllAcceptedInvitations();
	}
	
	public User getUser(){
		return parseData.getUser();
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}
	
	 @Override
		public boolean onOptionsItemSelected(MenuItem item) {
			switch (item.getItemId()) {
		    case R.id.action_sent_challenges:
		    	startAllAcceptedActivity();	
		      break;
		      
		    default:
		    	return super.onOptionsItemSelected(item);
		    }

		    return true;
		}

	private void startAllAcceptedActivity() {
		Intent intent = new Intent(this, AllAcceptedActivity.class);
		startActivity(intent);
	}
	 
}
