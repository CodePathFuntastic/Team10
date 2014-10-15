package org.codepath.team10.charitychallenger.activities;

import org.codepath.team10.charitychallenger.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
