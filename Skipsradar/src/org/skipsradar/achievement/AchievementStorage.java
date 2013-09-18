package org.skipsradar.achievement;

import java.util.ArrayList;
import org.mixare.R;
import android.content.Context;
import android.content.SharedPreferences;

public class AchievementStorage {

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
						String[] achiProgress = achiProgressString.split("\\|", -1);
						achi.add(new nrProgAchievement(tempAchi[0], tempAchi[1], 
								tempAchi[2], achiProgress[0], tempAchi[4], achiProgress[1]));
					}
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
	
}
