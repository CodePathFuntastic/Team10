package org.codepath.team10.charitychallenger.activities;

import org.codepath.team10.charitychallenger.R;
import org.codepath.team10.charitychallenger.fragments.NewPictureFragment;
import org.codepath.team10.charitychallenger.helper.ParseProxyObject;
import org.codepath.team10.charitychallenger.models.Picture;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class NewPictureActivity extends BaseActivity {
	
	private Picture picture;
    private ParseProxyObject ppo;
//    private TextView tvChallengeDescription;
//    private TextView tvChallegeTitle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		picture = new Picture();
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_picture);
		
//		tvChallengeDescription = (TextView) findViewById(R.id.tvChallengeDescription);
//		tvChallegeTitle = (TextView) findViewById(R.id.tvChallegeTitle);

		Intent intent = getIntent();

		if(intent.hasExtra("parseObject")){
			ppo = (ParseProxyObject) intent.getSerializableExtra("parseObject");
			//tvChallengeDescription.setText(ppo.getString("name"));
			//tvChallegeTitle.setText(ppo.getString("name"));
		}
		
		FragmentManager manager = getFragmentManager();
		Fragment fragment = manager.findFragmentById(R.id.fragmentContainer);

		if (fragment == null) {
			fragment = new NewPictureFragment();
			manager.beginTransaction()
					.add(R.id.fragmentContainer, fragment)
					.commit();
//		    Bundle bundle = new Bundle();
//		    bundle.putSerializable("invitation", ppo);
//		    fragment.setArguments(bundle);
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

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
