package org.codepath.team10.charitychallenger.fragments;

import java.util.List;

import org.codepath.team10.charitychallenger.R;
import org.codepath.team10.charitychallenger.models.Challenge;
import org.codepath.team10.charitychallenger.models.Invitation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

public class NewInvitationFragment extends Fragment {
	
	private ImageView ivCharityChallenge;
	private TextView tvChallengeName;
	private TextView tvTargetAmount;
	private TextView tvRaised;
	private Challenge challenge;
	private Invitation invitation;
	ProgressBar challengeProgressBar;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		Bundle arguments = getArguments();
		
		challenge = (Challenge)arguments.get("challenge");
		invitation = (Invitation)arguments.get("invitation");
		
		View view = inflater.inflate(R.layout.item_challenge_new_for_other_activities, container, false); 
		ivCharityChallenge = (ImageView)view.findViewById(R.id.ivCharityChallenge);
		tvChallengeName = (TextView) view.findViewById(R.id.tvChallengeName);
		tvTargetAmount = (TextView) view.findViewById(R.id.tvTargetAmount);
		tvRaised = (TextView) view.findViewById(R.id.tvRaised);
		challengeProgressBar = (ProgressBar) view.findViewById(R.id.challengeProgressBar);

		if(tvChallengeName != null){
			tvChallengeName.setText(challenge.getString("name"));
		}
		if(tvTargetAmount != null){
			tvTargetAmount.setText("/ $" + challenge.getInt("target"));
		}
		
		if(tvRaised != null){
			tvRaised.setText("$" + challenge.getInt("raised") + " Raised");
		}
		
		if(ivCharityChallenge != null){
			List<String> url = challenge.getList("challenge_pic_urls");
			ImageLoader.getInstance().displayImage(url.get(0), ivCharityChallenge);
		}
		
		if(challengeProgressBar != null){
			int progress = (int)((double)challenge.getInt("raised")/(double)challenge.getInt("target") * 100);
			challengeProgressBar.setProgress(progress);
		}
		return view;
	}
}