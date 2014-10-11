package org.codepath.team10.charitychallenger.fragments;



import org.codepath.team10.charitychallenger.R;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;

public class FacebookLoginFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.activity_home, container);
		// start Facebook Login
        Session.openActiveSession(getActivity(), this, true, new Session.StatusCallback() {

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
        		    		  TextView welcome = (TextView) view.findViewById(R.id.tvUsername);
        		    		  welcome.setText("Hello " + user.getName() + "!");
        		    		}
        		      }
        		    }).executeAsync();
        	  }
          }
        });
		return view;
	}
    
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//      super.onActivityResult(requestCode, resultCode, data);
//      Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
//    }
}
