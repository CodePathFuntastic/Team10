package org.codepath.team10.charitychallenger.activities;

import org.codepath.team10.charitychallenger.R;
import org.codepath.team10.charitychallenger.helper.ParseProxyObject;
import org.codepath.team10.charitychallenger.models.Challenge;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class DonateActivity extends BaseActivity {
	
    //ParseProxyObject ppo;
    private Challenge challenge;
    private TextView tvCharityNameValue;
    private TextView tvAddressValue;
    private TextView tvCauseValue;
    private TextView tvCharityUrlValue;
    private EditText etDonateAmountValue;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//getActionBar().setDisplayHomeAsUpEnabled(true);
		
		setContentView(R.layout.activity_donate_money);
		//getActionBar().setDisplayHomeAsUpEnabled(true);
		
		Intent intent = getIntent();
		//ppo = (ParseProxyObject) intent.getSerializableExtra("parseObject");
		challenge = (Challenge) intent.getParcelableExtra("challenge");
		Log.v(LOG_TAG, String.format("challenge description: %s", challenge.getName() ));
		
		tvCharityNameValue = (TextView) findViewById(R.id.tvCharityNameValue);
		tvAddressValue = (TextView) findViewById(R.id.tvAddressValue);
		tvCauseValue = (TextView) findViewById(R.id.tvCauseValue);
		tvCharityUrlValue = (TextView) findViewById(R.id.tvCharityUrlValue);
		etDonateAmountValue = (EditText) findViewById(R.id.etDonateAmountValue);

		int orgId = challenge.getOrganization();
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Organization");
		query.whereEqualTo("org_id", orgId);
		   query.getFirstInBackground(new GetCallback<ParseObject>() {
		        public void done(ParseObject parseObject, ParseException ParseError) {
		            Log.d( LOG_TAG,"inside done :"+parseObject.getString("description"));
		            if(ParseError == null){
		            	updateView(parseObject);
		            }else{
		                 Log.d( LOG_TAG, "Bombed error is :"+ParseError);
		            }
		        }
		    });
		
	}
	
	public void updateView(ParseObject parseObject) {
		tvCharityNameValue.setText(parseObject.getString("name"));
		tvAddressValue.setText(parseObject.getString("address"));
		tvCauseValue.setText(parseObject.getString("description"));
		tvCharityUrlValue.setText(parseObject.getString("url"));
	}
		
	public void onDonateNow(View v) {
		 
		Intent intent = new Intent(this, PaymentConfirmationActivity.class);
		 intent.putExtra("challenge", challenge);
		 
		 String amount = etDonateAmountValue.getText().toString();
		 intent.putExtra("amount", amount);
		
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
