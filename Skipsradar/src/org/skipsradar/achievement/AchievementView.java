package org.skipsradar.achievement;

import java.util.ArrayList;

import org.mixare.R;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class AchievementView extends ListActivity {
	
	public static final String SHARED_PREFS = "AchievementPrefs";
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
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		
		AchievementStorage.getInstance().storeAchievements(adapter.getAchievementSet());
	}
	
	private class AchievementAdapter extends BaseAdapter{
		
		TextView item; //TODO: Remove this, when safe
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
			
			holder.title.setText(achi.get(position).getName());
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

}
