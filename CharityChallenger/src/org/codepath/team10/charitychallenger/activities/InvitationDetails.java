package org.codepath.team10.charitychallenger.activities;

import org.codepath.team10.charitychallenger.ParseData;
import org.codepath.team10.charitychallenger.R;
import org.codepath.team10.charitychallenger.clients.PutAsyncTask;
import org.codepath.team10.charitychallenger.fragments.NewInvitationFragment;
import org.codepath.team10.charitychallenger.models.Challenge;
import org.codepath.team10.charitychallenger.models.Invitation;
import org.codepath.team10.charitychallenger.models.User;
import org.codepath.team10.charitychallenger.utils.RoundTransform;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.RefreshCallback;
import com.parse.SaveCallback;
import com.squareup.picasso.Picasso;

public class InvitationDetails extends BaseActivity {
	
	private Invitation mInvitation;
	private Challenge mChallenge;
	private ImageView ivChallengerImage;
	private TextView tvUserName;
	private ParseData parseData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_invitation_details);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		parseData = ParseData.getInstance();
		
		Intent intent = getIntent();

		if(intent.hasExtra("invitation")){
			mInvitation = (Invitation) intent.getParcelableExtra("invitation");
		}
		if( intent.hasExtra("challenge") ){
			mChallenge = (Challenge) intent.getParcelableExtra("challenge");
		}
		
		User sender = parseData.getFriendByFacebookId(mInvitation.getSender());
		
		// Begin the transaction
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		// Replace the container with the new fragment
		NewInvitationFragment newInvitationFragment = new NewInvitationFragment();
	    Bundle bundle = new Bundle();
	    bundle.putParcelable("challenge", mChallenge);
	    bundle.putParcelable("invitation", mInvitation);
	    newInvitationFragment.setArguments(bundle);
		ft.replace(R.id.invitation_detail_fragment, newInvitationFragment);
		ft.commit();
		
		ivChallengerImage = (ImageView) findViewById(R.id.ivSenderImage);
        Picasso.with(getApplicationContext()).load(sender.getImageUrl())
        									 .error(R.drawable.ic_launcher)
        									 .transform(new RoundTransform()).into(ivChallengerImage);

        tvUserName = (TextView) findViewById(R.id.etUserName);
        tvUserName.setText("Invited by " + sender.getName());
     
        setInvitationToOpened( mInvitation);
	}
	
	public void setInvitationToOpened( final Invitation invitation){
		
		PutAsyncTask async= new PutAsyncTask();
		String[] ids = { invitation.getObjectId() };
		async.execute(ids);
		
		
//		ParseQuery<ParseObject> query = ParseQuery.getQuery("Invitation");
//		query.getInBackground(invitation.getObjectId(), new GetCallback<ParseObject>() {
//			
//			@Override
//			public void done(ParseObject po, ParseException e) {
//				if( e == null ){
//					po.put("opened_status", true);
//					po.saveInBackground(new SaveCallback() {
//						
//						@Override
//						public void done(ParseException e) {
//							if( e != null ){
//								Log.e(BaseActivity.LOG_TAG, "Unable save Invitation", e);
//							}
//						}
//					});
//				}
//			}
//		});
		
//		Invitation invite = Invitation.createWithoutData(Invitation.class, invitation.getObjectId());
//		invite.refreshInBackground( new RefreshCallback(){
//			@Override
//			public void done(ParseObject po, ParseException e) {
//				if( e == null && po !=null ){
//					po.put("opened_status", true);
//					Log.d(BaseActivity.LOG_TAG, "Saving message " + po.getObjectId());
//					po.saveInBackground(new SaveCallback() {
//						@Override
//						public void done( ParseException e) {
//							if( e != null ){
//								Log.e(BaseActivity.LOG_TAG, "Unable to save object", e);
//							}
//						}
//					});
//				}
//			}
//		});
	}
		
	public void onClickAccept(View v) {
		 Intent intent = new Intent(this, NewPictureActivity.class);
         intent.putExtra("invitation", mInvitation);
         intent.putExtra("challenge", mChallenge);
 		 startActivityForResult(intent, 110);
	}
	
	public void onDonate(View view){
		Intent intent = new Intent(this, DonateActivity.class);
		intent.putExtra("invitation", mInvitation);
		intent.putExtra("challenge", mChallenge);
		startActivityForResult(intent, 120);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK && requestCode == 110) {
			Log.i(LOG_TAG, "Get back from the activity");
		}
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
