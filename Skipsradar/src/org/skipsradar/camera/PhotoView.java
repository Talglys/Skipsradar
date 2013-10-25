package org.skipsradar.camera;

import java.util.ArrayList;

import org.mixare.R;
import org.skipsradar.achievement.AchiDetailDiaFragment;
import org.skipsradar.achievement.Achievement;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class PhotoView extends Activity{
	
	GridView gridview;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.photolayout);

	    gridview = (GridView) findViewById(R.id.gridview);
	    gridview.setAdapter(new ImageAdapter(this));

	    gridview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position,
					long id) {
				ImageMenuDiaFragment dialog = new ImageMenuDiaFragment();
				Bundle args = new Bundle();
				args.putInt(ImageMenuDiaFragment.IMAGE_ID, position);
				dialog.setArguments(args);
				dialog.show(getFragmentManager(), ImageMenuDiaFragment.IMAGE_ID);
				
			}
	    });
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	    ((ImageAdapter)gridview.getAdapter()).setPhotos(CameraStorage.getInstance().getPhotos());
	}
	
	public Photo getPhoto(int position){
		return (Photo) ((ImageAdapter)gridview.getAdapter()).getItem(position);
	}
	
	class ImageAdapter extends BaseAdapter{

		private Context ctx;
		ArrayList<Photo> photos;
		
		public ImageAdapter(Context ctx) {
			this.ctx = ctx;
			photos = new ArrayList<Photo>();
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return photos.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return photos.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}
		
		public void setPhotos(ArrayList<Photo> photos){
			this.photos = photos;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
	        ImageView imageView;
	        if (convertView == null) {  // if it's not recycled, initialize some attributes
	            imageView = new ImageView(ctx);
	            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
	            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
	            imageView.setPadding(8, 8, 8, 8);
	        } else {
	            imageView = (ImageView) convertView;
	        }
	        imageView.setImageBitmap(photos.get(position).getPhoto());
	        return imageView;
	    }
		
	}
}
