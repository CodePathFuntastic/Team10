package org.codepath.team10.charitychallenger.activities;

import org.codepath.team10.charitychallenger.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class DonateActivity extends Activity {
	private static final String TAG = "DonateActivity ";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_donate_money);
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}
		
	public void onDonateNow(View v) {
		 Log.d(TAG, "onDonateNow called");
	}
}
