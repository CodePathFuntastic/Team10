package org.codepath.team10.charitychallenger.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName(value="Organization")
public class Organization extends ParseObject{

	/**
	 * <ol>
	 * 	<li>Name:String</li>
	 *  <li>Description:String</li>
	 *
	 *  <li>challengeId:int</li>
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
	public void setChallengeId( int id){
		put("challenge_id", id);
	}
	public int getChallengeId(){
		return getInt("challenge_id");
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
}
