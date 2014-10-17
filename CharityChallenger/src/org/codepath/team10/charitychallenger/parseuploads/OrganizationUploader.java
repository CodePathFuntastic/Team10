package org.codepath.team10.charitychallenger.parseuploads;

import java.util.Arrays;

import org.codepath.team10.charitychallenger.models.Organization;

import android.content.Context;

import com.activeandroid.util.Log;
import com.parse.ParseException;
import com.parse.SaveCallback;

public class OrganizationUploader {
	
	public static void upload( Context context){
	

		// save pictures first
		
		String[] pics1 = {"https://dpqe0zkrjo0ak.cloudfront.net/pfil/18474/ph_18474_65842.jpg",
							"https://dpqe0zkrjo0ak.cloudfront.net/pfil/18474/pict_original.jpg"};

		String[] pics2 = {"https://dpqe0zkrjo0ak.cloudfront.net/pfil/3788/ph_3788_13223.jpg",
				"https://dpqe0zkrjo0ak.cloudfront.net/pfil/3788/ph_3788_13226.jpg",
				"https://dpqe0zkrjo0ak.cloudfront.net/pfil/3788/ph_3788_29856.jpg",
				"https://dpqe0zkrjo0ak.cloudfront.net/pfil/3788/ph_3788_13220.jpg"};

		String[] pics3 = {"https://dpqe0zkrjo0ak.cloudfront.net/pfil/3400/ph_3400_13463.jpg", 
				"https://dpqe0zkrjo0ak.cloudfront.net/pfil/3400/ph_3400_52566.jpg",
				"https://dpqe0zkrjo0ak.cloudfront.net/pfil/3400/ph_3400_52567.jpg",
				"https://dpqe0zkrjo0ak.cloudfront.net/pfil/3400/ph_3400_52569.jpg"};

		String[] pics5 = {"https://dpqe0zkrjo0ak.cloudfront.net/pfil/8959/ph_8959_30337.jpg",
				"https://dpqe0zkrjo0ak.cloudfront.net/pfil/8959/ph_8959_38113.jpg",
				"https://dpqe0zkrjo0ak.cloudfront.net/pfil/8959/ph_8959_30336.jpg",
				"https://dpqe0zkrjo0ak.cloudfront.net/pfil/8959/ph_8959_30335.jpg"};

		String[] pics6 = {"https://dpqe0zkrjo0ak.cloudfront.net/pfil/10612/pict_original.jpg",
				"https://dpqe0zkrjo0ak.cloudfront.net/pfil/10612/ph_10612_52384.jpg",
				"https://dpqe0zkrjo0ak.cloudfront.net/pfil/10612/ph_10612_52385.jpg",
				"https://dpqe0zkrjo0ak.cloudfront.net/pfil/10612/ph_10612_52383.jpg"};

		
//		List<PictureUrl> picUrls1 = PictureUrl.savePictures( PictureUrl.getPicturesFromArray(pics1));
//		
//		List<PictureUrl> picUrls2 = PictureUrl.savePictures(PictureUrl.getPicturesFromArray(pics2));
//
//		List<PictureUrl> picUrls3 = PictureUrl.savePictures(PictureUrl.getPicturesFromArray(pics3));
//		
//		PictureUrl pic4 = new PictureUrl();
//		pic4.setUrl("https://dpqe0zkrjo0ak.cloudfront.net/pfil/16423/pict_grid7.jpg");
//		pic4.saveInBackground();
//		
//		List<PictureUrl> picUrls5 = PictureUrl.savePictures(PictureUrl.getPicturesFromArray(pics5));
//
//		List<PictureUrl> picUrls6 = PictureUrl.savePictures(PictureUrl.getPicturesFromArray(pics6));
	
		
		// create orgs
		Organization o1 = new Organization();
		o1.setOrgId(100);
		//o1.setChallengeId(100);
		o1.setName("the-school-food-fight-is-on");
		o1.setDescription("We create tools and resources to help schools transition to scratch cooking and providing fully-equipped kitchens with training, successful models and salad bars");
		o1.setAddress("Boulder, CO, United States");
		o1.setUrl("http://www.globalgiving.org/microprojects/the-school-food-fight-is-on/");
		
		o1.addAllUnique("picture_urls", Arrays.asList(pics1));
		o1.saveInBackground( new SaveCallback() {
			
			@Override
			public void done(ParseException paramParseException) {
				if(paramParseException == null){
					// no errors
				}else{
					Log.d("debug", "exception ", paramParseException);
				}
			}
		});
		

		
		Organization o2 = new Organization();
		o2.setOrgId(101);
		//o2.setChallengeId(101);
		o2.setName("Help 455 At-Risk Children in Mexico Stay in School");
		o2.setDescription("Provide early education, breakfast, child care and psychological services for 455 disadvantaged preschoolers in the Mexico City Metro Area whose working parents cannot afford these services otherwise.");
		o2.setAddress("mexico");
		o2.setUrl("http://www.globalgiving.org/projects/help-455-at-risk-children-in-mexico-stay-in-school/");
		o2.addAllUnique("picture_urls", Arrays.asList(pics2));
		o2.saveInBackground( new SaveCallback() {
			
			@Override
			public void done(ParseException paramParseException) {
				if(paramParseException != null){
					Log.d("debug", "exception ", paramParseException);
				}
			}
		});
		
	
		
		
		Organization o3 = new Organization();
		o3.setOrgId(102);
		//o3.setChallengeId(102);
		o3.setName("St. Jude Children's Research Hospital");
		o3.setDescription("Finding Cures. Saving Children. St. Jude Children's Research Hospital is leading the way the world understands, treats and defeats childhood cancer and other deadly diseases.");
		o3.setAddress("Memphis, TN, United States");
		o3.setUrl("http://www.globalgiving.org/projects/help-455-at-risk-children-in-mexico-stay-in-school/");
		o3.addAllUnique("picture_urls", Arrays.asList(pics3));
		o3.saveInBackground( new SaveCallback() {
			
			@Override
			public void done(ParseException paramParseException) {
				if( paramParseException != null){
					Log.d("debug", "exception ", paramParseException);
				}
			}
		});
		
		

		
		Organization o4 = new Organization();
		o4.setOrgId(103);
		//o4.setChallengeId(103);
		o4.setName("Help a deserving student in NYC earn a degree");
		o4.setDescription("New York is a gateway city for immigrants to America. St. George's Society's Scholarship Program assists talented students of recent British and Commonwealth background at Lehman College, part of The City University of New York, to earn their degrees, start careers and become integrated in New York and the US.");
		o4.setAddress("New York, NY, United States ");
		// https://dpqe0zkrjo0ak.cloudfront.net/pfil/16423/pict_grid7.jpg
		o4.setUrl("http://www.globalgiving.org/projects/scholarships-in-nyc/");
		o4.addUnique("picture_urls", "https://dpqe0zkrjo0ak.cloudfront.net/pfil/16423/pict_grid7.jpg");
		o4.saveInBackground(new SaveCallback() {
			
			@Override
			public void done(ParseException paramParseException) {
				if( paramParseException != null ){
					Log.d("debug", "exception ", paramParseException);
				}
			}
		});
		

		
		
		
		Organization o5 = new Organization();
		o5.setOrgId(104);
		//o5.setChallengeId(104);
		o5.setName("Literacy Programs for Children");
		o5.setDescription("New York is a gateway city for immigrants to America. St. George's Society's Scholarship Program assists talented students of recent British and Commonwealth background at Lehman College, part of The City University of New York, to earn their degrees, start careers and become integrated in New York and the US.");
		o5.setAddress("New York, NY, United States ");
		o5.setUrl("http://www.globalgiving.org/projects/scholarships-in-nyc/");
		o5.addAllUnique("picture_urls", Arrays.asList(pics5));
		o5.saveInBackground(new SaveCallback() {
			
			@Override
			public void done(ParseException paramParseException) {
				if( paramParseException != null ){
					Log.d("debug", "exception ", paramParseException);
				}
			}
		});
		

		
		Organization o6 = new Organization();
		o6.setOrgId(105);
		//o6.setChallengeId(105);
		o6.setName("Save the Lives of Shelter Pets in North America");
		o6.setDescription("Your donation will help more than 13,700 shelters and rescue groups in the U.S., Canada and Mexico improve the lives of homeless pets and help them get adopted. ");
		o6.setAddress("Tucson, AZ, United States");
		o6.setUrl("http://www.globalgiving.org/projects/save-the-lives-of-shelter-pets-in-north-america/");
		o6.addAllUnique("picture_urls", Arrays.asList(pics6));
		o6.saveInBackground(new SaveCallback() {
			
			@Override
			public void done(ParseException paramParseException) {
				if( paramParseException != null ){
					Log.d("debug", "exception ", paramParseException);
				}
			}
		});
		

		/*
		if( !o1.isDirty() ){
			o1.addPictures( picUrls1);
			o1.saveInBackground(new SaveCallback() {
				
				@Override
				public void done(ParseException paramParseException) {
					Log.d("debug", "save exception ", paramParseException);	
				}
			});
		}

		if( !o2.isDirty()){
			o2.addPictures(picUrls2);
			o2.saveInBackground(new SaveCallback() {
				
				@Override
				public void done(ParseException paramParseException) {
					Log.d("debug", "save exception ", paramParseException);	
				}
			});
		}

		if( !o3.isDirty() ){
			o3.addPictures( picUrls3);
			o3.saveInBackground(new SaveCallback() {
				
				@Override
				public void done(ParseException paramParseException) {
					Log.d("debug", "save exception ", paramParseException);	
				}
			});
		}

		if( !o4.isDirty()){
			o4.addPicture(pic4);
			o4.saveInBackground(new SaveCallback() {
				
				@Override
				public void done(ParseException paramParseException) {
					Log.d("debug", "save exception ", paramParseException);	
				}
			});
		}

		if( !o5.isDirty()){
			o5.addPictures(picUrls5);
			o5.saveInBackground(new SaveCallback() {
				
				@Override
				public void done(ParseException paramParseException) {
					Log.d("debug", "save exception ", paramParseException);	
				}
			});
		}
		
		if( !o6.isDirty()){
			o6.addPictures(picUrls6);
			o6.saveInBackground(new SaveCallback() {
				
				@Override
				public void done(ParseException paramParseException) {
					Log.d("debug", "save exception ", paramParseException);	
				}
			});
		}
		*/

	}
}
