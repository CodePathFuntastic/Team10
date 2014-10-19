package org.codepath.team10.charitychallenger.activities;

import java.util.List;

import org.codepath.team10.charitychallenger.R;
import org.codepath.team10.charitychallenger.helper.ParseProxyObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.parse.SendCallback;

public class InvitationDetails extends BaseActivity {
	private ParseObject mChallenge;
	private TextView mTvChallengeName;
	private TextView mTvTarget;
	private TextView mTvRaised;
	private TextView mTvDesc;
    private ParseProxyObject ppo;
	private ImageView mIvCharity;
	public static final String MAIN_CHANNEL = "MAIN_CHANNEL";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_invitation_details);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		int challengeId = 0;
		Intent intent = getIntent();

		if(intent.hasExtra("parseObject")){
			ppo = (ParseProxyObject) intent.getSerializableExtra("parseObject");
			challengeId = ppo.getInt("challengeId");
		}
		
		mTvChallengeName = (TextView) findViewById(R.id.tvCharityChallengeName);
		mTvTarget = (TextView) findViewById(R.id.tvTargetAmountRaised);
		mTvRaised = (TextView) findViewById(R.id.tvAmountRaised);
		mTvDesc = (TextView) findViewById(R.id.tvDescription);
		mIvCharity = (ImageView) findViewById(R.id.ivCharity); 
		
		if(challengeId != 0){
			ParseQuery<ParseObject> query = ParseQuery.getQuery("Challenge");
			query.whereEqualTo("challenge_id", challengeId);
			query.findInBackground(new FindCallback<ParseObject>() {
	        public void done(List<ParseObject> records, ParseException e) {
	        	if(e == null){
	        		mTvChallengeName.setText(records.get(0).getString("name"));
	        		int num = records.get(0).getInt("target");
	        		mTvTarget.setText("$ "+num);
	        		mTvRaised.setText("$ "+records.get(0).getInt("raised"));
	        		mTvDesc.setText(""+records.get(0).getString("description"));	
	        		List<String> url = records.get(0).getList("challenge_pic_urls");
	        		ImageLoader.getInstance().displayImage(url.get(0), mIvCharity);
	        	} else {
	        		e.printStackTrace();
	        	}
	        }
	        });
		}
	}
		
	public void onClickAccept(View v) {
		 Intent intent = new Intent(this, NewPictureActivity.class);
         intent.putExtra("parseObject", ppo);
 		 startActivityForResult(intent, 110);
	}
	
	public void onDonate(View view){
		Intent intent = new Intent(this, DonateActivity.class);
		startActivityForResult(intent, 120);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK && requestCode == 110) {
			Log.i("InviationDetail", "Get back from the activity");
			// set the ppo and store in the database.
			// update the invitation table
			final int challengeId = ppo.getInt("challengeId");
			// get the ParseFile URL
			ParseFile newPhoto = (ParseFile)data.getSerializableExtra("photo");
			final String newPhotoUrl = newPhoto.getUrl();
			ParseQuery<ParseObject> queryChallenge = ParseQuery.getQuery("Invitation");
			queryChallenge.whereEqualTo("challengeId", challengeId);
			queryChallenge.getFirstInBackground(new GetCallback<ParseObject>() {
				public void done(ParseObject parseObject, ParseException ParseError) {
					Log.d("Log","inside done :"+parseObject.getInt("challengeId"));
					if(ParseError == null){
						JSONArray photos = parseObject.getJSONArray("photos");
						JSONArray newPhotos;
						if (photos == null) {
							photos = new JSONArray();
						} 
						photos.put(newPhotoUrl);
						final String sender = parseObject.getString("sender");
						parseObject.saveInBackground(new SaveCallback() {
							public void done(ParseException e) {
								if (e == null) {
									Log.d("Log","EXCELENT");   
									// fire the push message
									ParsePush push = new ParsePush();
									push.setMessage(sender + " has accepted your Challenge");
									JSONObject data = new JSONObject();
									try {
										data.put("sender", sender);
									} catch (JSONException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									push.setData(data);
									push.setChannel(MAIN_CHANNEL);
									push.sendInBackground( new SendCallback(){
							
										@Override
										public void done(ParseException paramParseException) {
											if(paramParseException != null){
												Log.e("org.codepath.team10.charitychallenger", "Push Exception", paramParseException);
											}else{
												Log.d("org.codepath.team10.charitychallenger", "Push Done");
											}
											
										}});
								} else {

									Log.d("Log","Failed boss: "+e);
									System.out.println(e.getCause());
									System.out.println("VERY BAD");     
								}
							}
						});
					}else{
						Log.d("Log", "Bombed error is :"+ParseError);
					}
				}
			});	
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.new_picture, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == android.R.id.home) {
			this.finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
