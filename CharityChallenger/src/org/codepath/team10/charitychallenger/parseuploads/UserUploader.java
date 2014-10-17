package org.codepath.team10.charitychallenger.parseuploads;

import java.util.Arrays;

import org.codepath.team10.charitychallenger.models.User;

import android.content.Context;

public class UserUploader {

	public static void upload( Context context ){
		
		User u1 = new User();
		u1.setFacebookId("snambi@gmail.com");
		u1.setTwitterId("snambi");
		u1.setName("Nambi Sankaran");
		u1.setImageUrl("https://en.gravatar.com/userimage/27098625/0ef522318e4e61098a43e767a92ff08c.png");
		u1.setLocation("Sunnyvale, CA");
		String[] f1 = {"joe1", "kevin", "syed"};
		u1.addFriends(Arrays.asList( f1 ));
		u1.saveInBackground();
		
		User u2 = new User();
		u2.setFacebookId("syed");
		u2.setName("Syed Naqvi");
		u2.setTwitterId("syed");
		u2.setImageUrl("http://www.gravatar.com/avatar/12c1217a2578591700612d472a2d8d42.png");
		u2.setLocation("San jose, CA");
		String[] f2 = {"snambi", "kevin"};
		u2.addFriends(Arrays.asList( f2 ));
		u2.saveInBackground();

		User u3 = new User();
		u3.setFacebookId("kevin");
		u3.setName("Kevin Mo");
		u3.setTwitterId("kevin");
		u3.setImageUrl("https://en.gravatar.com/userimage/27098625/0ef522318e4e61098a43e767a92ff08c.png");
		u3.setLocation("Fremont, CA");
		String[] f3 = {"snambi", "syed"};
		u3.addFriends(Arrays.asList( f3 ));
		u3.saveInBackground();
		
		User u4 = new User();
		u4.setFacebookId("Nathan");
		u4.setTwitterId("Nathan");
		u4.setImageUrl("https://en.gravatar.com/userimage/27098625/0ef522318e4e61098a43e767a92ff08c.png");
		u4.setName("Nathan Esquenazi");
		u4.setLocation("San Francisco, CA");
		String[] f4 = {"snambi", "syed"};
		u4.addFriends(Arrays.asList( f4 ));
		u4.saveInBackground();

		User u5 = new User();
		u5.setFacebookId("Robert");
		u5.setName("Robert Enyedi");
		u5.setTwitterId("Robert");
		u5.setImageUrl("https://en.gravatar.com/userimage/27098625/0ef522318e4e61098a43e767a92ff08c.png");
		u5.setLocation("Santa Clara, CA");
		String[] f5 = {"snambi", "syed"};
		u5.addFriends(Arrays.asList( f5 ));
		u5.saveInBackground();

		User u6 = new User();
		u6.setFacebookId("Laura");
		u6.setTwitterId("Laura");
		u6.setImageUrl("https://en.gravatar.com/userimage/27098625/0ef522318e4e61098a43e767a92ff08c.png");
		u6.setName("Laura Wong");
		u6.setLocation("Santa Clara, CA");
		String[] f6 = {"snambi", "syed", "robert"};
		u6.addFriends(Arrays.asList( f6 ));
		u6.saveInBackground();

	}
}
