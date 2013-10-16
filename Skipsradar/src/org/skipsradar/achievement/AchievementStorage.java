package org.skipsradar.achievement;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import org.mixare.R;

import android.content.Context;
import android.content.SharedPreferences;

public class AchievementStorage {

	private static final String mmsiStore = "mmsiStore";
	private static final int mmsiLength = 9;
	private SharedPreferences settings;
	private static AchievementStorage instance;
	private Context ctx;
	
	private AchievementStorage(Context ctx){
		this.ctx = ctx;
		settings = ctx.getSharedPreferences(AchievementView.SHARED_PREFS, 0);
	}
	
	public static void initialize(Context ctx){
		if(instance == null){
			instance = new AchievementStorage(ctx);
		}
	}
	
	public static AchievementStorage getInstance(){
		return instance;
	}

	/**
	 * Will search through all achievements and their status,
	 * and return them.
	 * @return Returns an ArrayList of achievements, including the
	 * special types of achievements.
	 */
	public ArrayList<Achievement> getAchievements(){
		ArrayList<Achievement> achi = new ArrayList<Achievement>();
		
		String[] achiString = ctx.getResources().getStringArray(R.array.defaultachievements);
		for(int i = 0; i < achiString.length; i++){
			/**
			 * The static, unchanging parts of the achievement, like ID,
			 * name and description.
			 */
			String[] tempAchi = achiString[i].split("\\|", -1);
			/**
			 * The changing part of the achievement, like whether it's
			 * complete or not.
			 */
			String achiProgressString = settings.getString(tempAchi[0], "Null");
			String[] achiProgress = achiProgressString.split("\\|", -1);
			
			if(tempAchi.length > 3){ //Checks if the achievement is of a special type
				/*
				 * Form of nrProg achievements:
				 * Unchanging: AchiXX|Name|Description|nrProg|Target
				 * Changing: Complete|Status
				 */
				if(tempAchi[3].equals("nrProg")){
					if(achiProgressString.equals("Null")){
						achi.add(new nrProgAchievement(tempAchi[0], tempAchi[1], 
								tempAchi[2], "false", tempAchi[4], "0"));
					}
					else{
						achi.add(new nrProgAchievement(tempAchi[0], tempAchi[1], 
								tempAchi[2], achiProgress[0], tempAchi[4], achiProgress[1]));
					}
				}
				/*
				 * Form of specShip achievements:
				 * Unchanging: AchiXX|Name|Description|specShip|Y|mmsi1|mmsi2|...|name1|name2|...
				 * Changing: Complete|Status1|Status2|...
				 */
				else if(tempAchi[3].equals("specShip")){
					int length = Integer.parseInt(tempAchi[4]);
					String[] shipList = new String[length];
					String[] nameList = new String[length];
					boolean[] statusList = new boolean[length];
					String complete = "false";
					for (int j = 0; j < length; j++) {
						shipList[j] = tempAchi[5+j];
						nameList[j] = tempAchi[5+length+j];
						if(achiProgressString.equals("Null")){
							statusList[j] = false;
						}
						else{
							statusList[j] = Boolean.parseBoolean(achiProgress[1+j]);
							complete = achiProgress[0];
						}
					}
					achi.add(new specShipAchievement(tempAchi[0], tempAchi[1], tempAchi[2], 
							complete, shipList, nameList, statusList));
					
				}
				//else if(){
				//TODO: More types of achievements
				///}
			}
			/*
			 * If the achievement is of the trival type,
			 * with very simple triggers
			 * Form of normal achievements:
			 * Unchanging: AchiXX|Name|Description
			 * Changing: Complete
			 */
			else{
				if(achiProgressString.equals("Null")){
					achi.add(new Achievement(tempAchi[0], tempAchi[1], tempAchi[2], 
							"false"));
				}
				else{
					achi.add(new Achievement(tempAchi[0], tempAchi[1], tempAchi[2], 
							achiProgressString));
				}
			}
		}
		
		return achi;
	}
	
	public void storeAchievements(ArrayList<Achievement> achi){
		SharedPreferences.Editor editor = settings.edit();
		for (Achievement achievement : achi) {
			editor.putString(achievement.getID(), achievement.getCompletionStatus());
		}
		editor.commit();
	}
	
	/**
	 * Stores the supplied mmsi in the internal storage.
	 * This method will not check to see if the supplied
	 * value already exists within the storage.
	 * @param mmsi
	 */
	public void storeMmsi(String mmsi){
		
		String mmsiString = settings.getString(mmsiStore, "Null");
		SharedPreferences.Editor editor = settings.edit();
		if(mmsiString.equals("Null")){
			editor.putString(mmsiStore, mmsi);
		}
		else{
			mmsiString += "|" + mmsi;
			editor.putString(mmsiStore, mmsiString);
		}
		editor.commit();
	}
	
	/**
	 * Reads the internal storage and returns
	 * an ArrayList of the currently seen mmsi
	 * values.
	 * @return
	 */
	public ArrayList<String> getMmsi(){
		ArrayList<String> mmsi = new ArrayList<String>();
		String mmsiString = settings.getString(mmsiStore, "Null");
		System.out.println("mmsi: " + mmsiString);
		if(!mmsiString.equals("Null")){
			String[] mmsiList = mmsiString.split("\\|", -1);
			for (String string : mmsiList) {
				mmsi.add(string);
			}
		}
		
		return mmsi;
	}
	
}
