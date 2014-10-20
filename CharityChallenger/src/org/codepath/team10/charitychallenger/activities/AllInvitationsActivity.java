package org.codepath.team10.charitychallenger.activities;

import java.util.List;

import org.codepath.team10.charitychallenger.CharityChallengerApplication;
import org.codepath.team10.charitychallenger.R;
import org.codepath.team10.charitychallenger.adapters.ReceivedInvitationAdapter;
import org.codepath.team10.charitychallenger.helper.ParseProxyObject;
import org.codepath.team10.charitychallenger.models.Invitation;
import org.codepath.team10.charitychallenger.models.InvitationStatusEnum;
import org.codepath.team10.charitychallenger.queries.InvitationQuery;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.parse.SendCallback;

public class AllInvitationsActivity extends BaseActivity {
	
	private ListView mLvAllInvitations;
	private ReceivedInvitationAdapter mItemsAdapter;
	//private List<Invitation> mInvitations;
   // private ParseProxyObject ppo;
	//public static final String MAIN_CHANNEL = "MAIN_CHANNEL";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all_invitations);
		
		
		mItemsAdapter = new ReceivedInvitationAdapter(this, getInvitations());
		
		mLvAllInvitations = (ListView) findViewById(R.id.lvAllInvitations);
		mLvAllInvitations.setAdapter(mItemsAdapter);
		
		//setOnItemClickListener
		mLvAllInvitations.setOnItemClickListener( new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(AllInvitationsActivity.this, InvitationDetails.class);
				startActivity(intent);
			}
		});
		
		// if the invitations are 0, fire a parse query to load invitations.
		// then add a callback which notifies the adapter.
		if( getInvitations().size() == 0 ){
			InvitationQuery.getInvitations( getUser().getFacebookId(), 
											InvitationStatusEnum.OPEN, 
											false, 
											new FindCallback<Invitation>(){
												public void done(List<Invitation> invitations, 
															ParseException e){
													
													if( e == null ){
														if( invitations.size() > 0 ){
															getInvitations().addAll(invitations);
															
															// trigger the adapter to reload
															mItemsAdapter.notifyDataSetChanged();
														}
													}else{
														Log.e(LOG_TAG, "Unable to retreive invitations", e);
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
	
	public void launchInvitationDetails(){
		
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
	
	/*
	public void startPictureActivity(Intent intent) {
		 startActivityForResult(intent, 110);	
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK && requestCode == 110) {
			Log.i("InviationDetail", "Get back from the activity");
			// set the ppo and store in the database.
			// update the invitation table
			ParseProxyObject incomingPPo = (ParseProxyObject)data.getSerializableExtra("parseObject");
			final int challengeId = incomingPPo.getInt("challenge_Id");
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
									push.setChannel(CharityChallengerApplication.MAIN_CHANNEL);
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
	*/

}
