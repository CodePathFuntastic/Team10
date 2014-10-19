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
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

public class PaymentConfirmationActivity extends Activity {
	
    ParseProxyObject ppo;
    
    private TextView tvCharityNameOnConfirmation;
    private TextView tvDonationAmount;
    private TextView tvCharityAddressOnConfirmation;
    private TextView tvCharityConfirmationUrl;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_payment_confirmation);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		Intent intent = getIntent();
		final String donateAmount = intent.getStringExtra("amount");
		ppo = (ParseProxyObject) intent.getSerializableExtra("parseObject");
		Log.v("Test", String.format("Proxy object description: %s", ppo.getInt("orgId")));
		
		tvCharityNameOnConfirmation = (TextView) findViewById(R.id.tvCharityNameOnConfirmation);
		tvCharityAddressOnConfirmation = (TextView) findViewById(R.id.tvCharityAddressOnConfirmation);
		tvCharityConfirmationUrl = (TextView) findViewById(R.id.tvCharityConfirmationUrl);
		tvDonationAmount = (TextView) findViewById(R.id.tvDonationAmount);
		tvDonationAmount.setText(donateAmount);

		final int orgId = ppo.getInt("orgId");
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Organization");
		query.whereEqualTo("org_id", orgId);
		   query.getFirstInBackground(new GetCallback<ParseObject>() {
		        public void done(ParseObject parseObject, ParseException ParseError) {
		            Log.d("Log","inside done :"+parseObject.getString("description"));
		            if(ParseError == null){
		            	updateView(parseObject);
		            	int currentRaised = parseObject.getInt("raised");
		            	int totalRaised = currentRaised + Integer.valueOf(donateAmount);
		            	parseObject.put("raised", totalRaised);
		            	parseObject.saveInBackground(new SaveCallback() {
		                    public void done(ParseException e) {
		                           if (e == null) {
		                               Log.d("Log","EXCELENT");   

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
	
	private void updateView(ParseObject parseObject) {
		tvCharityNameOnConfirmation.setText(parseObject.getString("name"));
		tvCharityConfirmationUrl.setText(parseObject.getString("url"));
		tvCharityAddressOnConfirmation.setText(parseObject.getString("address"));
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
	
	public void onClickConfirm( View view){
		Intent intent = new Intent(this, InviteFriendsActivity.class);
		startActivity(intent);
	}
}
