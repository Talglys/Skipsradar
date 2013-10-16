package org.skipsradar.achievement;

/**
 * This type of achievement is seeing specific
 * ships, for example seeing ship 1 and ship 2
 * can give an achievement.
 * @author Andreas
 *
 */
public class specShipAchievement extends Achievement{

	/**
	 * The list of ships, and if they've
	 * been seen or not. It should be in
	 * the format {{ship1, false}, {ship2, true}}
	 */
	private String[] shipList;
	private String[] nameList;
	private boolean[] statusList;
	
	public specShipAchievement(String ID, String name, String desc,
			String complete, String[] shipList, String[] nameList,  
			boolean[] statusList) {
		super(ID, name, desc, complete);
		
		this.shipList = shipList;
		this.nameList = nameList;
		this.statusList = statusList;
	}
	
	/**
	 * Takes in an mmsi and checks if it matches
	 * any of the ships in the shipList. If it does,
	 * it sets the status of that ship to true. Returns
	 * whether or not this achievement was completed this
	 * round.
	 * @param mmsi
	 * @return
	 */
	public boolean progress(String mmsi){
		if(!this.getCompleted()){
			boolean complete = true;
			for (int i = 0; i < shipList.length; i++) {
				if(!statusList[i]){
					if(shipList[i].equals(mmsi)){
						statusList[i] = true;
					}
					else{
						complete = false;
					}
				}
			}
			if(complete){
				setCompleted();
				return true;
			}
		}
		return false;
	}
	
	public String[] getShipList(){
		return shipList;
	}
	
	public String[] getNameList(){
		return nameList;
	}
	
	public boolean[] getStatusList(){
		return statusList;
	}
	
	@Override
	public String getCompletionStatus(){
		String string = "" + this.getCompleted();
		for (int i = 0; i < statusList.length; i++) {
			string += "|" + statusList[i];
		}
		return string;
	}
	
	@Override
	public double getPercentage() {
		double percentage = 0;
		for (int i = 0; i < statusList.length; i++) {
			if(statusList[i]){
				percentage++;
			}
		}
		percentage /= statusList.length;
		percentage *= 100;
		return percentage;
	}
	
	@Override
	public String getDetails() {
		String message = super.getDetails();
		message += "\n";
		for (int i = 0; i < statusList.length; i++) {
			message += "\n" + nameList[i];
			if(statusList[i]){
				message +=  " - sett";
			}
		}
		return message;
	}

}
