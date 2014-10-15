package org.codepath.team10.charitychallenger.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName(value="User")
public class User extends ParseObject {

	public User(){
	}
	
	public void setId( int id){
		put("id", id);
	}
	public int getId(){
		return getInt("id");
	}
	public void setName( String name){
		put("name", name);
	}
	public String getName(){
		return getString("name");
	}
}
