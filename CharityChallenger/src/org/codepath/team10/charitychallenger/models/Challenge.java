package org.codepath.team10.charitychallenger.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName(value="Challenge")
public class Challenge extends ParseObject {

	/**
	 * Data needed for a challenge
	 * 
	 * <ol>
	 * 	<li>id:number</li>
	 *  <li>name:String</li>
	 *  <li>description:String</li>
	 *  <li>organization_id:number</li>
	 *  <li>charity_pictures:List</li>
	 * </ol>
	 */
	public Challenge(){
	}
	
	public void setId( int id){
		put("id", id);
	}
	public int getId(){
		return getInt("id");
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
	public void setOrganization( int orgId){
		put("orgId", orgId);
	}
	public int getOrganization(){
		return getInt("orgId");
	}
}
