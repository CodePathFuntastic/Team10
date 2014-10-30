package org.codepath.team10.charitychallenger.fragments;

import java.util.ArrayList;
import java.util.List;

import org.codepath.team10.charitychallenger.R;
import org.codepath.team10.charitychallenger.models.Challenge;
import org.codepath.team10.charitychallenger.models.Invitation;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.squareup.picasso.Picasso;

class BitmapScaler
{
	// Scale and maintain aspect ratio given a desired width
	// BitmapScaler.scaleToFitWidth(bitmap, 100);
	public static Bitmap scaleToFitWidth(Bitmap b, int width)
	{
		float factor = width / (float) b.getWidth();
		return Bitmap.createScaledBitmap(b, width, (int) (b.getHeight() * factor), true);
	}


	// Scale and maintain aspect ratio given a desired height
	// BitmapScaler.scaleToFitHeight(bitmap, 100);
	public static Bitmap scaleToFitHeight(Bitmap b, int height)
	{
		float factor = height / (float) b.getHeight();
		return Bitmap.createScaledBitmap(b, (int) (b.getWidth() * factor), height, true);
	}

}

public class NewInvitationFragment extends Fragment {
	
	private ImageView ivCharityChallenge;
	private TextView tvChallengeName;
	private TextView tvTargetAmount;
	private TextView tvRaised;
	private Challenge challenge;
	private Invitation invitation;
	ProgressBar challengeProgressBar;
	ProgressBar progressBarLoadImage;
	
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
		//ivCharityChallenge = (ImageView)view.findViewById(R.id.ivCharityChallenge);
		tvChallengeName = (TextView) view.findViewById(R.id.tvChallengeName);
		tvTargetAmount = (TextView) view.findViewById(R.id.tvTargetAmount);
		tvRaised = (TextView) view.findViewById(R.id.tvRaised);
		//challengeProgressBar = (ProgressBar) view.findViewById(R.id.challengeProgressBar);
		//progressBarLoadImage = (ProgressBar) view.findViewById(R.id.progressBarLoadImage1);
		if(tvChallengeName != null){
			tvChallengeName.setText(challenge.getString("name"));
		}
		if(tvTargetAmount != null){
			tvTargetAmount.setText("/ $" + challenge.getInt("target"));
		}
		
		if(tvRaised != null){
			tvRaised.setText("$" + challenge.getInt("raised") + " Raised");
		}
		
		/*
		if(ivCharityChallenge != null){
			List<String> url = challenge.getList("challenge_pic_urls");
			display(ivCharityChallenge, url.get(0), progressBarLoadImage);
			
		}*/
		
//		if(challengeProgressBar != null){
//			int progress = (int)((double)challenge.getInt("raised")/(double)challenge.getInt("target") * 100);
//			challengeProgressBar.setProgress(progress);
//		}
		
		ViewPager viewPager = (ViewPager) view.findViewById(R.id.view_pager);
		
		List<String> pictures = challenge.getChallengesPictureUrls();
	    ImagePagerAdapter adapter = new ImagePagerAdapter(pictures);
	    viewPager.setAdapter(adapter);
	    
	    
		return view;
	}
	
	public void display(ImageView img, String url, final ProgressBar spinner)
	{
		ImageLoader.getInstance().displayImage(url, img, new ImageLoadingListener(){
	
	        @Override
	        public void onLoadingStarted(String imageUri, View view) {
	         spinner.setVisibility(View.VISIBLE); // set the spinner visible
	        }
	        
	        @Override
	        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
	         spinner.setVisibility(View.GONE); // set the spinenr visibility to gone
	        }
	        
	        @Override
	        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
	         spinner.setVisibility(View.GONE); //  loading completed set the spinenr visibility to gone
	         BitmapScaler.scaleToFitHeight(loadedImage, 250);
	         BitmapScaler.scaleToFitWidth(loadedImage, 300);
	        }
	        
	        @Override
	        public void onLoadingCancelled(String imageUri, View view) {
	        	spinner.setVisibility(View.GONE); //  loading completed set the spinenr visibility to gone
	        }
	});
	}
	
	private  class ImagePagerAdapter extends PagerAdapter {
	    private List<String> imageUrls= new ArrayList<String>();

	    public ImagePagerAdapter( List<String> pics){
	    	for(String p : pics){
	    		if( p != null && !p.trim().equals("")){
	    			imageUrls.add(p);
	    		}
	    	}
	    	
	    }
	    @Override
	    public int getCount() {
	      return imageUrls.size();
	    }

	    @Override
	    public boolean isViewFromObject(View view, Object object) {
	      return view == ((ImageView) object);
	    }

	    @Override
	    public Object instantiateItem(ViewGroup container, int position) {
	      Context context = getActivity();
	      ImageView imageView = new ImageView(context);
	      int padding = context.getResources().getDimensionPixelSize(
	          R.dimen.padding_medium);
	      imageView.setPadding(padding, padding, padding, padding);
	      imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
	     
	      //imageView.setImageResource(mImages[position]);
	      String imageUrl = imageUrls.get(position);
	      
	      Picasso.with(context)
	      			.load(imageUrl)
					.error(R.drawable.ic_launcher)
					.into(imageView);
	      
	      ((ViewPager) container).addView(imageView, 0);
	      return imageView;
	    }

	    @Override
	    public void destroyItem(ViewGroup container, int position, Object object) {
	      ((ViewPager) container).removeView((ImageView) object);
	    }
	  }
}
