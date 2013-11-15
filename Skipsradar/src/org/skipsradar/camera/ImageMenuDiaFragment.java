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

/**
 * Dialog with options that appears when an image is clicked in the
 * gallery.
 * @author Andreas
 *
 */
public class ImageMenuDiaFragment extends DialogFragment{

	public static final String IMAGE_ID = "image_id";

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		int id = getArguments().getInt(IMAGE_ID);
		final Photo photo = ((PhotoView) getActivity()).getPhoto(id);
		String[] options;
		String desc;
		final boolean hasNoInfo = photo.getShipMmsi().equals(Photo.NO_INFO_MMSI);
		final Activity context = getActivity();
		//If the image has no information attached, the user should not be able
		//to upload it.
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
				case 0: //Show image
					//TODO provide better/larger bitmap
					showImageDialog(photo.getName(), getActivity());
					break;
				case 1:
					/*
					 * Here the image is deleted, after a comfirmation
					 * dialog appears.
					 */
					new AlertDialog.Builder(getActivity())
					.setIcon(android.R.drawable.ic_dialog_alert)
					.setTitle(R.string.image_dia_menu_delete)
					.setMessage(R.string.image_dia_delete_alert)
					.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

			            @Override
			            public void onClick(DialogInterface dialog, int which) {
			
			            	CameraStorage.deleteImage(photo.getName());
			            	context.recreate();
			            }

			        })
			        .setNegativeButton(R.string.no, null)
			        .show();
					break;
				//TODO: Add case 2: for uploading an image
				default:
					break;
				}
    		}
		
    	});
		
		return builder.create();
	}

/**
 * Shows an image as a dialog, without any borders.
 * Taken from
 * http://stackoverflow.com/questions/18110603/android-image-dialog-popup-same-size-as-image-and-no-border
 * @param name
 * @param context
 */
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
    	// The value (50 in this case) must be adjusted for phone/tables displays
    	while(bitmapHeight > (screenHeight - 50) || bitmapWidth > (screenWidth - 50)) {
    	    bitmapHeight = (int) (bitmapHeight * 0.95);
    	    bitmapWidth = (int) (bitmapWidth * 0.95);
    	}
    	
    	BitmapDrawable resizedBitmap = new BitmapDrawable(context.getResources(), Bitmap.createScaledBitmap(bitmap, bitmapWidth, bitmapHeight, false));
    	
    	// Create resized and rotated bitmap image
    	/*If we wanted to rotate the image, which I tried with bad results
    	Matrix matrix = new Matrix();
    	matrix.postRotate(90);
    	Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap , bitmapWidth, bitmapHeight, true);
    	Bitmap rotatedBitmap = Bitmap.createBitmap(scaledBitmap , 0, 0, scaledBitmap .getWidth(), scaledBitmap .getHeight(), matrix, true);
    	*/
    	
    	// Create dialog
    	Dialog dialog = new Dialog(context);
    	dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    	dialog.setContentView(R.layout.imagedialoglayout);

    	ImageView image = (ImageView) dialog.findViewById(R.id.thumbnail_IMAGEVIEW);

    	// !!! Do here setBackground() instead of setImageDrawable() !!! //
    	//image.setBackground(new BitmapDrawable(getResources(), rotatedBitmap));
    	image.setBackground(resizedBitmap);
    	
    	// Without this line there is a very small border around the image (1px)
    	// In my opinion it looks much better without it, so the choice is up to you.
    	dialog.getWindow().setBackgroundDrawable(null);
    	
    	// Show the dialog
    	dialog.show();
    }
	
}
