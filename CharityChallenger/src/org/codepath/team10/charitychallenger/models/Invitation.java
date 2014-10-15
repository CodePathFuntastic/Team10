package org.codepath.team10.charitychallenger.models;

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
	public void setSender(int user){
		put("sender", user);
	}
	public int getSender(){
		return getInt("sender");
	}
	public void setReceiver( int id){
		put("receiver", id);
	}
	public int getReceiver(){
		return getInt("receiver");
	}
	public void setStatus(int status){
		put("status", status);
	}
	public int getStatus(){
		return getInt("status");
	}
}
