package org.codepath.team10.charitychallenger.activities;

import java.util.ArrayList;
import java.util.List;

import org.codepath.team10.charitychallenger.CharityChallengerApplication;
import org.codepath.team10.charitychallenger.ParseData;
import org.codepath.team10.charitychallenger.R;
import org.codepath.team10.charitychallenger.fragments.CustomFbFriendsPickerFragment;
import org.codepath.team10.charitychallenger.models.Challenge;
import org.codepath.team10.charitychallenger.models.User;
import org.codepath.team10.charitychallenger.utils.FBUtils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import com.activeandroid.util.Log;
import com.facebook.FacebookException;
import com.facebook.model.GraphUser;
import com.facebook.widget.FriendPickerFragment;
import com.facebook.widget.PickerFragment;
import com.parse.ParseException;
import com.parse.SaveCallback;

public class InviteFriendsActivity extends BaseActivity {
	
	//FriendPickerFragment friendPickerFragment;
	CustomFbFriendsPickerFragment friendPickerFragment;
	
	//ParseProxyObject ppo;
	Challenge challenge;


	public static void populateParameters(Intent intent, String userId, boolean multiSelect, boolean showTitleBar) {
		intent.putExtra(FriendPickerFragment.USER_ID_BUNDLE_KEY, userId);
		intent.putExtra(FriendPickerFragment.MULTI_SELECT_BUNDLE_KEY, multiSelect);
		intent.putExtra(FriendPickerFragment.SHOW_TITLE_BAR_BUNDLE_KEY, showTitleBar);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_invite_friends);
		
		//ppo = (ParseProxyObject) getIntent().getSerializableExtra("challenge");
		challenge = getIntent().getParcelableExtra("challenge");				

		FragmentManager fm = getSupportFragmentManager();

		if (savedInstanceState == null) {
			final Bundle args = getIntent().getExtras();
			//friendPickerFragment = new FriendPickerFragment(args);
			friendPickerFragment = new CustomFbFriendsPickerFragment(args);
			fm.beginTransaction()
				.add(R.id.friend_picker_fragment, friendPickerFragment)
				.commit();
		} else {
			friendPickerFragment = (CustomFbFriendsPickerFragment) fm.findFragmentById(R.id.friend_picker_fragment);
		}

		friendPickerFragment.setOnErrorListener(new PickerFragment.OnErrorListener() {
			@Override
			public void onError(PickerFragment<?> fragment, FacebookException error) {
				InviteFriendsActivity.this.onError(error);
			}
		});

		final String senderId = getUser().getFacebookId();
		friendPickerFragment.setOnDoneButtonClickedListener(new PickerFragment.OnDoneButtonClickedListener() {

			@Override
			public void onDoneButtonClicked(PickerFragment<?> fragment) {
				// We just store our selection in the Application for other activities to look at.
				CharityChallengerApplication application = (CharityChallengerApplication) getApplication();
				application.setSelectedUsers(friendPickerFragment.getSelection());
				
				List<GraphUser> fbUsers = friendPickerFragment.getSelection();
				
				List<User> users = new ArrayList<User>();
				for( GraphUser u : fbUsers ){
					
					User ccuser = ParseData.getInstance().getFriendByFacebookId(u.getId());
					if(ccuser == null ){
						
						ccuser = User.create(User.class);
						String imgUrl = FBUtils.extractImageUrl(u.getInnerJSONObject());
						ccuser.setImageUrl(imgUrl);
						ccuser.setName( u.getName());
						ccuser.setFacebookId(u.getId());
						
						if( u.getLocation() != null && 
								u.getLocation().getCity() != null && 
								u.getLocation().getState() != null ){
							String loc = u.getLocation().getCity()+ ", " + u.getLocation().getState();
							ccuser.setLocation(loc);
						}
						
						ccuser.saveInBackground( new SaveCallback(){

							@Override
							public void done(ParseException e) {
								if( e != null ){
									Log.e(BaseActivity.LOG_TAG, "Error Saving User" , e);
								}
							}
						});
						
						users.add(ccuser);
					}
				}
								
				User[] array = new User[users.size()];
				users.toArray(array);
				
				Intent data = new Intent();
				data.putExtra("users", array);
				setResult(RESULT_OK, data);
				finish();
				
				/*
				List<Invitation> invitations = new ArrayList<Invitation>();
				for( GraphUser u : fbUsers ){
					Invitation i = new Invitation();
					i.setReceiver(u.getId());
					i.setSender(senderId);
					
					// TODO: challenge should have an amount
					i.setAmount( 10.00);
					i.setStatus(InvitationStatusEnum.OPEN.ordinal());
					i.setOpened(false);
					i.setChallengeId(challenge.getChallengeId());
					
					invitations.add(i);
				}
				
				application.sendInvitations( invitations);

				setResult(RESULT_OK, null);
				finish();
				*/
			}
		});
	}

	private void onError(Exception error) {
		String text = getString(R.string.exception, error.getMessage());
		Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
		toast.show();
	}

	@Override
	protected void onStart() {
		super.onStart();
		try {
			// Load data, unless a query has already taken place.
			friendPickerFragment.loadData(false);
		} catch (Exception ex) {
			onError(ex);
		}
	}
}
