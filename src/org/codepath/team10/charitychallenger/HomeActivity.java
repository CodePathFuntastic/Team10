package org.codepath.team10.charitychallenger;

import org.codepath.team10.charitychallenger.activities.NewPictureActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }
    
    public void onTakePictureClick(View v) {
//    	Intent intent = new Intent(this, NewPictureActivity.class);
//    	startActivity(intent);
    }
}
