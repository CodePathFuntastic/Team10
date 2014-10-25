package org.codepath.team10.charitychallenger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.codepath.team10.charitychallenger.clients.ParseRestClient;
import org.codepath.team10.charitychallenger.clients.TwitterRestClient;
import org.codepath.team10.charitychallenger.listeners.UserSynchedListener;
import org.codepath.team10.charitychallenger.models.Challenge;
import org.codepath.team10.charitychallenger.models.Invitation;
import org.codepath.team10.charitychallenger.models.Organization;
import org.codepath.team10.charitychallenger.models.Picture;
import org.codepath.team10.charitychallenger.models.PictureUrl;
import org.codepath.team10.charitychallenger.models.User;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.facebook.model.GraphUser;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseRole;
import com.parse.SaveCallback;
import com.parse.SendCallback;

public class CharityChallengerApplication extends Application {
	
	public static final String LOG_TAG = "org.codepath.team10.charitychallenger";
	public static final String MAIN_CHANNEL = "MAIN_CHANNEL";
	public static final String INVITATION_RECEIVE = "INVITATION_RECEIVE";
	public static final String INVITATION_COMPLETE = "INVITATION_COMPLETE";
	
	private static Context context;
	
	// maintain a list of listeners to send events system wide.
	// these listeners registered by various activities during onCreate
	/* BEGIN : ALL this DATA should be saved in ParseData Singleton Object */
	private List<UserSynchedListener> userSyncedListeners = new ArrayList<UserSynchedListener>();
	private List<Invitation> invitations = new ArrayList<Invitation>();
	
	private List<Invitation> acceptedInvitations = new ArrayList<Invitation>();
	//private User user;
	private Collection<GraphUser> selectedUsers=null;
	/* END : ALL this DATA should be saved in ParseData Singleton Object */
	
	// all data needed for all fragments/activies to be stored here
	private ParseData parseData;
	// all event listeners to be managed by event manager
	private EventManager eventManager;
	
	@Override
	public void onCreate() {
		super.onCreate();
		this.context = this;

		parseData = ParseData.getInstance();
		eventManager = EventManager.getInstance();

		initializeParseAndLocalDB();
		
		initParseNotificationChannels();
		
		//initializeFb();
		
		ActiveAndroid.initialize(this);
		
		initImageLoader();
		
		ParseRestClient parseClient = ParseRestClient.getInstance();
		parseClient.getInvitations();
		
		//ParseTwitterUtils.initialize( Constants.TWITTER_CONSUMER_KEY, Constants.TWITTER_CONSUMER_SECRET);
	}
	

	public Collection<GraphUser> getSelectedUsers() {
        return selectedUsers;
    }

    public void setSelectedUsers(Collection<GraphUser> selectedUsers) {
        this.selectedUsers = selectedUsers;
    }
    
    public List<Invitation> getAllInvitations(){
    	return invitations;
    }
    
    public List<Invitation> getAllAcceptedInvitations(){
    	return acceptedInvitations;
    }   
    
    public void addInvitation( Invitation i){
    	invitations.add(i);
    }
    
    public void setUser( User user){
    	//this.user = user;
    	eventManager.processUserSyncEvent(user);
    }
//    public User getUser(){
//    	return user;
//    }
	
	private void initializeFb() {
		// Set your Facebook App Id in strings.xml
		ParseFacebookUtils.initialize( getString(R.string.facebook_app_id));

	}

	private void initImageLoader(){ 
        // 	Create global configuration and initialize ImageLoader with this configuration
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
																	.cacheInMemory()
																	.cacheOnDisc()
																	.build();
		
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
																		.defaultDisplayImageOptions(defaultOptions)
																		.build();
		
		ImageLoader.getInstance().init(config);	
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
		

		
		final ParseACL roleACL = new ParseACL();
		roleACL.setPublicReadAccess(true);
		final ParseRole role = new ParseRole("Engineer", roleACL);
				
//		OrganizationUploader.upload( this);
//		ChallengeUploader.upload(this);
//		UserUploader.upload(this);
//		InvitationUploader.upload(this);	
	}
	
    private void initParseNotificationChannels() {
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
		
		ParsePush.subscribeInBackground(MAIN_CHANNEL,  new SaveCallback() {
			
			@Override
			public void done(ParseException e) {
				if( e == null ){
					Log.d(LOG_TAG, "successfully subcribed to "+ MAIN_CHANNEL);
				}else{
					Log.e(LOG_TAG, "failed to subscribe to " +  MAIN_CHANNEL , e);
				}
			}
		});

		ParsePush.subscribeInBackground(INVITATION_RECEIVE,  new SaveCallback() {
			
			@Override
			public void done(ParseException e) {
				if( e == null ){
					Log.d(LOG_TAG, "successfully subcribed to "+ INVITATION_RECEIVE);
				}else{
					Log.e(LOG_TAG, "failed to subscribe to " +  INVITATION_RECEIVE , e);
				}
			}
		});

		ParsePush.subscribeInBackground(INVITATION_COMPLETE,  new SaveCallback() {
			
			@Override
			public void done(ParseException e) {
				if( e == null ){
					Log.d(LOG_TAG, "successfully subcribed to "+ INVITATION_COMPLETE);
				}else{
					Log.e(LOG_TAG, "failed to subscribe to " +  INVITATION_COMPLETE , e);
				}
			}
		});
	}
	
	
	public static TwitterRestClient getRestClient() {
		return (TwitterRestClient) TwitterRestClient.getInstance(TwitterRestClient.class, CharityChallengerApplication.context);
	}

	public void signUpUser( final User user){
		
		user.saveInBackground( new SaveCallback(){
			@Override
			public void done(ParseException paramParseException) {
				if(paramParseException != null ){
					Log.e(LOG_TAG, "Unable to save user", paramParseException);
				}else{
					setUser(user);
				}
			}
		});
	}
	
	public void retrieveOrSignupUser(final User user) {
		
		ParseQuery<User> query = ParseQuery.getQuery(User.class);
		
		query.whereEqualTo("facebookId", user.getFacebookId());
		query.findInBackground( new FindCallback<User>(){

			@Override
			public void done(List<User> users, ParseException e) {
				
				if( e == null ){
					
					if( users != null ){
					
						// if users is empty, that means the user doesn't exist
						// so signup for the user
						if( users.size() == 0){
							signUpUser(user);
						}else if( users.size() == 1 ){
							// if the user exists, then save the user
							User u = users.get(0);
							if( u.getFacebookId().equals(user.getFacebookId())){
								if(user.getName() != null ){
									u.setName(user.getName());
								}
								if( user.getImageUrl() != null ){
									u.setImageUrl(user.getImageUrl());
								}
								u.saveInBackground();
								
								// Save the user with ParseData.
								
								setUser(u);
							}
						}else if( users.size() > 1 ){
							// TODO: complex case, deal with it later
						}
					}
				}else{
					Log.d(LOG_TAG, "exception "+ e);
				}
			}
			
		});	
	}

	public void sendInvitations( List<Invitation> invitations) {
		if( invitations != null ){
			
			for( Invitation i : invitations ){
			
				i.pinInBackground();
				i.saveInBackground( new SaveCallback(){
					@Override
					public void done(ParseException e) {
						if( e == null ){
							// success.
							
						}else{
							// error
						}
					}
				});
				
				sendInvitationPush(i);
			}
		}
	}
	
	public void sendInvitationPush( Invitation i ){
		ParsePush push = new ParsePush();
		String senderName = parseData.getUser().getName();

		try {
			JSONObject json = new JSONObject();
			json.put("subject", senderName + " sent a challenge for you!");
			json.put("message", "You have received a challenge");
			json.put("challengeId", i.getChallengeId());
			json.put("amount", i.getAmount());
			json.put("inviteId",  i.getInviteId());
			json.put("sender", i.getSender());
			json.put("receiver", i.getReceiver());
			json.put("objectId", i.getObjectId());
			json.put("status", i.getStatus());

			push.setMessage( json.toString() );
			push.setChannel("INVITATION_RECEIVE");
			
			push.sendInBackground( new SendCallback(){

				@Override
				public void done(ParseException e) {
					if( e == null ){
						
					}else{
						// error
					}
				}
			});

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
}
