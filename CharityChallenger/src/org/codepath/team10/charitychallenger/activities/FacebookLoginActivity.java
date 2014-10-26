package org.codepath.team10.charitychallenger.activities;

import java.io.IOException;

import org.codepath.team10.charitychallenger.CharityChallengerApplication;
import org.codepath.team10.charitychallenger.R;
import org.codepath.team10.charitychallenger.models.User;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphUser;

public class FacebookLoginActivity extends BaseActivity{
	
	private Session session=null;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_login);
    }
	
	public void onLoginToFacebook(View v){
		
		// start Facebook Login
		session = Session.openActiveSession(this, true, new Session.StatusCallback(){

			@Override
			public void call(Session session, SessionState state, Exception exception) {
				
				Log.d(LOG_TAG, "Session" + session.toString() + ", state: " + state.toString() , exception);
				
				// session open successfully
				if( Session.getActiveSession().isOpened() == true ){
					
					// make a request to get the GraphApi response with User Object
					
					new Request(
						    session,
						    "/me",
						    null,
						    HttpMethod.GET,
						    new Request.Callback() {
						        public void onCompleted(Response response) {
						        	if( response !=null){
	        		    	        	   GraphObject go = response.getGraphObject();
	        		    	        	   if(go == null){
	        		    	        		   Log.e("Error: ", "Graph Object is null");
	        		    	        		   return;
	        		    	        	   }
	        		    	        		   
	        		    	        	   User user = new User();
        		    	        		   user.setFacebookId((String) go.asMap().get("id"));
        		    	        		   user.setName( (String) go.asMap().get("name"));
        		    	        		   
        		    	        		   callFacebookUserProfile(user);
	        		    	         }
						        }
						    }
						).executeAsync();
					
										
					Intent intent = new Intent( FacebookLoginActivity.this, ChallengesHomeSwipeActivity.class);
					startActivity(intent);

				}else{
					Toast.makeText(FacebookLoginActivity.this, "login failed", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
	
	public void callFacebookUserProfile( final User user){
		
		if( Session.getActiveSession().isOpened() == true ){

			/*
			 * me/picture?width=200&type=&height=200&redirect=false
			 */
			
			Bundle params = new Bundle();
			params.putBoolean("redirect", false);
			params.putString("height", "200");
			params.putString("type", "normal");
			params.putString("width", "200");
			
			new Request(session,
						"/me/picture",
						params,
						HttpMethod.GET,
						new Request.Callback() {
							
							@Override
							public void onCompleted(Response response) {
								
								try {
									if( response != null ){
										int responsecode = response.getConnection().getResponseCode();
										if( responsecode == 200 ){
											
											GraphObject go = response.getGraphObject();
											
											JSONObject json = go.getInnerJSONObject();
											try {
												JSONObject data = json.getJSONObject("data");
												if( data.has("url") ){
													String imageUrl = (String) data.get("url");
													user.setImageUrl(imageUrl);
												}
											} catch (JSONException e) {
												e.printStackTrace();
											}
										}
									}
									
								} catch (IOException e) {
									e.printStackTrace();
								}
								
								Application  a = getApplication();
						 	   	if( a instanceof CharityChallengerApplication) {
						 	   		CharityChallengerApplication app = (CharityChallengerApplication) a;
						 	   		app.retrieveOrSignupUser( user);
						 	   	}
							}
						}
			).executeAsync();
		}
	}
	
	public void startHomeActivity(){
		  Intent intent = new Intent(this, HomeActivity.class);
		  startActivity(intent);		
	}
  
	@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if( Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data) == true ){
			
			
			// session open successfully
			if( Session.getActiveSession().isOpened() == true ){
				// make a request to get the GraphApi response with User Object
				Request.newMeRequest( Session.getActiveSession(), new Request.GraphUserCallback() {
					
					@Override
					public void onCompleted(GraphUser user, Response response) {
						// Save the user details on Sharedpreferences
						user.getId();
					}
				});
				
				Intent intent = new Intent( this, HomeActivity.class);
				startActivity(intent);

			}else{
				Toast.makeText(this, "login failed", Toast.LENGTH_SHORT).show();
			}
		}
    }
  
	public static class FacebookStatusCallback implements Session.StatusCallback{

		@Override
		public void call(Session session, SessionState state,
				Exception exception) {		
			
        	if (session.isOpened()) {
      		// make request to the /me API
      		    Request.newMeRequest(session, new Request.GraphUserCallback() {

      		      // callback after Graph API response with user object
      		      @Override
      		      public void onCompleted(GraphUser user, Response response) {
      		    	  if (user != null) {
      		    		  //TextView welcome = (TextView) findViewById(R.id.tvUsername);
      		    		  //welcome.setText("Hello " + user.getName() + "!");
      		    		  //startHomeActivity();
      		    		}
      		      }
      		    }).executeAsync();
      	  	}

		}
		
	}
	
	// helper function to generate the keyHash. If your app is not authenticating from facebook.
//	private void generateKehHash(){
//	    try {
//	        PackageInfo info = getPackageManager().getPackageInfo(
//	                "org.codepath.team10.charitychallenger", 
//	                PackageManager.GET_SIGNATURES);
//	        for (Signature signature : info.signatures) {
//	            MessageDigest md = MessageDigest.getInstance("SHA");
//	            md.update(signature.toByteArray());
//	            Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//	            }
//	    } catch (NameNotFoundException e) {
//	
//	    } catch (NoSuchAlgorithmException e) {
//	
//	    }
//	}
}