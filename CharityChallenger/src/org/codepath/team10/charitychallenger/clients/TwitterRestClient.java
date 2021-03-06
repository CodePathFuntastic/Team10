package org.codepath.team10.charitychallenger.clients;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.codepath.team10.charitychallenger.models.AATweet;
import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterRestClient extends OAuthBaseClient implements Serializable {
	
	private static final long serialVersionUID = 1551228791225134824L;

	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
	public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
	public static final String REST_CONSUMER_KEY = "308Y6AhszvPZK1Tg9l7RDXf9Z";       // Change this
	public static final String REST_CONSUMER_SECRET = "xdRYse6QDDbWkE5bFA7xB5XsLwl6DWusTUSytx7TMge4LE1YNS"; // Change this
	public static final String REST_CALLBACK_URL = "oauth://charitychallenger"; // Change this (here and in manifest)

	public TwitterRestClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}
	
	public void getUserTimeline(String screenName, TweetsCounter counter, AsyncHttpResponseHandler responseHandler){
		getTweetsFromApi("statuses/user_timeline.json", counter, screenName, responseHandler);
	}
	
	public void getHomeTimeLine( TweetsCounter counter, AsyncHttpResponseHandler responseHandler ){
		getTweetsFromApi("statuses/home_timeline.json", counter, null, responseHandler);
	}
	
	public void getMentionsTimeline( TweetsCounter counter, AsyncHttpResponseHandler responseHandler) {
		getTweetsFromApi("statuses/mentions_timeline.json", counter, null, responseHandler);
	}
	
	public void getTweetsFromApi( String api, TweetsCounter counter, String screenName, AsyncHttpResponseHandler responseHandler ){
		//String apiUrl = getApiUrl("statuses/home_timeline.json");
		String apiUrl = getApiUrl(api);
		RequestParams params = null;
						
		if( counter.getMaxId() > 0){
			
			if( params == null ){
				params = new RequestParams();
			}
			params.put("max_id", counter.getMaxIdStr() );
		}
		if( screenName != null && !screenName.trim().equals("")){
			if( params == null ){
				params = new RequestParams();
			}
			params.put("screen_name", screenName);
		}
				
		if( params == null ){
			client.get(apiUrl, null, responseHandler);
		}else{
			client.get(apiUrl, params, responseHandler);
		}
		
	}
	
	
	public void getMyInfo(  AsyncHttpResponseHandler responseHandler){
		String api_url = getApiUrl("account/verify_credentials.json");
		client.get(api_url, responseHandler);
	}
	
	public void getUsersDetails( ArrayList<String> screenNames, AsyncHttpResponseHandler responseHandler ){
		String api_url = getApiUrl("/users/lookup.json");
		
		RequestParams params = new RequestParams();
		params.put("screen_name",  screenNames);
		client.get(api_url, params, responseHandler);
	}
	
	
	public void createTweet( String tweet, AsyncHttpResponseHandler responseHandler ){
		String api_url = getApiUrl("statuses/update.json");
		
		RequestParams params = new RequestParams();
		params.put("status", tweet);
		
		client.post(api_url, params, responseHandler);
	}

	

	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
	 * 	  i.e getApiUrl("statuses/home_timeline.json");
	 * 2. Define the parameters to pass to the request (query or body)
	 *    i.e RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    i.e client.get(apiUrl, params, handler);
	 *    i.e client.post(apiUrl, params, handler);
	 */
	
	
	/**
	 * <code>TimelineCounter</code> keeps track of which tweets are downloaded and which need to be fetched.
	 */
	public static  class TweetsCounter{
		
		// max value of the received tweets
		private long sinceId=0l;
		// min values of the received tweets 
		private long maxId=0l;
				
		public TweetsCounter(){
		}
		public long getSinceId() {
			return sinceId;
		}
		public String getSinceIdStr(){
			return ""+ sinceId;
		}
		public void setSinceId(long sinceId) {
			this.sinceId = sinceId;
		}
		public String getMaxIdStr(){
			return ""+ maxId;
		}
		public long getMaxId() {
			return maxId;
		}
		public void setMaxId(long maxId) {
			this.maxId = maxId;
		}
		public void setSinceIdMaxIdFrom( List<AATweet> tweets){
			if( tweets == null || tweets.size() == 0){
				return ;
			}
			
			// find the highest id and lowest id from the list
			long high=0;
			long low=0;
			
			for( AATweet t : tweets ){
				// special case, if "low" is 0 , then start from the first value
				if( low == 0){
					low = t.getUid();
				}
				if( t.getUid() <= low){
					low = t.getUid();
				}
				if( t.getUid() > high){
					high = t.getUid();
				}
			}
			
			if( high > getSinceId() ){
				setSinceId(high);
			}else if( getSinceId() == 0){
				// handle special case
				setSinceId(high);
			}
			if( low < getMaxId() ){
				setMaxId(low);
			}else if( getMaxId() == 0){
				setMaxId(low);
			}
		}
	}
}