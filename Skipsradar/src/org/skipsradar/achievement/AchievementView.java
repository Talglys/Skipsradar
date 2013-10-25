package org.skipsradar.achievement;

import java.io.FileOutputStream;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.mixare.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ListActivity;
import android.content.Context;
import android.net.NetworkInfo.DetailedState;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class AchievementView extends ListActivity {
	
	public static final String SHARED_PREFS = "AchievementPrefs";
	public static final int MENU_DETAILS_ID = Menu.FIRST;
	private static AchievementAdapter adapter;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

	}
	
	@Override
	protected void onResume() {
		super.onResume();
		adapter = new AchievementAdapter();
		adapter.setAchievements(AchievementStorage.getInstance().getAchievements());
		setListAdapter(adapter);
//		ListView lv = getListView();
//		registerForContextMenu(lv);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		
		AchievementStorage.getInstance().storeAchievements(adapter.getAchievementSet());
	}
	
	private class AchievementAdapter extends BaseAdapter{
		
		private LayoutInflater mInflater;
		private ArrayList<Achievement> achi;

		public AchievementAdapter() {
			mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			achi = new ArrayList<Achievement>();
		}
		
		/**
		 * Sets the current achievements to be the ones provided in the
		 * ArrayList
		 * @param achi
		 */
		public void setAchievements(ArrayList<Achievement> achi){
			this.achi = achi;
		}
		
		public ArrayList<Achievement> getAchievementSet(){
			return achi;
		}
		
		public void addAchievement(Achievement a){
			achi.add(a);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return achi.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return achi.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			
			if (convertView==null) {
				convertView = mInflater.inflate(R.layout.achievementlist, null);
				holder = new ViewHolder();
				holder.title = (TextView) convertView.findViewById(R.id.list_text);
				holder.desc = (TextView) convertView.findViewById(R.id.description_text);
				holder.status = (CheckBox) convertView.findViewById(R.id.list_checkbox);
				holder.status.setTag(position);
				holder.icon = (ImageView) convertView.findViewById(R.id.achievement_icon);

				convertView.setTag(holder);
			}
			else{
				holder = (ViewHolder) convertView.getTag();
			}
			
			String completion = completion(achi.get(position)) + " %";
			holder.title.setText(achi.get(position).getName() + " - " + completion);
			holder.desc.setText(achi.get(position).getDescription());
			holder.icon.setImageResource(R.drawable.wikipedia);
			holder.status.setChecked(achi.get(position).getCompleted());

			return convertView;
		}
		
		private class ViewHolder{
			TextView title;
			TextView desc;
			CheckBox status;
			ImageView icon;
		}
		
	}
	
	/**
	 * Takes an achievement and returns a string
	 * representing the percentage of completion
	 * for this achievement.
	 * 
	 * @param achi
	 * @return
	 */
	private String completion(Achievement achi){
		return new DecimalFormat("#.#").format(achi.getPercentage());
	}
	
	/*
	 * Shows a dialog with details about the achievement
	 * (non-Javadoc)
	 * @see android.app.ListActivity#onListItemClick(android.widget.ListView, android.view.View, int, long)
	 */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		AchiDetailDiaFragment dialog = new AchiDetailDiaFragment();
		Bundle args = new Bundle();
		args.putString(AchiDetailDiaFragment.DETAILS_STRING, ((Achievement)adapter.getItem((int)id)).getDetails());
		dialog.setArguments(args);
		dialog.show(getFragmentManager(), AchiDetailDiaFragment.DETAILS_STRING);
		super.onListItemClick(l, v, position, id);
	}
	
}
