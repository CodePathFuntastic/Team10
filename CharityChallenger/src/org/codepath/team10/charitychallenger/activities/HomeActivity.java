package org.codepath.team10.charitychallenger.activities;

import java.util.ArrayList;

import org.codepath.team10.charitychallenger.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HomeActivity extends Activity {
	
	private ListView lvChallenges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
        ListView lvChallenges = (ListView) findViewById(R.id.lvListOfChallenges);
        
        ArrayList<String> items = new ArrayList<String>();
        items.add("Item 1");
        items.add("Item 2");
        items.add("Item 3");
        items.add("Item 4");
        items.add("Item 5");
        
        ArrayAdapter<String> itemsAdapter = 
        	    new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        
        lvChallenges.setAdapter(itemsAdapter);
        
    }
    
    public void onChallengeDetails(View view){
    	Intent intent = new Intent(this, ChallengeDetailsActivity.class);
    	startActivity(intent);	
    }
    
    /*
    public void onFacebookLogin(View view){
    	Intent intent = new Intent(this, FacebookLoginActivity.class);
    	startActivity(intent);
    }
    */
}
