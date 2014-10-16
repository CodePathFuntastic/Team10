package org.codepath.team10.charitychallenger.parseuploads;

import org.codepath.team10.charitychallenger.models.Organization;

import android.content.Context;

public class OrganizationUploader {
	
	public static void upload( Context context){
	

		Organization o = new Organization();
		o.setOrgId(100);
		o.setChallengeId(100);
		o.setName("the-school-food-fight-is-on");
		o.setDescription("We create tools and resources to help schools transition to scratch cooking and providing fully-equipped kitchens with training, successful models and salad bars");
		// https://dpqe0zkrjo0ak.cloudfront.net/pfil/18474/ph_18474_65842.jpg
		// https://dpqe0zkrjo0ak.cloudfront.net/pfil/18474/pict_original.jpg
		o.setAddress("Boulder, CO, United States");
		o.setUrl("http://www.globalgiving.org/microprojects/the-school-food-fight-is-on/");
		o.saveInBackground();
		
		o = new Organization();
		o.setOrgId(101);
		o.setChallengeId(101);
		o.setName("Help 455 At-Risk Children in Mexico Stay in School");
		o.setDescription("Provide early education, breakfast, child care and psychological services for 455 disadvantaged preschoolers in the Mexico City Metro Area whose working parents cannot afford these services otherwise.");
		o.setAddress("mexico");
		// https://dpqe0zkrjo0ak.cloudfront.net/pfil/3788/ph_3788_13223.jpg
		// https://dpqe0zkrjo0ak.cloudfront.net/pfil/3788/ph_3788_13226.jpg
		// https://dpqe0zkrjo0ak.cloudfront.net/pfil/3788/ph_3788_29856.jpg
		// https://dpqe0zkrjo0ak.cloudfront.net/pfil/3788/ph_3788_13220.jpg
		o.setUrl("http://www.globalgiving.org/projects/help-455-at-risk-children-in-mexico-stay-in-school/");
		o.saveInBackground();
	
		o = new Organization();
		o.setOrgId(102);
		o.setChallengeId(102);
		o.setName("St. Jude Children's Research Hospital");
		o.setDescription("Finding Cures. Saving Children. St. Jude Children's Research Hospital is leading the way the world understands, treats and defeats childhood cancer and other deadly diseases.");
		o.setAddress("Memphis, TN, United States");
		// https://dpqe0zkrjo0ak.cloudfront.net/pfil/3400/ph_3400_13463.jpg
		// https://dpqe0zkrjo0ak.cloudfront.net/pfil/3400/ph_3400_52566.jpg
		// https://dpqe0zkrjo0ak.cloudfront.net/pfil/3400/ph_3400_52567.jpg
		// https://dpqe0zkrjo0ak.cloudfront.net/pfil/3400/ph_3400_52569.jpg
		o.setUrl("http://www.globalgiving.org/projects/help-455-at-risk-children-in-mexico-stay-in-school/");
		o.saveInBackground();
		
		o = new Organization();
		o.setOrgId(103);
		o.setChallengeId(103);
		o.setName("Help a deserving student in NYC earn a degree");
		o.setDescription("New York is a gateway city for immigrants to America. St. George's Society's Scholarship Program assists talented students of recent British and Commonwealth background at Lehman College, part of The City University of New York, to earn their degrees, start careers and become integrated in New York and the US.");
		o.setAddress("New York, NY, United States ");
		// https://dpqe0zkrjo0ak.cloudfront.net/pfil/16423/pict_grid7.jpg
		o.setUrl("http://www.globalgiving.org/projects/scholarships-in-nyc/");
		o.saveInBackground();

		o = new Organization();
		o.setOrgId(104);
		o.setChallengeId(104);
		o.setName("Literacy Programs for Children");
		o.setDescription("New York is a gateway city for immigrants to America. St. George's Society's Scholarship Program assists talented students of recent British and Commonwealth background at Lehman College, part of The City University of New York, to earn their degrees, start careers and become integrated in New York and the US.");
		o.setAddress("New York, NY, United States ");
		// https://dpqe0zkrjo0ak.cloudfront.net/pfil/8959/ph_8959_30337.jpg
		// https://dpqe0zkrjo0ak.cloudfront.net/pfil/8959/ph_8959_38113.jpg
		// https://dpqe0zkrjo0ak.cloudfront.net/pfil/8959/ph_8959_30336.jpg
		// https://dpqe0zkrjo0ak.cloudfront.net/pfil/8959/ph_8959_30335.jpg
		o.setUrl("http://www.globalgiving.org/projects/scholarships-in-nyc/");
		o.saveInBackground();
		
		o = new Organization();
		o.setOrgId(105);
		o.setChallengeId(105);
		o.setName("Save the Lives of Shelter Pets in North America");
		o.setDescription("Your donation will help more than 13,700 shelters and rescue groups in the U.S., Canada and Mexico improve the lives of homeless pets and help them get adopted. ");
		o.setAddress("Tucson, AZ, United States");
		// https://dpqe0zkrjo0ak.cloudfront.net/pfil/10612/pict_original.jpg
		// https://dpqe0zkrjo0ak.cloudfront.net/pfil/10612/ph_10612_52384.jpg
		// https://dpqe0zkrjo0ak.cloudfront.net/pfil/10612/ph_10612_52385.jpg
		// https://dpqe0zkrjo0ak.cloudfront.net/pfil/10612/ph_10612_52383.jpg
		o.setUrl("http://www.globalgiving.org/projects/save-the-lives-of-shelter-pets-in-north-america/");
		o.saveInBackground();
	}
}
