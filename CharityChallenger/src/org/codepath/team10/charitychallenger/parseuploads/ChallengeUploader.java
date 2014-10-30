package org.codepath.team10.charitychallenger.parseuploads;

import java.util.Arrays;

import org.codepath.team10.charitychallenger.models.Challenge;

import android.content.Context;

public class ChallengeUploader {

	public static void upload( Context context){
		
		
		Challenge c1 = new Challenge();
		
		c1.setChallengeId(100);
		c1.setOrganization(100);
		c1.setName("Put whipped cream on your face");
		c1.setDescription("Raise money for constructing new buildings for laurelwood school in sunnyvale, CA");
		c1.setTargetAmount(1000);
		c1.setAmountRaised(200);
		c1.setOpenInvitation(200);
		c1.setClosedInvitations(30);
		c1.setPaidInvitations(40);
		String[] pics1 = {"http://www.t-g.com/photos/18/58/84/1858842-L.jpg", "http://cache3.asset-cache.net/gc/200017996-001-teenage-boy-having-cream-pie-thrown-at-gettyimages.jpg?v=1&c=IWSAsset&k=2&d=VR4Znm6PNEZbRnNhFRjSB1vp5BS7TLC5%2BPRnyrQXvQkbxxzEUrAL3EpI8vrNNd7a", "http://bloximages.chicago2.vip.townnews.com/nwitimes.com/content/tncms/assets/v3/editorial/b/cb/bcb91757-8307-51f0-9eb7-ef031465d9df/4f41a00748481.image.jpg"};
		c1.addChallengePictureUrls(Arrays.asList(pics1));
		c1.saveInBackground();
		
		
		Challenge c2 = new Challenge();
		c2.setChallengeId(101);
		c2.setOrganization(101);
		c2.setName("Jump into water");
		c2.setDescription("Raise funds to purchase computers for low income kids. We have 300 kids.");
		c2.setTargetAmount(5000);
		c2.setAmountRaised(300);
		c2.setOpenInvitation(100);
		c2.setClosedInvitations(5);
		c2.setPaidInvitations(30);
		String[] pics2 = {"http://www.thewizofodds.com/.a/6a00e553e551d18834011168eedaac970c-pi", "http://img.sparknotes.com/content/sparklife/sparktalk/jumpingdude_LargeWide.jpg", "https://c2.staticflickr.com/4/3649/3633471158_a87f1cc5e0.jpg"};
		c2.addChallengePictureUrls(Arrays.asList(pics2));
		c2.saveInBackground();

		
		Challenge c3 = new Challenge();
		c3.setChallengeId(102);
		c3.setOrganization(102);
		c3.setName("Put egg on your face");
		c3.setDescription("We are trying to protect the homeless people during winter by providing shelters during cold months. Please offer your support.");
		c3.setTargetAmount(5000);
		c3.setAmountRaised(300);
		c3.setOpenInvitation(100);
		c3.setClosedInvitations(5);
		c3.setPaidInvitations(30);
		String[] pics3 = {"http://www.stephenhannah.com/wp-content/uploads/egg-on-face.jpg", "http://bp2.blogger.com/_2upKmA8vnTk/SFghZC24-gI/AAAAAAAAALk/YbD4ENQisY0/s400/eggface.jpg", "http://itmakessenseblog.com/files/2012/04/Egg-on-face.jpg"};
		c3.addChallengePictureUrls(Arrays.asList(pics3));
		c3.saveInBackground();


		Challenge c4 = new Challenge();
		c4.setChallengeId(103);
		c4.setOrganization(103);
		c4.setName("Dress up like a pirate");
		c4.setDescription("Save the tropical forests of the world from loggers.");
		c4.setTargetAmount(5000);
		c4.setAmountRaised(300);
		c4.setOpenInvitation(100);
		c4.setClosedInvitations(5);
		c4.setPaidInvitations(30);
		String[] pics4 = {"http://media-cache-ec0.pinimg.com/236x/6f/f1/cb/6ff1cbcc279ed00c75fa4039f35e76ec.jpg", "http://www.dees-fancydress.co.uk/Pirates/sexy%20pirate.jpg","http://regmedia.co.uk/2010/04/30/pirates_westminster.jpg"};
		c4.addChallengePictureUrls(Arrays.asList(pics4));
		c4.saveInBackground();

		
		Challenge c5 = new Challenge();
		c5.setChallengeId(104);
		c5.setOrganization(104);
		c5.setName("Dress up like a baby");
		c5.setDescription("We are trying to help underprevileged children aged less than 10 years by providing them care, shelter and education. ");
		c5.setTargetAmount(3000);
		c5.setAmountRaised(500);
		c5.setOpenInvitation(500);
		c5.setClosedInvitations(56);
		c5.setPaidInvitations(76);
		String[] pics5 = {"http://www.costumecollection.com.au/img/b/a/blue-adult-baby-funny-costume-f358dd69.jpg","http://f00.inventorspot.com/images/6-_Baby_Parents.jpg","http://www.essaytigers.com/images/adult-baby-boomer-costume.jpg"};
		c5.addChallengePictureUrls(Arrays.asList(pics5));
		c5.saveInBackground();

		
		
		Challenge c6 = new Challenge();
		c6.setChallengeId(105);
		c6.setOrganization(105);
		c6.setName("Carry your dog");
		c6.setDescription("We rescue animals and offer them shelter");
		c6.setTargetAmount(3000);
		c6.setAmountRaised(500);
		c6.setOpenInvitation(500);
		c6.setClosedInvitations(56);
		c6.setPaidInvitations(76);
		String[] pics6 = {"http://img2-1.timeinc.net/people/i/2011/pets/news/110926/dog-carrier-300.jpg","http://www.woohome.com/wp-content/uploads/2009/03/walkingthedog.jpg", "http://imghumour.com/assets/Uploads/Dog-Carry-Case.jpg", ""};
		c6.addChallengePictureUrls(Arrays.asList(pics6));
		c6.saveInBackground();
		
	}
}
