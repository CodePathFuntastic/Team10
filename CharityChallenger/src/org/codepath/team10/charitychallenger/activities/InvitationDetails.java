package org.codepath.team10.charitychallenger.activities;

import org.codepath.team10.charitychallenger.R;
import org.codepath.team10.charitychallenger.fragments.NewInvitationFragment;
import org.codepath.team10.charitychallenger.models.Challenge;
import org.codepath.team10.charitychallenger.models.Invitation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class InvitationDetails extends BaseActivity {
	
	private Invitation mInvitation;
	private Challenge mChallenge;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_invitation_details);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		Intent intent = getIntent();

		if(intent.hasExtra("invitation")){
			mInvitation = (Invitation) intent.getParcelableExtra("invitation");
		}
		if( intent.hasExtra("challenge") ){
			mChallenge = (Challenge) intent.getParcelableExtra("challenge");
		}
		
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
