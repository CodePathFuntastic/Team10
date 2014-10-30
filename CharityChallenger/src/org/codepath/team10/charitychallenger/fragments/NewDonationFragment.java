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

public class NewDonationFragment extends Fragment {
	
	private ImageView ivDonateCharityChallenge;
	private TextView tvDonateCharityNameValue;
	private TextView tvDonateCharityUrlValue;
	private TextView tvDonateAddressValue;
	private TextView tvDonateTargetAmount;
	private TextView tvDonateRaised;

	private Challenge challenge;
	private Invitation invitation;
	private String name;
	private String address;
	private String url;
    private String donateAmount;
	ProgressBar challengeDonateProgressBar;
	
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
		name = (String)arguments.get("name");
		address = (String)arguments.get("address");
		url = (String)arguments.get("url");
		donateAmount = (String)arguments.get("donateAmount");
		
		View view = inflater.inflate(R.layout.item_donate_new_for_other_activities, container, false); 
		
		
		
		ivDonateCharityChallenge = (ImageView)view.findViewById(R.id.ivDonateCharityChallenge);
		tvDonateCharityNameValue = (TextView) view.findViewById(R.id.tvDonateChallengeName);
		tvDonateTargetAmount = (TextView) view.findViewById(R.id.tvDonateTargetAmount);
		tvDonateRaised = (TextView) view.findViewById(R.id.tvDonateRaised);
		challengeDonateProgressBar = (ProgressBar) view.findViewById(R.id.challengeDonateProgressBar);

		String thankyou = 
				 "Thank you for your donation $" 
				+ donateAmount + " to " + name + " at "
				+ "Address: " + address;
		
		if(tvDonateCharityNameValue != null){
			tvDonateCharityNameValue.setText(thankyou);
		}
		if(tvDonateTargetAmount != null){
			tvDonateTargetAmount.setText("/ $" + challenge.getInt("target"));
		}
		
		if(tvDonateRaised != null){
			tvDonateRaised.setText("$" + challenge.getInt("raised") + " Raised");
		}
		
		if(ivDonateCharityChallenge != null){
			List<String> url = challenge.getList("challenge_pic_urls");
			ImageLoader.getInstance().displayImage(url.get(0), ivDonateCharityChallenge);
		}
		
		if(tvDonateRaised != null){
			int progress = (int)((double)challenge.getInt("raised")/(double)challenge.getInt("target") * 100);
			challengeDonateProgressBar.setProgress(progress);
		}
		return view;
	}
}
