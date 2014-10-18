package org.codepath.team10.charitychallenger.activities;

import java.util.ArrayList;
import java.util.List;

import org.codepath.team10.charitychallenger.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AllInvitationsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all_invitations);
		
		List<String> invitations = new ArrayList<String>();
		invitations.add("Invitation from bob");
		invitations.add("Invitation from dad");
		invitations.add("Invitation from joe");
		invitations.add("Invitation from mary");
		
		ArrayAdapter<String> itemsAdapter = 
			    new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, invitations);
		
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
}
