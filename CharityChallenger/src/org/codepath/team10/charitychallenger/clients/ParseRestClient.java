package org.codepath.team10.charitychallenger.clients;

import org.apache.http.client.HttpClient;
import org.codepath.team10.charitychallenger.activities.BaseActivity;
import org.json.JSONObject;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class ParseRestClient {
	
	private static final String APPLICATION_ID = "9e0wpyP9qg9UvX1g2cz65Qs2h2EkUkno88bzctFL";
	private static final String REST_API_KEY = "QFEwPbowXUwEkgTvCsLvN23y8kb88kNmLMAJ4Gi8";
	private static final String HEADER_APPLICATION_ID = "X-Parse-Application-Id";
	private static final String HEADER_REST_API_KEY = "X-Parse-REST-API-Key";
	
	private static final String PARSE_BASE_URL = "https://api.parse.com/1/classes/";
	
	AsyncHttpClient httpClient = new AsyncHttpClient();
	
	private static ParseRestClient client = null;
	
	private ParseRestClient(){
		httpClient = new AsyncHttpClient();
		httpClient.addHeader(HEADER_APPLICATION_ID, APPLICATION_ID);
		httpClient.addHeader(HEADER_REST_API_KEY, REST_API_KEY);
	}

	public static ParseRestClient getInstance(){
		if( client == null ){
			client = new ParseRestClient();
		}
		
		return client;
	}
	
	
	public void getInvitations(){
		String endPoint = PARSE_BASE_URL + "Invitation";
		
		RequestParams params = new RequestParams();
		
		
		httpClient.get( endPoint, params, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int paramInt, JSONObject json) {
				if( json != null ){
					Log.d(BaseActivity.LOG_TAG, "Json " + json.toString());
				}
			}
		});
	}
}
