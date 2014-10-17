package org.codepath.team10.charitychallenger.activities;

import org.codepath.team10.charitychallenger.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ChallengeDetailsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_challenge_details);
	}
	
	public void onClickInvite( View view){
		Intent intent = new Intent(this, InviteFriendsActivity.class);
		startActivity(intent);
	}
	
	public void onClickDonate(View view){
		Intent intent = new Intent(this, DonateActivity.class);
		startActivity(intent);
	}
}
