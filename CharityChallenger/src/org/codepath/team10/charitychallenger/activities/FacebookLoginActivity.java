package org.codepath.team10.charitychallenger.activities;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.codepath.team10.charitychallenger.R;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;

public class FacebookLoginActivity extends Activity{
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_login);
    }
	
	public void onLoginToFacebook(View v){
		// start Facebook Login
		Session.openActiveSession(this, true, new Session.StatusCallback() {
	        // callback when session changes state
	        @Override
	        public void call(Session session, SessionState state, Exception exception) {
	      	  	if (session.isOpened()) {
	      		// make request to the /me API
	      		    Request.newMeRequest(session, new Request.GraphUserCallback() {
	
	      		      // callback after Graph API response with user object
	      		      @Override
	      		      public void onCompleted(GraphUser user, Response response) {
	      		    	  if (user != null) {
	      		    		  TextView welcome = (TextView) findViewById(R.id.tvUsername);
	      		    		  welcome.setText("Hello " + user.getName() + "!");
	      		    		  startActivity1();
	      		    		}
	      		      }
	      		    }).executeAsync();
	      	  	}
	        }
		});
	}
	
	public void startActivity1(){
		  Intent intent = new Intent(this, HomeActivity.class);
		  startActivity(intent);		
	}
  
	@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
    }
  
	// helper function to generate the keyHash. If your app is not authenticating from facebook.
	private void generateKehHash(){
	    try {
	        PackageInfo info = getPackageManager().getPackageInfo(
	                "org.codepath.team10.charitychallenger", 
	                PackageManager.GET_SIGNATURES);
	        for (Signature signature : info.signatures) {
	            MessageDigest md = MessageDigest.getInstance("SHA");
	            md.update(signature.toByteArray());
	            Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
	            }
	    } catch (NameNotFoundException e) {
	
	    } catch (NoSuchAlgorithmException e) {
	
	    }
	}
}
