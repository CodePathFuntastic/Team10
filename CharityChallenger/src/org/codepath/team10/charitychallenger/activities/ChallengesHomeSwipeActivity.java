package org.codepath.team10.charitychallenger.activities;

import org.codepath.team10.charitychallenger.R;
import org.codepath.team10.charitychallenger.adapters.ChallengesFragmentPageAdapter;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

public class ChallengesHomeSwipeActivity extends BaseActivity implements ActionBar.TabListener{
	 private ActionBar mActionbar;
	 private ViewPager mViewpager;
	 private ChallengesFragmentPageAdapter mFragmentAdatper;
	 
	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 setContentView(R.layout.activity_home_challenges_viewpager);
	  
		 mViewpager = (ViewPager) findViewById(R.id.pager);
		 mFragmentAdatper = new ChallengesFragmentPageAdapter(getSupportFragmentManager());
		 mViewpager.setAdapter((PagerAdapter) mFragmentAdatper);
	  
		 mActionbar = getActionBar();
		 mActionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		 mActionbar.addTab(mActionbar.newTab().setText("Home").setTabListener(this));
		 mActionbar.addTab(mActionbar.newTab().setText("Received").setTabListener(this));
		 mActionbar.addTab(mActionbar.newTab().setText("Sent").setTabListener(this));
	  
		 mViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			 @Override
			 public void onPageSelected(int index) {
				 mActionbar.setSelectedNavigationItem(index);
			 }
	 
			 @Override
			 public void onPageScrolled(int arg0, float arg1, int arg2) {
			 }
			 
			 @Override
			 public void onPageScrollStateChanged(int arg0) {
			 }
		 });
	 }
	 
	@Override
	public void onTabSelected(Tab tab, android.app.FragmentTransaction ft) {
		// TODO Auto-generated method stub
		mViewpager.setCurrentItem(tab.getPosition());
	}
	@Override
	public void onTabUnselected(Tab tab, android.app.FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onTabReselected(Tab tab, android.app.FragmentTransaction ft) {
		// TODO Auto-generated method stub
	}
}
