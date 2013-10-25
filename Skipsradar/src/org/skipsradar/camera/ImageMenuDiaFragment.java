package org.skipsradar.camera;

import org.mixare.MixView;
import org.mixare.R;
import org.skipsradar.ClickBundle;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

public class ImageMenuDiaFragment extends DialogFragment{

	public static final String IMAGE_ID = "image_id";

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		int id = getArguments().getInt(IMAGE_ID);
		final Photo photo = ((PhotoView) getActivity()).getPhoto(id);
		String[] options;
		String desc;
		boolean hasNoInfo = photo.getShipMmsi().equals(Photo.NO_INFO_MMSI); 
		if(hasNoInfo){
			options = new String[]{getResources().getString(R.string.image_dia_menu_show), 
					getResources().getString(R.string.image_dia_menu_delete)};
			desc = getResources().getString(R.string.image_dia_noupload);
		}
		else{
			options = new String[]{getResources().getString(R.string.image_dia_menu_show), 
					getResources().getString(R.string.image_dia_menu_delete), 
					getResources().getString(R.string.image_dia_menu_upload)};
			desc = photo.getShipName();
		}
		
		builder.setTitle(desc)
		//.setMessage(desc)
    	.setItems(options, new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int which) {
    			// The 'which' argument contains the index position
    			// of the selected item
    			switch (which) {
				case 0:
					//TODO provide better/larger bitmap
					showImageDialog(photo.getName(), getActivity());
					break;

				default:
					break;
				}
    		}
		
    	});
		
		return builder.create();
	}
	
public void showImageDialog(String name, Activity context){

    	// Get screen size
    	Display display = context.getWindowManager().getDefaultDisplay();
    	Point size = new Point();
    	display.getSize(size);
    	int screenWidth = size.x;
    	int screenHeight = size.y;

    	// Get target image size
    	Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + 
    			"/" + CameraStorage.imageFolder + "/" + name);
    	int bitmapHeight = bitmap.getHeight();
    	int bitmapWidth = bitmap.getWidth();

    	// Scale the image down to fit perfectly into the screen
    	// The value (250 in this case) must be adjusted for phone/tables displays
    	while(bitmapHeight > (screenWidth - 10) || bitmapWidth > (screenHeight - 10)) {
    	    bitmapHeight = (int) (bitmapHeight * 0.95);
    	    bitmapWidth = (int) (bitmapWidth * 0.95);
    	}

    	System.out.println("Debug: bitmapX:" + bitmapWidth +  " compared to: " + screenHeight);
    	System.out.println("Debug: bitmapY:" + bitmapHeight +  " compared to: " + screenWidth);
    	// Create resized and rotated bitmap image
    	Matrix matrix = new Matrix();
    	matrix.postRotate(90);
    	Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap , bitmapWidth, bitmapHeight, true);
    	Bitmap rotatedBitmap = Bitmap.createBitmap(scaledBitmap , 0, 0, scaledBitmap .getWidth(), scaledBitmap .getHeight(), matrix, true);
    	
    	System.out.println("Debug: rotated: " + rotatedBitmap.getWidth() + " x " + rotatedBitmap.getHeight());
    	
    	// Create dialog
    	Dialog dialog = new Dialog(context);
    	dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    	dialog.setContentView(R.layout.imagedialoglayout);

    	ImageView image = (ImageView) dialog.findViewById(R.id.thumbnail_IMAGEVIEW);

    	// !!! Do here setBackground() instead of setImageDrawable() !!! //
    	image.setBackground(new BitmapDrawable(getResources(), rotatedBitmap));

    	// Without this line there is a very small border around the image (1px)
    	// In my opinion it looks much better without it, so the choice is up to you.
    	dialog.getWindow().setBackgroundDrawable(null);
    	
    	// Show the dialog
    	dialog.show();
    	
    	/*
    	AlertDialog.Builder imageDialog = new AlertDialog.Builder(context);
    	LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    	View layout = inflater.inflate(R.layout.imagedialoglayout, null);
    	ImageView image = (ImageView) layout.findViewById(R.id.thumbnail_IMAGEVIEW);
    	image.setImageDrawable(bitmap);
    	imageDialog.setView(layout);
    	imageDialog.create();
    	imageDialog.show();
    	*/
    }
	
}
