package org.codepath.team10.charitychallenger.clients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.codepath.team10.charitychallenger.activities.BaseActivity;

import android.os.AsyncTask;
import android.util.Log;

public class PutAsyncTask extends AsyncTask<String, Integer, Void> {

	@Override
	protected Void doInBackground(String... objectId ) {
		
		try {
			
			String endpoint = ParseRestClient.PARSE_END_POINT_INVITATION + "/" + objectId[0]; 
			URL url = new URL(endpoint);
			HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
			httpCon.setDoOutput(true);
			httpCon.setRequestMethod("PUT");
			httpCon.addRequestProperty( ParseRestClient.HEADER_APPLICATION_ID, ParseRestClient.APPLICATION_ID);
			httpCon.addRequestProperty( ParseRestClient.HEADER_REST_API_KEY, ParseRestClient.REST_API_KEY);
			httpCon.addRequestProperty( ParseRestClient.HEADER_CONTENT_TYPE, ParseRestClient.HEADER_CONTENT_VALUE);
			httpCon.connect();
			
			String json = "{ \"opened_status\": true }";
			
			OutputStreamWriter out = new OutputStreamWriter(httpCon.getOutputStream());
			out.write(json);
			out.close();
			
			int status = httpCon.getResponseCode();
			String msg = httpCon.getResponseMessage();
			
			InputStream in =httpCon.getInputStream();
			StringBuilder sb=new StringBuilder();
			InputStreamReader is = new InputStreamReader(in);
			BufferedReader br = new BufferedReader(is);
			String read = br.readLine();
			
			while(read != null) {
			    //System.out.println(read);
			    sb.append(read);
			    read =br.readLine();
			}
			
			Log.d(BaseActivity.LOG_TAG, "" + status + ": " + msg);
			
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
	}

	@Override
	protected void onPostExecute(Void handler) {
		super.onPostExecute(handler);
	}
}
