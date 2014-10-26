package org.codepath.team10.charitychallenger.activities;

import java.util.ArrayList;
import java.util.List;

import org.codepath.team10.charitychallenger.CharityChallengerApplication;
import org.codepath.team10.charitychallenger.R;
import org.codepath.team10.charitychallenger.fragments.CustomFbFriendsPickerFragment;
import org.codepath.team10.charitychallenger.models.Challenge;
import org.codepath.team10.charitychallenger.models.Invitation;
import org.codepath.team10.charitychallenger.models.InvitationStatusEnum;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import com.facebook.FacebookException;
import com.facebook.model.GraphUser;
import com.facebook.widget.FriendPickerFragment;
import com.facebook.widget.PickerFragment;

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
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_invite_friends);
//		
//		ArrayList<String> friends = new ArrayList<String>();
//		
//		friends.add("Joe");
//		friends.add("Mary");
//		friends.add("Martha");
//		friends.add("Nathan");
//		friends.add("Syed");
//		friends.add("Kevin");
//		friends.add("Nambi");
//		friends.add("Shiva");
//		friends.add("Jim");
//		
//		ArrayAdapter<String> friendsAdapter = 
//			    new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, friends);
//		
//		ListView list = (ListView)findViewById(R.id.lvListOfFriends);
//		list.setAdapter(friendsAdapter);
//		
//		//setOnItemClickListener
//		list.setOnItemClickListener( new OnItemClickListener(){
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				Intent intent = new Intent(InviteFriendsActivity.this, FunActivity.class);
//				startActivity(intent);
//			}
//			
//		});
//	}
//	
//	public void onClickInvite( View view){
//		Intent intent = new Intent(this, FunActivity.class);
//		startActivity(intent);
//	}
}
