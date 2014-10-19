package org.codepath.team10.charitychallenger.activities;

import java.util.List;

import org.codepath.team10.charitychallenger.CharityChallengerApplication;
import org.codepath.team10.charitychallenger.fragments.MenuFragment;
import org.codepath.team10.charitychallenger.models.Invitation;
import org.codepath.team10.charitychallenger.models.User;

import android.app.Application;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

public class BaseActivity extends FragmentActivity {
	
	public static final String LOG_TAG = "org.codepath.team10.charitychallenger";
	private CharityChallengerApplication application;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if( application == null ){
			Application app = getApplication();
			if( app instanceof CharityChallengerApplication){
				application = (CharityChallengerApplication) app;
			}
		}
		
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		
		MenuFragment menufragment = new MenuFragment();
		fragmentTransaction.add(menufragment, "menu");
		fragmentTransaction.commit();
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
