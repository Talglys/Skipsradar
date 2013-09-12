package org.mixare.data;

import java.util.ArrayList;
import java.util.List;

import org.mixare.R;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class TestList extends ListActivity{
	
	private static TestAdapter adapter;
	
	@Override
	protected void onResume() {
		super.onResume();
		adapter = new TestAdapter();
		setListAdapter(adapter);
	}
	
	private class TestAdapter extends BaseAdapter{
		
		String item;
		private LayoutInflater mInflater;

		public TestAdapter() {
			item = "lolzilue";
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
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			TextView tempView;
			
			if (convertView==null) {
				convertView = mInflater.inflate(R.layout.testlayout, null);
				tempView = (TextView) convertView.findViewById(R.id.list_text);
				convertView.setTag(tempView);
			}
			else{
				tempView = (TextView) convertView.getTag();
			}
			
			tempView.setText(item);
			
			return convertView;
		}
		
	}
}
