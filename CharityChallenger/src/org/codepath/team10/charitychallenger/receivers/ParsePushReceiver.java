package org.codepath.team10.charitychallenger.receivers;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class ParsePushReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if( intent != null ){
			if( !intent.getExtras().isEmpty() ){
				Bundle extras = intent.getExtras();
				
				try {
					String channel = extras.getString("com.parse.Channel");
					JSONObject json = new JSONObject(intent.getExtras().getString("com.parse.Data"));
					Toast.makeText(context, "Push Received. channel :" + channel + ", data: "+json, Toast.LENGTH_SHORT).show();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

		}else{
			Toast.makeText(context, "Push Received without intent", Toast.LENGTH_SHORT).show();
		}
	}

}
