package org.codepath.team10.charitychallenger.utils;

import org.json.JSONException;
import org.json.JSONObject;

public class FBUtils {

	public static String extractImageUrl( JSONObject json){
		
		String imgUrl=null;
		
		if( json == null ){
			throw new NullPointerException("input cannot be null");
		}
		
		if( !json.isNull("picture")){
			
			try {
				JSONObject pjson = json.getJSONObject("picture");
				if( !pjson.isNull("data") ){
					JSONObject djson = pjson.getJSONObject("data");
					if( !djson.isNull("url")){
						imgUrl = djson.getString("url");
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		return imgUrl;
	}
}
