package org.codepath.team10.charitychallenger.models;

import java.util.ArrayList;
import java.util.List;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseRelation;

@ParseClassName(value="Organization")
public class Organization extends ParseObject{

	/**
	 * <ol>
	 * 	<li>Name:String</li>
	 *  <li>Description:String</li>
	 *  
	 *  Should the list of photos be stored in this object?
	 *  <li>orgPhotos:List<String></li>
	 *  <li>challengePhotos:List<String></li>
	 * </ol>
	 */
	
	public Organization(){
	}
	
	public void setName(String name){
		put("name", name);
	}
	public String getName(){
		return getString("name");
	}
	public void setDescription(String desc){
		put("description", desc);
	}
	public String getDescription(){
		return getString("description");
	}
	public void setOrgId( int id){
		put("org_id", id);
	}
	public int getOrgId(){
		return getInt("org_id");
	}
	public void setAddress(String address){
		put("address", address);
	}
	public String getAddress(){
		return getString("address");
	}
	public void setUrl(String url){
		put("url", url);
	}
	public String getUrl(){
		return getString("url");
	}
	
	public void addPicture( PictureUrl pic){
		ParseRelation<PictureUrl> relation =  getRelation("PictureObjects");
		relation.add(pic);
	}
	public void addPictures( List<PictureUrl> urls){
		ParseRelation<PictureUrl> relation =  getRelation("PictureObjects");
		for( PictureUrl url : urls){
			relation.add(url);
		}
	}
	public List<PictureUrl> getPictures(){
		
		List<PictureUrl> urls = null;
		ParseRelation<PictureUrl> relation =  getRelation("PictureObjects");
		try {
			urls = relation.getQuery().find();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return urls;
	}
	
	public List<String> getPictureUrls(){
		List<PictureUrl> pics = getPictures();
		List<String> urls = new ArrayList<String>();
		for( PictureUrl u : pics ){
			urls.add(u.getUrl());
		}
		return urls;
	}
	
}
