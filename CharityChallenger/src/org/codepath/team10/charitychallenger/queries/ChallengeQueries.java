package org.codepath.team10.charitychallenger.queries;

import org.codepath.team10.charitychallenger.models.Challenge;

import com.parse.FindCallback;
import com.parse.ParseQuery;

public class ChallengeQueries {

	public static void getChallengeById( int challengeId, FindCallback<Challenge> callback ){
		
		ParseQuery<Challenge> query = ParseQuery.getQuery(Challenge.class);
		query.whereEqualTo("challenge_id", challengeId);
		query.findInBackground(callback);
	}
	
	public static void getAllChallenges(FindCallback<Challenge> callback ){
		ParseQuery<Challenge> query = ParseQuery.getQuery(Challenge.class);
		query.findInBackground(callback);
	}
}
