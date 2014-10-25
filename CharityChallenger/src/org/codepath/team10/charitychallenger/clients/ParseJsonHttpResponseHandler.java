package org.codepath.team10.charitychallenger.clients;

import org.codepath.team10.charitychallenger.activities.BaseActivity;
import org.json.JSONArray;
import org.json.JSONObject;

import com.activeandroid.util.Log;
import com.loopj.android.http.JsonHttpResponseHandler;

public class ParseJsonHttpResponseHandler extends JsonHttpResponseHandler {

	@Override
	public void onFailure(Throwable e) {
		Log.e( BaseActivity.LOG_TAG, "Error retrieving sent invitations", e);
	}
	
	@Override
	public void onFailure(Throwable e,String paramString) {
		Log.e(BaseActivity.LOG_TAG, "Error retrieving sent invitations :" + paramString, e);
	}
	
	@Override
	public void onFailure(Throwable e,JSONArray paramJSONArray) {
		Log.e( BaseActivity.LOG_TAG, "Error retrieving sent invitations", e);
	}
	
	@Override
	public void onFailure(Throwable e,JSONObject paramJSONObject) {
		Log.e(BaseActivity.LOG_TAG, "Error retrieving sent invitations", e);
	}

}
