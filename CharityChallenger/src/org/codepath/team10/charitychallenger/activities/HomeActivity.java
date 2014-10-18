package org.codepath.team10.charitychallenger.activities;

import org.codepath.team10.charitychallenger.R;
import org.codepath.team10.charitychallenger.adapters.ChallengesViewAdapter;
import org.codepath.team10.charitychallenger.helper.ParseProxyObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.parse.ParseObject;

public class HomeActivity extends Activity {
	
	private ListView lvChallenges;
	private ChallengesViewAdapter mChallengesAdapter; 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
        lvChallenges = (ListView) findViewById(R.id.lvListOfChallenges);
        mChallengesAdapter = new ChallengesViewAdapter(this, "Challenge");
        lvChallenges.setAdapter(mChallengesAdapter);
        
        //OnItemClickListener
        lvChallenges.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                    int position, long id) {
                Intent intent = new Intent(HomeActivity.this, ChallengeDetailsActivity.class);
                ParseObject parseObject = mChallengesAdapter.getItem(position);
                ParseProxyObject ppo = new ParseProxyObject(parseObject);
                intent.putExtra("parseObject", ppo);
                startActivity(intent);
            }
        });
    }
    
    public void onChallengeDetails(View view){
    	Intent intent = new Intent(this, ChallengeDetailsActivity.class);
    	startActivity(intent);	
    }
	
    public boolean onCreateOptionsMenu(Menu menu) {
    	getMenuInflater().inflate(R.menu.home_activity_menu, menu);

        RelativeLayout badgeLayout = (RelativeLayout) menu.findItem(R.id.badge).getActionView();
        TextView tv = (TextView) badgeLayout.findViewById(R.id.actionbar_notifcation_textview);
        tv.setText("12");
        
        
        MenuItemCompat.getActionView(menu.findItem(R.id.badge)).findViewById(R.id.ivReceivedChallenges).setOnClickListener(new OnClickListener() {	
        	@Override 
        	public void onClick(View v) {
        		//Intent intent = new Intent(HomeActivity.this, InviteFriendsActivity.class);
        		Intent intent = new Intent(HomeActivity.this, AllInvitationsActivity.class);
    			startActivity(intent);
        		} 
        	});
		return true;
    }
    
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		int id = item.getItemId();
//		if (id == R.id.badge) {
//			return true;
//		}
//		return super.onOptionsItemSelected(item);
//	}
}
