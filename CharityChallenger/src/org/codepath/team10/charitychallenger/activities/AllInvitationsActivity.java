package org.codepath.team10.charitychallenger.activities;

import java.util.ArrayList;
import java.util.List;

import org.codepath.team10.charitychallenger.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.ParseObject;

public class AllInvitationsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all_invitations);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		try{
		    // Get the Bundle Object        
		    Bundle bundleObject = getIntent().getExtras();
		             
		        // Get ArrayList Bundle
		    ArrayList<ParseObject> classObject = (ArrayList<ParseObject>) bundleObject.getSerializable("list_of_objects");
		             
		    //Retrieve Objects from Bundle
		    for(int index = 0; index < classObject.size(); index++){
		                 
		    	ParseObject Object = classObject.get(index);
		        Toast.makeText(getApplicationContext(), "Id is :"+Object.getString("name"), Toast.LENGTH_SHORT).show();
		    }
		} catch(Exception e){
		    e.printStackTrace();
		}
		
		
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
