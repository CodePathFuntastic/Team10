package org.codepath.team10.charitychallenger.activities;

import org.codepath.team10.charitychallenger.R;
import org.codepath.team10.charitychallenger.adapters.ChallengesViewAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.parse.ParseObject;
import com.parse.ParseQuery;

public class HomeActivity extends BaseActivity {
	
	private ListView mlvChallenges;
	private ChallengesViewAdapter mChallengesAdapter; 
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_home);
        
		
        mlvChallenges = (ListView) findViewById(R.id.lvListOfChallenges);
        mChallengesAdapter = new ChallengesViewAdapter(this, "Challenge");
        mlvChallenges.setAdapter(mChallengesAdapter);
        
        //OnItemClickListener
        mlvChallenges.setOnItemClickListener(new OnItemClickListener() {
        	
            public void onItemClick(AdapterView<?> parent, 
            						View v,
            						int position, 
            						long id) {
                
            	Intent intent = new Intent(HomeActivity.this, ChallengeDetailsActivity.class);
               
                intent.putExtra("challenge", mChallengesAdapter.getItem(position));
                
                startActivity(intent);
            }
        });
    }
    
    public void onChallengeDetails(View view){
    	Intent intent = new Intent(this, ChallengeDetailsActivity.class);
    	startActivity(intent);	
    }
	   
//    private ParseQuery<ParseObject> create() {
//		Log.d("HomeActivity: ", "Invitation query");
//		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Invitation");
//		query.whereEqualTo("receiver", "syed");
//		query.whereEqualTo("status", 1);
//		return query;
//	}
}
