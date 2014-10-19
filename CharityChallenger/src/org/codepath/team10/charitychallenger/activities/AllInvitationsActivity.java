package org.codepath.team10.charitychallenger.activities;

import org.codepath.team10.charitychallenger.R;
import org.codepath.team10.charitychallenger.adapters.ReceivedInvitationAdapter;
import org.codepath.team10.charitychallenger.models.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class AllInvitationsActivity extends BaseActivity {

	//private List<Invitation> mInvitations;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all_invitations);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		
		ReceivedInvitationAdapter itemsAdapter = 
			    new ReceivedInvitationAdapter(this, getInvitations());
		
		ListView lvAllInvitations = (ListView) findViewById(R.id.lvAllInvitations);
		lvAllInvitations.setAdapter(itemsAdapter);
		
		//setOnItemClickListener
		lvAllInvitations.setOnItemClickListener( new OnItemClickListener(){

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
