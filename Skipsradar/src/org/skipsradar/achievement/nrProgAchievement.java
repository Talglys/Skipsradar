package org.skipsradar.achievement;

/**
 * This is an achievement of the type; the user has done 
 * a thing x out of y times, and must do that thing y times
 * or more to get the achievment completed.
 * An example is that the user has to click on 10 unique ships.
 * @author Andreas
 *
 */
public class nrProgAchievement extends Achievement {

	/**
	 * The intended target that is to be
	 * reached in order to complete the
	 * Achievement.
	 */
	private int target;
	/**
	 * The current progress in completing
	 * the Achievement, should never be
	 * higher than target.
	 */
	private int status;
	
	public nrProgAchievement(String ID, String name, String desc,
			String complete, String target, String status) {
		super(ID, name, desc, complete);
		this.target = Integer.parseInt(target);
		this.status = Integer.parseInt(status);
	}
	
	/**
	 * Will progress status by one, and if
	 * target is reached, will set the 
	 * Achievement to completed.
	 */
	public void progress(){
		if(!this.getCompleted()){
			status++;
			if(status >= target){
				this.setCompleted();
			}
		}
	}
	
	/**
	 * Will progress status by the number
	 * provided in progression. If target
	 * is reached, will set the Achievement
	 * to completed.
	 * 
	 * @param progression
	 */
	public void progress(int progression){
		if(!this.getCompleted()){
			status += progression;
			if(status >= target){
				this.setCompleted();
			}
		}
	}
	
	public int getTarget(){
		return target;
	}
	
	public int getStatus(){
		return status;
	}

}
