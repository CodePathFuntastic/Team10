package org.codepath.team10.charitychallenger.activities;

import java.util.List;

import org.codepath.team10.charitychallenger.R;
import org.codepath.team10.charitychallenger.adapters.InvitationsAdapter;
import org.codepath.team10.charitychallenger.models.Invitation;
import org.codepath.team10.charitychallenger.models.InvitationStatusEnum;
import org.codepath.team10.charitychallenger.queries.InvitationQuery;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;

public class AllInvitationsActivity extends BaseActivity {
	
	private ListView mLvAllInvitations;
	private InvitationsAdapter mItemsAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all_invitations);
		
		
		mItemsAdapter = new InvitationsAdapter(this, getInvitations());
		
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
		
		// if the invitations are 0, fire a parse query to load invitations.
		// then add a callback which notifies the adapter.
		if( getInvitations().size() == 0 ){
			InvitationQuery.getInvitations( getUser().getFacebookId(), 
											InvitationStatusEnum.OPEN, 
											false, 
											new FindCallback<Invitation>(){
												public void done(List<Invitation> invitations, 
															ParseException e){
													
													if( e == null ){
														if( invitations.size() > 0 ){
															getInvitations().addAll(invitations);
															
															// trigger the adapter to reload
															mItemsAdapter.notifyDataSetChanged();
														}
													}else{
														Log.e(LOG_TAG, "Unable to retreive invitations", e);
													}
												}
											});
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.new_picture, menu);
		return true;
	}
	
	public void launchInvitationDetails(){
		
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
