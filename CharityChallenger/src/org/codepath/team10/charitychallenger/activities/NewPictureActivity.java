package org.codepath.team10.charitychallenger.activities;

import org.codepath.team10.charitychallenger.R;
import org.codepath.team10.charitychallenger.fragments.NewPictureFragment;
import org.codepath.team10.charitychallenger.models.Challenge;
import org.codepath.team10.charitychallenger.models.Invitation;
import org.codepath.team10.charitychallenger.models.Picture;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

public class NewPictureActivity extends BaseActivity {
	
	private Picture picture;
    private Challenge challenge;
    private Invitation invitation;
//    private TextView tvChallengeDescription;
//    private TextView tvChallegeTitle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		picture = new Picture();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_picture);
		
		Intent intent = getIntent();
		
		if( intent.hasExtra("challenge") ){
			challenge = intent.getParcelableExtra("challenge");
		}
		
		if( intent.hasExtra("invitation")){
			invitation = intent.getParcelableExtra("invitation");
		}
		
		FragmentManager manager = getFragmentManager();
		Fragment fragment = manager.findFragmentById(R.id.fragmentContainer);

		if (fragment == null) {
			fragment = new NewPictureFragment();
			
		    Bundle bundle = new Bundle();
		    bundle.putParcelable("challenge", challenge);
		    bundle.putParcelable("invitation", invitation);
		    fragment.setArguments(bundle);

			manager.beginTransaction()
					.add(R.id.fragmentContainer, fragment)
					.commit();
		}
	}
	
	public Picture getCurrentPicture() {
		return picture;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.new_picture, menu);
		return true;
	}
}
