package org.codepath.team10.charitychallenger.parseuploads;

import org.codepath.team10.charitychallenger.models.Challenge;

import android.content.Context;

public class ChallengeUploader {

	public static void upload( Context context){
		
		Challenge c = new Challenge();
		
		c.setId(100);
		c.setOrganization(100);
		c.setName("Eat a Cat");
		c.setDescription("Catch and Eat a cat");
		c.setTargetAmount(1000);
		c.setAmountRaised(200);
		c.setOpenInvitation(200);
		c.setClosedInvitations(30);
		c.setPaidInvitations(40);
		//
		//
		//
		//
		c.saveInBackground();
		
		
		c = new Challenge();
		c.setId(101);
		c.setOrganization(101);
		c.setName("Put your face in icecream");
		c.setDescription("Eat the icecream");
		c.setTargetAmount(5000);
		c.setAmountRaised(300);
		c.setOpenInvitation(100);
		c.setClosedInvitations(5);
		c.setPaidInvitations(30);
		//
		//
		//
		//
		c.saveInBackground();

		
		c = new Challenge();
		c.setId(101);
		c.setOrganization(101);
		c.setName("Put your face in icecream");
		c.setDescription("Eat the icecream");
		c.setTargetAmount(5000);
		c.setAmountRaised(300);
		c.setOpenInvitation(100);
		c.setClosedInvitations(5);
		c.setPaidInvitations(30);
		//
		//
		//
		//
		c.saveInBackground();


		c = new Challenge();
		c.setId(102);
		c.setOrganization(102);
		c.setName("Dress up like a bear");
		c.setDescription("Use a bug bear constume");
		c.setTargetAmount(5000);
		c.setAmountRaised(300);
		c.setOpenInvitation(100);
		c.setClosedInvitations(5);
		c.setPaidInvitations(30);
		//
		//
		//
		//
		c.saveInBackground();

		
		c = new Challenge();
		c.setId(103);
		c.setOrganization(103);
		c.setName("Put your face on ICE for 2 minutes");
		c.setDescription("Get a big bar of ice from fridge");
		c.setTargetAmount(3000);
		c.setAmountRaised(500);
		c.setOpenInvitation(500);
		c.setClosedInvitations(56);
		c.setPaidInvitations(76);
		//
		//
		//
		//
		c.saveInBackground();

		
		
		c = new Challenge();
		c.setId(103);
		c.setOrganization(103);
		c.setName("Put your face on ICE for 2 minutes");
		c.setDescription("Get a big bar of ice from fridge");
		c.setTargetAmount(3000);
		c.setAmountRaised(500);
		c.setOpenInvitation(500);
		c.setClosedInvitations(56);
		c.setPaidInvitations(76);
		//
		//
		//
		//
		c.saveInBackground();

		
		c = new Challenge();
		c.setId(104);
		c.setOrganization(104);
		c.setName("Make a funny face");
		c.setDescription("you should look like a clown");
		c.setTargetAmount(9000);
		c.setAmountRaised(500);
		c.setOpenInvitation(900);
		c.setClosedInvitations(99);
		c.setPaidInvitations(342);
		//
		//
		//
		//
		c.saveInBackground();
		
		
		c = new Challenge();
		c.setId(105);
		c.setOrganization(105);
		c.setName("Jump into water");
		c.setDescription("You should become wet");
		c.setTargetAmount(1000);
		c.setAmountRaised(900);
		c.setOpenInvitation(100);
		c.setClosedInvitations(9);
		c.setPaidInvitations(23);
		//
		//
		//
		//
		c.saveInBackground();
		
	}
}
