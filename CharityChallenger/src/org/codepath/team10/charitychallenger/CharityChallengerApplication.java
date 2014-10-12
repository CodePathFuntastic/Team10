package org.codepath.team10.charitychallenger;

import org.codepath.team10.charitychallenger.models.Picture;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class CharityChallengerApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		
		initializeParseAndLocalDB();
		
		initializeFb();
	}
	
	private void initializeFb() {
		// Set your Facebook App Id in strings.xml
		ParseFacebookUtils.initialize( getString(R.string.facebook_app_id));

	}

	public void initializeParseAndLocalDB(){

		// enable local data store
		Parse.enableLocalDatastore(this);
		
		// register all the modles
		ParseObject.registerSubclass(Picture.class);


		// initialize parse SDK
		Parse.initialize(this, "9e0wpyP9qg9UvX1g2cz65Qs2h2EkUkno88bzctFL", "PchSOljUdwS9F1bHsmotb6Aqv4epxH154UFbVggx");

		
		ParseUser.enableAutomaticUser();

		ParseACL defaultACL = new ParseACL();

		defaultACL.setPublicReadAccess(true);

		ParseACL.setDefaultACL(defaultACL, true);
		
	}

}
