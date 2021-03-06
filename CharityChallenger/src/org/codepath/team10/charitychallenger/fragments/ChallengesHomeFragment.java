package org.codepath.team10.charitychallenger.fragments;

import org.codepath.team10.charitychallenger.R;
import org.codepath.team10.charitychallenger.activities.ChallengeDetailsActivity;
import org.codepath.team10.charitychallenger.adapters.ChallengesViewAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;

public class ChallengesHomeFragment extends Fragment {

	private ListView mlvChallenges; 
	private ChallengesViewAdapter mChallengesAdapter;
	private ProgressBar mProgressBar;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		mChallengesAdapter = new ChallengesViewAdapter(getActivity(), "Challenge");
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, 
			@Nullable Bundle savedInstanceState) {
		
		super.onCreateView(inflater, container, savedInstanceState);
		
		//inflater.inflate( R.layout.fragment_all_invitations, container, false);
		View view = inflater.inflate(R.layout.fragment_home_challenges, container, false); // passing false is important here.
		mlvChallenges = (ListView) view.findViewById(R.id.lvChallenges);
		mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);

		mlvChallenges.setAdapter(mChallengesAdapter);
	      
		//OnItemClickListener
		mlvChallenges.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, 
		          						View v,
		          						int position, 
		          						long id) {
		              
				Intent intent = new Intent(getActivity(), ChallengeDetailsActivity.class);   
				intent.putExtra("challenge", mChallengesAdapter.getItem(position)); 
				startActivityForResult(intent, 201);
				getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
			}
		});
	      
		mProgressBar.setVisibility(view.GONE);  
		return view;
	}
	
	private void populateChallengesHome(){
		
	}
}
