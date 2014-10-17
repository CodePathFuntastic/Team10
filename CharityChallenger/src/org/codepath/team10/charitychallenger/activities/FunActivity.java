package org.codepath.team10.charitychallenger.activities;

import java.util.ArrayList;

import org.codepath.team10.charitychallenger.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FunActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fun);
		
		ArrayList<String> activities = new ArrayList<String>();
		
		activities.add("Cake Eating");
		activities.add("Dumping Fish");
		activities.add("Pumpkin Mela");
		activities.add("Water riding");
		activities.add("Blahgg");
		activities.add("XXX");
		activities.add("Ho ho");
		activities.add("Dkjkf");
		activities.add("III");
		
		ArrayAdapter<String> friendsAdapter = 
			    new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, activities);
		
		ListView list = (ListView)findViewById(R.id.lvFunChallenges);
		list.setAdapter(friendsAdapter);

	}
}