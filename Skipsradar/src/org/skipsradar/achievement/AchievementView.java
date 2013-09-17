package org.skipsradar.achievement;

import org.mixare.R;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AchievementView extends ListActivity {
		
	private static AchievementAdapter adapter;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		adapter = new AchievementAdapter();
		setListAdapter(adapter);

	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	
	private class AchievementAdapter extends BaseAdapter{
		
		TextView item;
		private LayoutInflater mInflater;

		public AchievementAdapter() {
			mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 1;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return item;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 1;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			if (convertView==null) {
				convertView = mInflater.inflate(R.layout.achievementlist, null);
				item = (TextView) convertView.findViewById(R.id.list_text);
				convertView.setTag(item);
			}
			else{
				item = (TextView) convertView.getTag();
			}
			
			item.setText("lolzilue");

			return convertView;
		}
		
	}

}
