package org.codepath.team10.charitychallenger.activities;

import java.util.List;

import org.codepath.team10.charitychallenger.R;
import org.codepath.team10.charitychallenger.adapters.InvitationsAcceptedAdapter;
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

public class AllAcceptedActivity extends BaseActivity {

	private ListView mlvAllInvitationsAccepted;
	private InvitationsAcceptedAdapter mItemsAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all_accepted);
		
		mItemsAdapter = new InvitationsAcceptedAdapter(this, getAcceptedInvitations());
		
		mlvAllInvitationsAccepted = (ListView) findViewById(R.id.lvAllInvitationsAccepted);
		mlvAllInvitationsAccepted.setAdapter(mItemsAdapter);
		
		//setOnItemClickListener
		mlvAllInvitationsAccepted.setOnItemClickListener( new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(AllAcceptedActivity.this, InvitationAcceptedActivity.class);
				startActivity(intent);
			}
		});
		
		// if the invitations are 0, fire a parse query to load invitations.
		// then add a callback which notifies the adapter.
		if( getAcceptedInvitations().size() == 0 ){
			InvitationQuery.getInvitations( getUser().getFacebookId(), 
											InvitationStatusEnum.PIC_SENT, 
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
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.all_accepted, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
