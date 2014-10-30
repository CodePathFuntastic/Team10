package org.codepath.team10.charitychallenger.adapters;

import java.util.List;

import org.codepath.team10.charitychallenger.R;
import org.codepath.team10.charitychallenger.models.Challenge;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.parse.ParseQueryAdapter;

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

        // ...
}

public class ChallengesViewAdapter extends ParseQueryAdapter<Challenge> {

	public static class ViewHolder{
		ImageView ivCharityChallenge;
		TextView tvChallengeName;
		TextView tvTargetAmount;
		TextView tvRaised;
		ProgressBar challengeProgressBar;
		ProgressBar progressBarLoadImage;
	}
	public ChallengesViewAdapter(Context context, final String tableName) {
		super(context, tableName);
	}

	// Customize the layout by overriding getItemView
	@Override
	public View getItemView(Challenge challenge, View v, ViewGroup parent) {
		
		ViewHolder viewHolder = null;
		if (v == null) {
			viewHolder = new ViewHolder();
			v = View.inflate(getContext(), R.layout.item_challenge_new, null);
			viewHolder.ivCharityChallenge = (ImageView)v.findViewById(R.id.ivCharityChallenge);
			viewHolder.tvChallengeName = (TextView) v.findViewById(R.id.tvChallengeName);
			viewHolder.tvTargetAmount = (TextView) v.findViewById(R.id.tvTargetAmount);
			viewHolder.tvRaised = (TextView) v.findViewById(R.id.tvRaised);
			viewHolder.challengeProgressBar = (ProgressBar) v.findViewById(R.id.challengeProgressBar);
			viewHolder.progressBarLoadImage = (ProgressBar) v.findViewById(R.id.progressBarLoadImage);
			
			v.setTag(viewHolder);
			
		} else {
			viewHolder = (ViewHolder)v.getTag();
		}

		super.getItemView(challenge, v, parent);

		if(viewHolder.tvChallengeName != null){
			viewHolder.tvChallengeName.setText(challenge.getString("name"));
		}
		if(viewHolder.tvTargetAmount != null){
			viewHolder.tvTargetAmount.setText("/ $" + challenge.getInt("target"));
		}
		
		if(viewHolder.tvRaised != null){
			viewHolder.tvRaised.setText("$" + challenge.getInt("raised") + " Raised");
		}
		
		if(viewHolder.ivCharityChallenge != null){
			List<String> url = challenge.getList("challenge_pic_urls");
			display(viewHolder.ivCharityChallenge, url.get(0), viewHolder.progressBarLoadImage);
			
		}
		
		if(viewHolder.challengeProgressBar != null){
			int progress = (int)((double)challenge.getInt("raised")/(double)challenge.getInt("target") * 100);
			viewHolder.challengeProgressBar.setProgress(progress);
		}
		return v;
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
	         int width=0;
	         if( view.getWidth() == 0){
	        	 width = view.getMeasuredWidth();
	         }else{
	        	 width = view.getWidth();
	         }
	         if( width == 0){
	        	 width = loadedImage.getWidth();
	         }
	         
	         int height=0;
	         if( view.getHeight() == 0){
	        	 height = view.getMeasuredHeight();
	         }else{
	        	 height = view.getHeight();
	         }
	         if( height == 0 ){
	        	 height = loadedImage.getHeight();
	         }
	         BitmapScaler.scaleToFitHeight(loadedImage, height);
	         BitmapScaler.scaleToFitWidth(loadedImage, width);
	        }
	        @Override
	        public void onLoadingCancelled(String imageUri, View view) {

	        }
	});
	}
}
