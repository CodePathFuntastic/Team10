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
	 *  <li>TargetAmount:int</li>
	 *  <li>AmountRaised:int</li>
	 *  
	 *  <li>openChallenges:int</li>
	 *  <li>completedChallenges:int</li>
	 *  <li>paidChallenges:int</li>
	 *  
	 *  <li>challenge_pictures:List</li>
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
	public void setOpenInvitation( int invitations){
		put("open_invitations", invitations);
	}
	public int getOpenInvitations(){
		return getInt("open_invitations");
	}
	public void setClosedInvitations(int invitations){
		put("closed_invitations", invitations);
	}
	public int getClosedInvitations(){
		return getInt("closed_invitations");
	}
	public void setPaidInvitations( int invitations){
		put("paid_invitations", invitations);
	}
	public int getPaidInvitations(){
		return getInt("paid_invitations");
	}
	
}
