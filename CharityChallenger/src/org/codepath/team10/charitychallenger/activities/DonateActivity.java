package org.codepath.team10.charitychallenger.activities;

import org.codepath.team10.charitychallenger.R;
import org.codepath.team10.charitychallenger.helper.ParseProxyObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class DonateActivity extends Activity {
	
	private static final String TAG = "DonateActivity ";
    ParseProxyObject ppo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_donate_money);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		Intent intent = getIntent();
		ppo = (ParseProxyObject) intent.getSerializableExtra("parseObject");
	}
		
	public void onDonateNow(View v) {
		 Intent intent = new Intent(this, PaymentConfirmationActivity.class);
		 startActivity(intent);
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
