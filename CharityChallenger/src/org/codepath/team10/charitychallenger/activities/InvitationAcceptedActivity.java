package org.codepath.team10.charitychallenger.activities;

import org.codepath.team10.charitychallenger.R;
import org.codepath.team10.charitychallenger.models.Invitation;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

public class InvitationAcceptedActivity extends BaseActivity {

	private Invitation mInvitation;
	ImageView mIvAcceptedPicture;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_invitation_accepted);
		
		mIvAcceptedPicture = (ImageView) findViewById(R.id.ivAcceptedPicture);
		Intent intent = getIntent();
		if(intent.hasExtra("invitation")){
			mInvitation = (Invitation) intent.getParcelableExtra("invitation");
		}
		ImageLoader.getInstance().displayImage(mInvitation.getPhotos().get(0), mIvAcceptedPicture);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.invitation_accepted, menu);
		return true;
	}
	
	public void OnDone(View view){
		this.finish();
	}
}
