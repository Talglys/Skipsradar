package org.skipsradar.achievement;

import java.util.ArrayList;
import org.mixare.R;
import android.content.Context;

public class AchievementStorage {

	private static AchievementStorage instance;
	private Context ctx;
	
	private AchievementStorage(Context ctx){
		this.ctx = ctx;
	}
	
	public static void initialize(Context ctx){
		if(instance == null){
			instance = new AchievementStorage(ctx);
		}
	}
	
	public static AchievementStorage getInstance(){
		return instance;
	}

	public ArrayList<Achievement> getAchievements(){
		ArrayList<Achievement> achi = new ArrayList<Achievement>();
		
		String[] achiString = ctx.getResources().getStringArray(R.array.defaultachievements);
		for(int i = 0; i < achiString.length; i++){
			String[] tempAchi = achiString[i].split("\\|", -1);
			if(tempAchi.length > 3){
				if(tempAchi[3].equals("nrProg")){
					//TODO Do stuff here
				}
				//TODO Do stuff here too
			}
		}
		
		return achi;
	}
	
}
