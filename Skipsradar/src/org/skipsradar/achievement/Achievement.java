package org.skipsradar.achievement;

import java.text.DecimalFormat;

/**
 * This is the superclass of all other achievements,
 * and should not be used by itself unless the
 * Achievement is of the trivial type, where the user
 * only has to do one thing.
 * @author Andreas
 *
 */
public class Achievement {

	/**
	 * The Achievement's ID, should be in the form
	 * AchiXX, where the XX represents a number.
	 */
	protected String ID;
	/**
	 * The name of the Achievement
	 */
	private String name;
	/**
	 * The description of the Achievement,
	 * not to be confused with the
	 * progress of the Achievement, which
	 * will be implemented in subclasses.
	 */
	private String description;
	/**
	 * Whether the achievement is complete
	 * or not
	 */
	private boolean complete;
	
	public Achievement(String ID, String name, String desc, String complete){
		this.ID = ID;
		this.name = name;
		description = desc;
		if(complete.equals("true")){
			this.complete = true;
		}
		else{
			this.complete = false;
		}
	}
	
	public String getID(){
		return ID;
	}
	
	public String getName(){
		return name;
	}
	
	public String getDescription(){
		return description;
	}
	
	/**
	 * Sets the Achievement to completed,
	 * you cannot uncomplete an
	 * Achievement.
	 */
	public void setCompleted(){
		complete = true;
	}
	
	public boolean getCompleted(){
		return complete;
	}
	
	public String getCompletionStatus() {
		return ""+complete;
	}
	
	/**
	 * Returns the percentage of completion
	 * for this achievement.
	 * @return
	 */
	public double getPercentage(){
		if(complete){
			return 100;
		}
		else{
			return 0;
		}
	}
	
	/**
	 * Gets a string with details about the achievement
	 * for displaying in a dialog
	 * @return
	 */
	public String getDetails(){
		String message = description;
		message += "\n" + (new DecimalFormat("#.#")).format(getPercentage()) + "% fullført.";
		return message;
	}
}
