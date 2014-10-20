package org.codepath.team10.charitychallenger.activities;

import java.util.List;

import org.codepath.team10.charitychallenger.CharityChallengerApplication;
import org.codepath.team10.charitychallenger.fragments.MenuFragment;
import org.codepath.team10.charitychallenger.models.Invitation;
import org.codepath.team10.charitychallenger.models.User;
import org.codepath.team10.charitychallenger.receivers.ParsePushReceiver;

import android.app.Application;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

public class BaseActivity extends FragmentActivity {
	
	public static final String LOG_TAG = "org.codepath.team10.charitychallenger";
	private CharityChallengerApplication application;
	private ParsePushReceiver pushReceiver;
	private MenuFragment menufragment;
	
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
		
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		
		menufragment = new MenuFragment();
		fragmentTransaction.add(menufragment, "menu");
		fragmentTransaction.commit();
		application.registerUserSyncedListener(menufragment);
		
		
		
		pushReceiver = new ParsePushReceiver();
		
		pushReceiver.registerInvitationReceivedListener(menufragment);
		pushReceiver.registerInvitationCompleteListener(menufragment);
		pushReceiver.setUser( application.getUser());
		
		registerReceiver(pushReceiver, new IntentFilter("com.parse.push.intent.RECEIVE"));
		registerReceiver(pushReceiver, new IntentFilter("com.parse.push.intent.DELETE"));
		registerReceiver(pushReceiver, new IntentFilter("com.parse.push.intent.OPEN"));
		registerReceiver(pushReceiver, new IntentFilter("SENDPUSH"));
		
		application.registerUserSyncedListener(pushReceiver);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		pushReceiver.unregisterInvitationReceivedListener(menufragment);
		pushReceiver.unregisterInvitationCompleteListener(menufragment);
		unregisterReceiver(pushReceiver);
	}
	
	public List<Invitation> getInvitations(){
		return application.getAllInvitations();
	}
	public User getUser(){
		return application.getUser();
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}
}
