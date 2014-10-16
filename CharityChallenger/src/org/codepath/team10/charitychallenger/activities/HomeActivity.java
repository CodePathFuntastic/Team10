package org.codepath.team10.charitychallenger.activities;

import org.codepath.team10.charitychallenger.R;
import org.codepath.team10.charitychallenger.adapters.ChallengesViewAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class HomeActivity extends Activity {
	
	private ListView lvChallenges;
	private ChallengesViewAdapter mChallengesAdapter; 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
        ListView lvChallenges = (ListView) findViewById(R.id.lvListOfChallenges);
        mChallengesAdapter = new ChallengesViewAdapter(this, "Challenge");
        lvChallenges.setAdapter(mChallengesAdapter);
        
      //OnItemClickListener
        lvChallenges.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                    int position, long id) {
                Intent intent = new Intent(HomeActivity.this, ChallengeDetailsActivity.class);
                
                //intent.putExtra("Challenge_id", imageData);
                startActivity(intent);
            }
        });
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
