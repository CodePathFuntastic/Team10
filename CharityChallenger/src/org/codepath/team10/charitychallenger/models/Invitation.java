package org.codepath.team10.charitychallenger.models;

import java.util.List;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName(value="Invitation")
public class Invitation extends ParseObject {

	/**
	 * <ol>
	 * 		<li>id:int</li>
	 * 		<li>challengeId:int</li>
	 * 		<li>senderId:id</li>
	 * 		<li>receiver:id</li>
	 * 		<li>status:int</li>
	 * 		<li>message:String</li>
	 * 		<li>amount:float</li>
	 * 		<li>photos</li>
	 * </ol>
	 */
	public Invitation(){
	}
	
	public void setChallengeId( int id){
		put("challengeId", id);
	}
	public int getChallengeId(){
		return getInt("challengeId");
	}
	public void setSender(String user){
		put("sender", user);
	}
	public String getSender(){
		return getString("sender");
	}
	public void setReceiver( String id){
		put("receiver", id);
	}
	public String getReceiver(){
		return getString("receiver");
	}
	public void setMessage(String msg){
		put("message", msg);
	}
	public String getMessage(){
		return getString("message");
	}
	public void setAmount(double amt){
		put("amount", amt);
	}
	public double getAmount(){
		return getDouble("amount");
	}
	public void setStatus(int status){
		put("status", status);
	}
	public int getStatus(){
		return getInt("status");
	}
	public void addPhoto(String photoUrl){
		addUnique("photos",photoUrl);
	}
	public List<String> getPhotos(){
		return getList("photos");
	}
}
