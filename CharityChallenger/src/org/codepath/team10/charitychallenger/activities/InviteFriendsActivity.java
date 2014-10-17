package org.codepath.team10.charitychallenger.activities;

import java.util.ArrayList;

import org.codepath.team10.charitychallenger.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class InviteFriendsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_invite_friends);
		
		ArrayList<String> friends = new ArrayList<String>();
		
		friends.add("Joe");
		friends.add("Mary");
		friends.add("Martha");
		friends.add("Nathan");
		friends.add("Syed");
		friends.add("Kevin");
		friends.add("Nambi");
		friends.add("Shiva");
		friends.add("Jim");
		
		ArrayAdapter<String> friendsAdapter = 
			    new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, friends);
		
		ListView list = (ListView)findViewById(R.id.lvListOfFriends);
		list.setAdapter(friendsAdapter);
		
		//setOnItemClickListener
		list.setOnItemClickListener( new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(InviteFriendsActivity.this, FunActivity.class);
				startActivity(intent);
			}
			
		});
	}
	
	public void onClickInvite( View view){
		Intent intent = new Intent(this, FunActivity.class);
		startActivity(intent);
	}
}
