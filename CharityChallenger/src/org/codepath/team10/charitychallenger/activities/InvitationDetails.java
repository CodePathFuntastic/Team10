package org.codepath.team10.charitychallenger.activities;

import org.codepath.team10.charitychallenger.ParseData;
import org.codepath.team10.charitychallenger.R;
import org.codepath.team10.charitychallenger.fragments.NewInvitationFragment;
import org.codepath.team10.charitychallenger.models.Challenge;
import org.codepath.team10.charitychallenger.models.Invitation;
import org.codepath.team10.charitychallenger.models.User;
import org.codepath.team10.charitychallenger.utils.RoundTransform;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
