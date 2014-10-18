package org.codepath.team10.charitychallenger;

import java.util.Collection;

import org.codepath.team10.charitychallenger.clients.TwitterRestClient;
import org.codepath.team10.charitychallenger.models.Challenge;
import org.codepath.team10.charitychallenger.models.Invitation;
import org.codepath.team10.charitychallenger.models.Organization;
import org.codepath.team10.charitychallenger.models.Picture;
import org.codepath.team10.charitychallenger.models.PictureUrl;
import org.codepath.team10.charitychallenger.models.User;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.facebook.model.GraphUser;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseRole;
import com.parse.SaveCallback;
import com.parse.SendCallback;

public class CharityChallengerApplication extends Application {
	
	private static Context context;

	@Override
	public void onCreate() {
		super.onCreate();
		this.context = this;
		
		initializeParseAndLocalDB();
		
		//initializeFb();
		
		ActiveAndroid.initialize(this);
		
		//ParseTwitterUtils.initialize( Constants.TWITTER_CONSUMER_KEY, Constants.TWITTER_CONSUMER_SECRET);
	}
	
    private Collection<GraphUser> selectedUsers;

    public Collection<GraphUser> getSelectedUsers() {
        return selectedUsers;
    }

    public void setSelectedUsers(Collection<GraphUser> selectedUsers) {
        this.selectedUsers = selectedUsers;
    }
	
	private void initializeFb() {
		// Set your Facebook App Id in strings.xml
		ParseFacebookUtils.initialize( getString(R.string.facebook_app_id));

	}

	public void initializeParseAndLocalDB(){

		// enable local data store
		Parse.enableLocalDatastore(this);
		
		// register all the modles
		
		ParseObject.registerSubclass(Challenge.class);
		ParseObject.registerSubclass(Invitation.class);
		ParseObject.registerSubclass(Organization.class);
		ParseObject.registerSubclass(Picture.class);
		ParseObject.registerSubclass(PictureUrl.class);
		ParseObject.registerSubclass(User.class);

		// initialize parse SDK
		//Parse.initialize(this, "9e0wpyP9qg9UvX1g2cz65Qs2h2EkUkno88bzctFL", "PchSOljUdwS9F1bHsmotb6Aqv4epxH154UFbVggx");
		Parse.initialize(this, Constants.PARSE_APPLICATION_ID, Constants.PARSE_CLIENT_KEY );
		ParseInstallation.getCurrentInstallation().saveInBackground();
		Parse.setLogLevel(Parse.LOG_LEVEL_VERBOSE);
		
		// Enable push notifications
		ParsePush.subscribeInBackground("", new SaveCallback() {
			  @Override
			  public void done(ParseException e) {
			    if (e == null) {
			      Log.d("org.codepath.team10.charitychallenger", "successfully subscribed to the broadcast channel.");
			    } else {
			      Log.e("org.codepath.team10.charitychallenger", "failed to subscribe for push", e);
			    }
			  }
			});
		
		// send a test push notification
		ParsePush push = new ParsePush();
		push.setMessage("test message");
		push.setChannel("");
		push.sendInBackground( new SendCallback(){

			@Override
			public void done(ParseException paramParseException) {
				if(paramParseException != null){
					Log.e("org.codepath.team10.charitychallenger", "Push Exception", paramParseException);
				}else{
					Log.d("org.codepath.team10.charitychallenger", "Push Done");
				}
				
			}});
		
		
		final ParseACL roleACL = new ParseACL();
		roleACL.setPublicReadAccess(true);
		final ParseRole role = new ParseRole("Engineer", roleACL);
				
//		OrganizationUploader.upload( this);
//		ChallengeUploader.upload(this);
//		UserUploader.upload(this);
//		InvitationUploader.upload(this);	
	}
	
	public static TwitterRestClient getRestClient() {
		return (TwitterRestClient) TwitterRestClient.getInstance(TwitterRestClient.class, CharityChallengerApplication.context);
	}
}
