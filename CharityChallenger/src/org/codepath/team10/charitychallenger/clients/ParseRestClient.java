package org.codepath.team10.charitychallenger.clients;

import org.codepath.team10.charitychallenger.models.Invitation;
import org.codepath.team10.charitychallenger.models.User;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class ParseRestClient {
	
	private static final String APPLICATION_ID = "9e0wpyP9qg9UvX1g2cz65Qs2h2EkUkno88bzctFL";
	private static final String REST_API_KEY = "QFEwPbowXUwEkgTvCsLvN23y8kb88kNmLMAJ4Gi8";
	private static final String HEADER_APPLICATION_ID = "X-Parse-Application-Id";
	private static final String HEADER_REST_API_KEY = "X-Parse-REST-API-Key";
	
	private static final String PARSE_BASE_URL = "https://api.parse.com/1/classes/";
	private static final String PARSE_END_POINT_INVITATION = PARSE_BASE_URL + "Invitation";
	private static final String PARSE_END_POINT_CHALLENGE = PARSE_BASE_URL + "Challenge";
	private static final String PARSE_END_POINT_USER = PARSE_BASE_URL + "User";
	
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

	/*
	 sample curl request for testing from CLI
	  curl -X GET \
     	-H "X-Parse-Application-Id: 9e0wpyP9qg9UvX1g2cz65Qs2h2EkUkno88bzctFL" \
     	-H "X-Parse-REST-API-Key: QFEwPbowXUwEkgTvCsLvN23y8kb88kNmLMAJ4Gi8" \
     	-G \
     	--data-urlencode 'where={"receiver":"686916806"}' \
     	https://api.parse.com/1/classes/Invitation
      
      
      NOTE: No need to encode the data when used in the rest cient
	 */
	public void getReceivedInvitations( String receiver, AsyncHttpResponseHandler responseHandler ){
		if( receiver == null ){
			throw new NullPointerException("receiver cannot be null");
		}
		if( responseHandler == null ){
			throw new NullPointerException("response handler cannot be null");
		}
		
		String json = Invitation.createJsonQuery( null, receiver);
		
		RequestParams params = new RequestParams();
		params.put("where", json);
		
		
		httpClient.get(PARSE_END_POINT_INVITATION, params, responseHandler);
	}

	public void getReceivedInvitations( String receiver, int status, 
										AsyncHttpResponseHandler responseHandler ){
		
		if( receiver == null ){
			throw new NullPointerException("receiver cannot be null");
		}
		if( responseHandler == null ){
			throw new NullPointerException("response handler cannot be null");
		}
		
		String json = Invitation.createJsonQueryStatus( null, receiver, status);
		
		RequestParams params = new RequestParams();
		params.put("where", json);
		
		
		httpClient.get(PARSE_END_POINT_INVITATION, params, responseHandler);
	}

	
	public void getSentInvitations( String sender, AsyncHttpResponseHandler responseHandler){
		
		if( sender == null ){
			throw new NullPointerException("sender cannot be null");
		}
		if( responseHandler == null ){
			throw new NullPointerException("response handler cannot be null");
		}
		
		String json = Invitation.createJsonQuery(sender, null);
		
		RequestParams params = new RequestParams();
		params.put("where", json);
		
		httpClient.get(PARSE_END_POINT_INVITATION, params, responseHandler);
	}
	
	public void getUserDetails( String facebookId, AsyncHttpResponseHandler responseHandler ){
		if( facebookId == null ){
			throw new NullPointerException("facebookId cannot be null");
		}
		if( responseHandler == null ){
			throw new NullPointerException("response handler cannot be null");
		}
		
		String json = User.createJsonQuery(facebookId);
		RequestParams params = new RequestParams();
		params.put("where", json);
		
		httpClient.get(PARSE_END_POINT_USER, params, responseHandler);
	}
	
	public void getAllUserDetails(  AsyncHttpResponseHandler responseHandler ){
		if( responseHandler == null ){
			throw new NullPointerException("response handler cannot be null");
		}
				
		httpClient.get(PARSE_END_POINT_USER, null, responseHandler);
	}

	
	/*
	public void getInvitations( ){
		String endPoint = PARSE_BASE_URL + "Invitation";
		
		RequestParams params = new RequestParams();
		
		
		httpClient.get( endPoint, params, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int paramInt, JSONObject json) {
				if( json != null ){
					Log.d(BaseActivity.LOG_TAG, "Json " + json.toString());
					if( !json.isNull("results")){
						try {
							JSONArray array = json.getJSONArray("results");
							List<Invitation> invitations =Invitation.fromJsonArray(array);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		});
	}
	*/
}
