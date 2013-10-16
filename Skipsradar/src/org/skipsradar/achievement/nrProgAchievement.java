package org.skipsradar.achievement;

import java.text.DecimalFormat;

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
	public boolean progress(){
		if(!this.getCompleted()){
			status++;
			if(status >= target){
				this.setCompleted();
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Will progress status by the number
	 * provided in progression. If target
	 * is reached, will set the Achievement
	 * to completed. It returns true if
	 * the achievement was completed during
	 * the progression. False otherwise.
	 * 
	 * @param progression
	 */
	public boolean progress(int progression){
		if(!this.getCompleted()){
			status += progression;
			if(status >= target){
				this.setCompleted();
				return true;
			}
		}
		return false;
	}
	
	public int getTarget(){
		return target;
	}
	
	public int getStatus(){
		return status;
	}
	
	@Override
	public String getCompletionStatus() {
		if(this.getCompleted()){
			return "true|" + status;
		}
		else{
			return "false|" + status;
		}
	}
	
	@Override
	public double getPercentage() {
		double percentage = (double)status/(double)target;
		percentage *= 100;
		return percentage;
	}
	
	@Override
	public String getDetails() {
		String message = getDescription();
		message += "\nDu har " + status + "/" + target;
		message += "\n" + (new DecimalFormat("#.#")).format(getPercentage()) + "% fullført.";
		return message;
	}

}
