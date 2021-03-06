package org.codepath.team10.charitychallenger.utils;

import java.text.SimpleDateFormat;
import java.util.Locale;

import android.text.format.DateUtils;
import android.util.Log;

public class FancyTimeUtil {
	// getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
	public static String getRelativeTimeAgo(String rawJsonDate) {
		String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
		SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
		sf.setLenient(true);
	 
		String relativeDate = "";
		try {
			long dateMillis = sf.parse(rawJsonDate).getTime();
			relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
					System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int index = relativeDate.indexOf(' ');
		String duration = null;
		if(index != -1)
			duration = relativeDate.substring(0, index);
		else{
			Log.d("getRelativeTimeAgo", "unexpected time: " + relativeDate);
			return relativeDate; 
		}
		if(relativeDate.contains("seconds") || relativeDate.contains("second")){
			relativeDate = duration + "s";
		} else if(relativeDate.contains("minutes") || relativeDate.contains("minute")){
			relativeDate = duration + "m";
		} else if(relativeDate.contains("hour") || relativeDate.contains("hours")) {
			relativeDate = duration + "h";
		} else if(relativeDate.contains("day") || relativeDate.contains("days")) {
			relativeDate = duration + "d";
		}else if(relativeDate.contains("year") || relativeDate.contains("years")) {
			relativeDate = duration + "y";
		}
		
		return relativeDate;
	}
}