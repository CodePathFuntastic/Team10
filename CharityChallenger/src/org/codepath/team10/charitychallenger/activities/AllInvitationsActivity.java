package org.codepath.team10.charitychallenger.activities;

import org.codepath.team10.charitychallenger.R;
import org.codepath.team10.charitychallenger.adapters.ReceivedInvitationAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class AllInvitationsActivity extends BaseActivity {
	private ListView mLvAllInvitations;
	private ReceivedInvitationAdapter mItemsAdapter;
	//private List<Invitation> mInvitations;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all_invitations);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		if( getInvitations().size() == 0 ){
			// fire an event to get all invitations
		}
		
		mItemsAdapter = new ReceivedInvitationAdapter(this, getInvitations());
		
		mLvAllInvitations = (ListView) findViewById(R.id.lvAllInvitations);
		mLvAllInvitations.setAdapter(mItemsAdapter);
		
		//setOnItemClickListener
		mLvAllInvitations.setOnItemClickListener( new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(AllInvitationsActivity.this, InvitationDetails.class);
				startActivity(intent);
			}
		});
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
