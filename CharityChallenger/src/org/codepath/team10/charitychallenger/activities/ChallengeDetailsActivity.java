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
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}
		
	public void onClickAccept(View v) {
		 Intent intent = new Intent(this, ActionDetailActivity.class);
		 startActivity(intent);
	}
}
