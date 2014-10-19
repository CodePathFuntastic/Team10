package org.codepath.team10.charitychallenger.fragments;

import org.codepath.team10.charitychallenger.activities.BaseActivity;

import android.app.Activity;
import android.os.Bundle;

import com.facebook.widget.FriendPickerFragment;

public class CustomFbFriendsPickerFragment extends FriendPickerFragment {

	BaseActivity baseActivity;
	
	public CustomFbFriendsPickerFragment(Bundle args) {
		super(args);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Activity a = getActivity();
		if( a instanceof BaseActivity ){
			baseActivity = (BaseActivity) a;
		}
	}
	
	public BaseActivity getBaseActivity(){
		return baseActivity;
	}
}
