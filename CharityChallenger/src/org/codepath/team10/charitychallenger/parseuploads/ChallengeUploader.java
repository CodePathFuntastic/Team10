package org.codepath.team10.charitychallenger.parseuploads;

import java.util.Arrays;

import org.codepath.team10.charitychallenger.models.Challenge;

import android.content.Context;

public class ChallengeUploader {

	public static void upload( Context context){
		
		Challenge c1 = new Challenge();
		
		c1.setId(100);
		c1.setOrganization(100);
		c1.setName("Eat a Cat");
		c1.setDescription("Catch and Eat a cat");
		c1.setTargetAmount(1000);
		c1.setAmountRaised(200);
		c1.setOpenInvitation(200);
		c1.setClosedInvitations(30);
		c1.setPaidInvitations(40);
		String[] pics1 = {"http://proof.nationalgeographic.com/files/2014/09/Sun-City-_Panorama.jpg", "http://proof.nationalgeographic.com/files/2014/03/MM8247_131003_0456941.jpg"};
		c1.addChallengePictureUrls(Arrays.asList(pics1));
		c1.saveInBackground();
		
		
		Challenge c2 = new Challenge();
		c2.setId(101);
		c2.setOrganization(101);
		c2.setName("Put your face in icecream");
		c2.setDescription("Eat the icecream");
		c2.setTargetAmount(5000);
		c2.setAmountRaised(300);
		c2.setOpenInvitation(100);
		c2.setClosedInvitations(5);
		c2.setPaidInvitations(30);
		String[] pics2 = {"http://proof.nationalgeographic.com/files/2014/03/MM8247_131009_056653.jpg", "http://proof.nationalgeographic.com/files/2014/03/MM8247_131003_047027.jpg", "http://proof.nationalgeographic.com/files/2014/03/MM8247_131003_047346.jpg"};
		c2.addChallengePictureUrls(Arrays.asList(pics2));
		c2.saveInBackground();

		
		Challenge c3 = new Challenge();
		c3.setId(102);
		c3.setOrganization(102);
		c3.setName("Put your face in icecream");
		c3.setDescription("Eat the icecream");
		c3.setTargetAmount(5000);
		c3.setAmountRaised(300);
		c3.setOpenInvitation(100);
		c3.setClosedInvitations(5);
		c3.setPaidInvitations(30);
		String[] pics3 = {"http://proof.nationalgeographic.com/files/2014/06/140618-vorhes-turbines-03.jpg","http://proof.nationalgeographic.com/files/2014/06/140618-vorhes-turbines-01.jpg"};
		c3.addChallengePictureUrls(Arrays.asList(pics3));
		c3.saveInBackground();


		Challenge c4 = new Challenge();
		c4.setId(103);
		c4.setOrganization(103);
		c4.setName("Dress up like a bear");
		c4.setDescription("Use a bug bear constume");
		c4.setTargetAmount(5000);
		c4.setAmountRaised(300);
		c4.setOpenInvitation(100);
		c4.setClosedInvitations(5);
		c4.setPaidInvitations(30);
		String[] pics4 = {"http://proof.nationalgeographic.com/files/2014/10/20140923_PFWSS15_0589.jpg","http://proof.nationalgeographic.com/files/2014/10/landon-nordeman-pfw-2-18.nocrop.w1800.h1330.2x.jpg"};
		c4.addChallengePictureUrls(Arrays.asList(pics4));
		c4.saveInBackground();

		
		Challenge c5 = new Challenge();
		c5.setId(104);
		c5.setOrganization(104);
		c5.setName("Put your face on ICE for 2 minutes");
		c5.setDescription("Get a big bar of ice from fridge");
		c5.setTargetAmount(3000);
		c5.setAmountRaised(500);
		c5.setOpenInvitation(500);
		c5.setClosedInvitations(56);
		c5.setPaidInvitations(76);
		String[] pics5 = {"http://proof.nationalgeographic.com/files/2014/09/featured_image.jpg", "http://proof.nationalgeographic.com/files/2014/09/20140919-tracks-06.jpg"};
		c5.addChallengePictureUrls(Arrays.asList(pics5));
		c5.saveInBackground();

		
		
		Challenge c6 = new Challenge();
		c6.setId(105);
		c6.setOrganization(105);
		c6.setName("Put your face on ICE for 2 minutes");
		c6.setDescription("Get a big bar of ice from fridge");
		c6.setTargetAmount(3000);
		c6.setAmountRaised(500);
		c6.setOpenInvitation(500);
		c6.setClosedInvitations(56);
		c6.setPaidInvitations(76);
		String[] pics6 = {"http://images.nationalgeographic.com/wpf/media-live/photos/000/271/cache/autumn-colors_27164_600x450.jpg", "http://images.nationalgeographic.com/wpf/media-live/photos/000/271/cache/autumn-colors-white-mountains_27175_600x450.jpg"};
		c6.addChallengePictureUrls(Arrays.asList(pics6));
		c6.saveInBackground();
		
	}
}
