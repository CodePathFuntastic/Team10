package org.codepath.team10.charitychallenger.adapters;

import org.codepath.team10.charitychallenger.fragments.ChallengesHomeFragment;
import org.codepath.team10.charitychallenger.fragments.ReceivedInvitationsFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ChallengesFragmentPageAdapter extends FragmentPagerAdapter {
	 public ChallengesFragmentPageAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	 @Override
	 public Fragment getItem(int arg0) {
	  // TODO Auto-generated method stub
	  switch (arg0) {
	  case 0:
	      return new ChallengesHomeFragment();
	  case 1:
	      return new ReceivedInvitationsFragment();
//	  case 2:
//	     return new SentInvitationsFragment();
	  default:
	   break;
	  }
	  return null;
	 }

	 @Override
	 public int getCount() {
	  // TODO Auto-generated method stub
	  return 2;
	 }


	}