package org.codepath.team10.charitychallenger.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName(value="PictureURL")
public class PictureUrl extends ParseObject {

	public PictureUrl(){
	}
	
	public void setUrl(String url){
		put("url", url);
	}
	public String getUrl(){
		return getString("url");
	}
	
	public static List<PictureUrl> getPicturesFromList( List<String> urls){
		List<PictureUrl> pics = new ArrayList<PictureUrl>();
		for( String url : urls){
			PictureUrl p = new PictureUrl();
			p.setUrl(url);
			pics.add(p);
		}
		return pics;
	}
	
	public static List<PictureUrl> getPicturesFromArray( String[] urls){
		return getPicturesFromList(Arrays.asList(urls));
	}
	
	public static List<PictureUrl> savePictures( List<PictureUrl> pics){
		for( PictureUrl p : pics ){
			p.saveInBackground();
		}
		
		return pics;
	}
}
