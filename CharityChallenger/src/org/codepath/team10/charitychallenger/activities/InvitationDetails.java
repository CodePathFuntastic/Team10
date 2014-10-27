package org.codepath.team10.charitychallenger.activities;

import java.util.List;

import org.codepath.team10.charitychallenger.R;
import org.codepath.team10.charitychallenger.fragments.NewInvitationFragment;
import org.codepath.team10.charitychallenger.models.Challenge;
import org.codepath.team10.charitychallenger.models.Invitation;
import org.codepath.team10.charitychallenger.queries.ChallengeQueries;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.parse.FindCallback;
import com.parse.ParseException;

public class InvitationDetails extends BaseActivity {
	
	private Fragment fragment;
	private TextView mTvChallengeName;
	private TextView mTvTarget;
	private TextView mTvRaised;
	private TextView mTvDesc;
//    private ParseProxyObject ppo;
	
	private Invitation mInvitation;
	private Challenge mChallenge;
	private ImageView mIvCharity;
	
	public static final String MAIN_CHANNEL = "MAIN_CHANNEL";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_invitation_details);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		int challengeId = 0;
		Intent intent = getIntent();

		if(intent.hasExtra("invitation")){
			mInvitation = (Invitation) intent.getParcelableExtra("invitation");
			challengeId = mInvitation.getInt("challengeId");
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
				
//		mTvChallengeName = (TextView) findViewById(R.id.tvCharityChallengeName);
//		mTvTarget = (TextView) findViewById(R.id.tvTargetAmountRaised);
//		mTvRaised = (TextView) findViewById(R.id.tvAmountRaised);
//		mTvDesc = (TextView) findViewById(R.id.tvDescription);
//		mIvCharity = (ImageView) findViewById(R.id.ivCharity); 
		
//		if(challengeId != 0){
//			
//			ChallengeQueries.getChallengeById( challengeId, new FindCallback<Challenge>() {
//
//				@Override
//				public void done(List<Challenge> list,
//								ParseException e) {
//					
//					if( e == null ){
//						// should be only one challenge
//						Challenge c = list.get(0);
//						
//						mTvChallengeName.setText(c.getName());
//						mTvTarget.setText( "$" +c.getTargetAmount());
//						mTvTarget.setText("$" + c.getAmountRaised());
//						mTvDesc.setText(c.getDescription());
//						List<String> imageUrls = c.getChallengesPictureUrls();
//						if( imageUrls.size() > 0){
//							String image = imageUrls.get(0);
////					        Picasso.with(getApplicationContext()).load(image)
////					        .error(R.drawable.ic_launcher).transform(new RoundTransform())
////					        .into(mIvCharity);						
//							ImageLoader.getInstance().displayImage(image, mIvCharity);
//						}
//					}
//				}
//			});
//		}
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
