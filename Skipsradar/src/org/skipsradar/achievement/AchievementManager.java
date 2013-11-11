package org.skipsradar.achievement;

import java.util.ArrayList;

import org.mixare.R;

import android.content.Context;
import android.widget.Toast;

/**
 * This singleton class will keep all achievements during runtime
 * to update them once something noteworthy happens
 * @author Andreas
 *
 */
public class AchievementManager {

	private static AchievementManager instance;
	private ArrayList<Achievement> achievements;
	private ArrayList<String> mmsi;
	private Context ctx;
	
	private AchievementManager(Context ctx){
		achievements = AchievementStorage.getInstance().getAchievements();
		mmsi = AchievementStorage.getInstance().getMmsi();
		this.ctx = ctx;
	}
	
	public static void initialize(Context ctx){
		instance = new AchievementManager(ctx);
	}
	
	public static AchievementManager getInstance(){
		return instance;
	}
	
	/**
	 * Forces an update of the achievement list by getting
	 * the stored values.
	 */
	public void updateAchievements(){
		achievements = AchievementStorage.getInstance().getAchievements();
	}
	
	/**
	 * Forces an update of the mmsi list by getting the
	 * stored values.
	 */
	public void updateMmsi(){
		mmsi = AchievementStorage.getInstance().getMmsi();
	}
	
	/**
	 * This method will handle all updating of achievements
	 * having anything to do with seeing ships.
	 * The input is the mmsi of the ship seen.
	 * @param ship
	 */
	public void shipEvent(String ship){
		updateMmsi();
		if(!mmsi.contains(ship)){ //identifies if the ship is already seen
			AchievementStorage.getInstance().storeMmsi(ship);
			updateAchievements();
			for (int i = 0; i < achievements.size(); i++) {
				boolean completed = false;
				Achievement achi = achievements.get(i);
				if(achi instanceof nrProgAchievement){
					completed = ((nrProgAchievement) achi).progress();
				}
				if(achi instanceof specShipAchievement){
					completed = ((specShipAchievement) achi).progress(ship);
				}
				//TODO handle special case achievements
				
				/*
				 * If the achievement was completed, show a short
				 * toast message to let the user know.
				 */
				if(completed){
					String text = ctx.getResources().getString(R.string.achievement_complete) + "\n" + achi.getName();
					Toast toast = Toast.makeText(ctx, text, Toast.LENGTH_LONG); 
					toast.show();
				}
			}
			AchievementStorage.getInstance().storeAchievements(achievements);
		}
	}
	
}
