package org.codepath.team10.charitychallenger.activities;

import org.codepath.team10.charitychallenger.CharityChallengerApplication;
import org.codepath.team10.charitychallenger.ParseData;
import org.codepath.team10.charitychallenger.R;
import org.codepath.team10.charitychallenger.fragments.NewDonationFragment;
import org.codepath.team10.charitychallenger.models.Challenge;
import org.codepath.team10.charitychallenger.models.Invitation;
import org.codepath.team10.charitychallenger.models.InvitationStatusEnum;
import org.codepath.team10.charitychallenger.models.User;
import org.codepath.team10.charitychallenger.utils.InvitationMessageUtils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.parse.SendCallback;

public class PaymentConfirmationActivity extends BaseActivity {
	
    //ParseProxyObject ppo;
    
    private TextView tvCharityNameOnConfirmation;
    private TextView tvDonationAmount;
    private TextView tvCharityAddressOnConfirmation;
    private TextView tvCharityConfirmationUrl;
    
    private Challenge challenge;
    private Invitation invitation;
    private String donateAmount;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_payment_confirmation);
		//getActionBar().setDisplayHomeAsUpEnabled(true);
		
		Intent intent = getIntent();
		
		if( intent.hasExtra("amount")){
			donateAmount = intent.getStringExtra("amount");
		}
		if( intent.hasExtra("challenge")){
			challenge = (Challenge) intent.getParcelableExtra("challenge");
		}
		if( intent.hasExtra("invitation")){
			invitation = (Invitation) intent.getParcelableExtra("invitation");
		}

		
		parseData = ParseData.getInstance();
		
		User sender = parseData.getFriendByFacebookId(invitation.getSender());		
		
		Log.v(LOG_TAG, String.format("challenge description: %s", challenge.getName() ));
		
//		tvCharityNameOnConfirmation = (TextView) findViewById(R.id.tvCharityNameOnConfirmation);
//		tvCharityAddressOnConfirmation = (TextView) findViewById(R.id.tvCharityAddressOnConfirmation);
//		tvCharityConfirmationUrl = (TextView) findViewById(R.id.tvCharityConfirmationUrl);
//		tvDonationAmount = (TextView) findViewById(R.id.tvDonationAmount);
//		tvDonationAmount.setText(donateAmount);

		//final int orgId = ppo.getInt("orgId");
		
		final int orgId = challenge.getOrganization();
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Organization");
		
		query.whereEqualTo("org_id", orgId);
		   query.getFirstInBackground(new GetCallback<ParseObject>() {
		        public void done(ParseObject parseObject, ParseException ParseError) {
		            Log.d(LOG_TAG,"inside done :"+parseObject.getString("description"));
		            if(ParseError == null){
		            	updateView(parseObject);
		            }else{
		                 Log.d(LOG_TAG, "Bombed error is :"+ParseError);
		            }
		        }
		    });	
		   
		   
		
	}
	
	private void updateView(ParseObject parseObject) {
//		tvCharityNameOnConfirmation.setText(parseObject.getString("name"));
//		tvCharityConfirmationUrl.setText(parseObject.getString("url"));
//		tvCharityAddressOnConfirmation.setText(parseObject.getString("address"));
		// Begin the transaction
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		// Replace the container with the new fragment
		NewDonationFragment newDonationFragment = new NewDonationFragment();
	    Bundle bundle = new Bundle();
	    bundle.putParcelable("challenge", challenge);
	    bundle.putParcelable("invitation", invitation);
	    bundle.putString("name", parseObject.getString("name"));
	    bundle.putString("url", parseObject.getString("url"));
	    bundle.putString("address", parseObject.getString("address"));
	    bundle.putString("amount",donateAmount);
	    newDonationFragment.setArguments(bundle);
		ft.replace(R.id.payment_confirm_fragment, newDonationFragment);
		ft.commit();
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
		
		// refresh invitation and challenge		
		// update and save invitation, challenge in parse
		// send push notifications
		// start new activity

		refreshFromParse();
	}

	public void refreshFromParse() {
		// refresh the invitation and challenge
		
		if( invitation != null ){
			invitation.fetchInBackground( new GetCallback<Invitation>(){

				@Override
				public void done(Invitation invitation,
						ParseException e) {
					
					if( e == null ){
						
						// refresh the challenge
						challenge.fetchInBackground( new GetCallback<Challenge>() {
							@Override
							public void done(Challenge c,
									ParseException e) {
								if( e == null ){
									saveInParse();
								}else{
									// handle exception
								}
							}
						});
						
					}else{
						// handle the error
					}
				}
			} );	
		}else{
			
			challenge.fetchInBackground( new GetCallback<Challenge>() {
				@Override
				public void done(Challenge c,
						ParseException e) {
					if( e == null ){
						saveInParse();
					}else{
						// handle exception
					}
				}
			});
		}
	}
	
	public void saveInParse() {
		
		if( invitation != null ){
			
			invitation.setStatus(InvitationStatusEnum.PIC_SENT.ordinal());
			double amt = Double.valueOf(donateAmount);
			invitation.setAmount(amt);
			
			// update the invitation and challenge
			invitation.saveInBackground( new SaveCallback() {
				@Override
				public void done(ParseException e) {
					if( e == null ){
						
						int oi = challenge.getOpenInvitations();
						oi = oi-1;
						int pi = challenge.getPaidInvitations();
						pi = pi +1;
						
						double a = challenge.getAmountRaised();
						a = a + invitation.getAmount();
						
						challenge.setOpenInvitation(oi);
						challenge.setClosedInvitations(pi);
						challenge.setAmountRaised(a);
						
						challenge.saveInBackground( new SaveCallback() {
							
							@Override
							public void done(ParseException e) {
								if( e == null ){
									sendPushNotification();
								}else{
									// TODO : handle exception
								}
							}
						});
					}else{
						// TODO: handle exception
					}
				}
			});
		
		}else{
			
			int oi = challenge.getOpenInvitations();
			oi = oi-1;
			int pi = challenge.getPaidInvitations();
			pi = pi +1;
			
			double amt = Double.valueOf(donateAmount);
			double a = challenge.getAmountRaised();
			a = a + amt;
			
			challenge.setOpenInvitation(oi);
			challenge.setClosedInvitations(pi);
			challenge.setAmountRaised(a);
			
			challenge.saveInBackground( new SaveCallback() {
				
				@Override
				public void done(ParseException e) {
					if( e == null ){
						sendPushNotification();
					}else{
						// TODO : handle exception
					}
				}
			});
		}
	}
	
	private void sendPushNotification() {
		
		if( invitation != null ){
			// send push notification
			ParsePush push = new ParsePush();
		
			String msg = InvitationMessageUtils.generateInvitationCompleteMsg(invitation, challenge);
			push.setMessage(msg);
		
			push.setChannel(CharityChallengerApplication.INVITATION_COMPLETE);
			push.sendInBackground( new SendCallback(){

				@Override
				public void done(ParseException e) {
					if( e == null ){
						
					}else{
						// handle error
					}
				}
			});
		}
		
		// start to fun activity
//		Intent data = new Intent();
//		data.putExtra("invitation", invitation);
//		data.putExtra("challenge", challenge);
//		data.putExtra("newPhotoUrl", picture.getPhotoFile().getUrl());
//
//		
//		getActivity().setResult(Activity.RESULT_OK, data);
//		getActivity().finish();
		
		//Intent intent = new Intent( this, FunActivity.class);
		Intent intent = new Intent( this, ChallengesHomeActivity.class);
		intent.putExtra("invitation", invitation);
		intent.putExtra("challenge", challenge);
		
		startActivity(intent);
	}
}
