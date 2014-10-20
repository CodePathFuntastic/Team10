package org.codepath.team10.charitychallenger.queries;

import org.codepath.team10.charitychallenger.models.User;

import com.parse.FindCallback;
import com.parse.ParseQuery;


public class UserQuery {
	public static void getUserBySenderId(String senderId,
			FindCallback<User> callback){

	ParseQuery<User> query = ParseQuery.getQuery(User.class);
	query.whereEqualTo("facebookId", senderId);
	query.findInBackground(callback);
	}
}
