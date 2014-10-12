package org.codepath.team10.charitychallenger.activities;

import org.codepath.team10.charitychallenger.R;

import android.app.Activity;
import android.os.Bundle;

public class ChallengeDetailsActivity extends Activity {
		@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_challenge_details);
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}
}
