package org.codepath.team10.charitychallenger.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName(value="Organization")
public class Organization extends ParseObject{

	/**
	 * <ol>
	 * 	<li>Name:String</li>
	 *  <li>Description:String</li>
	 *  <li>TargetAmount:int</li>
	 *  <li>AmountRaised:int</li>
	 *  <li>challengeId:int</li>
	 *  <li>openChallenges:int</li>
	 *  <li>completedChallenges:int</li>
	 *  <li>paidChallenges:int</li>
	 *  
	 *  Should the list of photos be stored in this object?
	 *  <li>Photos:List<String></li>
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
	public void setTargetAmount(double target){
		put("target", target);
	}
	public double getTargetAmount(){
		return getDouble("target");
	}
	public void setAmountRaised(double raised){
		put("raised", raised);
	}
	public double getAmountRaised(){
		return getDouble("raised");
	}
	public void setChallengeId( int id){
		put("challenge_id", id);
	}
	public int getChallengeId(){
		return getInt("challenge_id");
	}
	public void setOpenChallenges( int challenges){
		put("open_challenges", challenges);
	}
	public int getOpenChallenges(){
		return getInt("open_challenges");
	}
	public void setClosedChallenges(int challenges){
		put("closed_challenges", challenges);
	}
	public int getClosedChallenges(){
		return getInt("closed_challenges");
	}
	public void setPaidChallenges( int challenges){
		put("paid_challenges", challenges);
	}
	public int getPaidChallenges(){
		return getInt("paid_challenges");
	}
}
