package org.skipsradar.camera;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.skipsradar.achievement.Achievement;
import org.skipsradar.achievement.AchievementView;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

/**
 * This singleton class handles all storage and retrieval
 * of photos.
 * @author Andreas
 *
 */
public class CameraStorage {

	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int MEDIA_TYPE_VIDEO = 2;
	public static final String imageFolder = "Skipsradar"; //The name of the folder
	public static final String LAST_SAVED_IMAGE = "last_saved_image";
	private static CameraStorage instance; //Singleton instance
	
	Context ctx; //The context for storage, which is used to get SharedPreferences
	SharedPreferences settings;
	
	private CameraStorage(Context ctx) {
		this.ctx = ctx;
		settings = ctx.getSharedPreferences(AchievementView.SHARED_PREFS, 0);
	}
	
	public static CameraStorage getInstance(){
		return instance;
	}
	
	/**
	 * Initializer. Call once in before
	 * any other calls to this class.
	 * @param ctx
	 */
	public static void initialize(Context ctx){
		instance = new CameraStorage(ctx);
	}

	/**
	 * Gets, from the designated folder, all jpg pictures, and their corresponding
	 * information.
	 * @return An arraylist of Photo elements.
	 */
	public ArrayList<Photo> getPhotos(){
		File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + imageFolder);
		File[] filesInFolder = folder.listFiles();
		ArrayList<Photo> photos = new ArrayList<Photo>();
		if(filesInFolder != null){ //If the folder is not empty
			for (int i = 0; i < filesInFolder.length; i++) {
				// Check to see if any files are directories
				if(!filesInFolder[i].isDirectory()){
					String[] extList = filesInFolder[i].getName().split("\\.");
					String ext = extList[extList.length-1];
					//Check to see if they're jpg images
					if(ext.equals("jpg")){
						//Form of image info:
						//Name|mmsi|ShipName
						//Retrieves information on this image.
						String imgInfoString = settings.getString(
								filesInFolder[i].getName(), "Null");
						//If no info on the image is saved on the phone
						if(imgInfoString.equals("Null")){
							photos.add(new Photo(
									decodeSampledBitmapFromResource(filesInFolder[i].getPath(), 90, 90), 
									filesInFolder[i].getName(), Photo.NO_INFO_MMSI, "Unknown", ""));
						}
						//If info IS saved on the phone
						else{
							String[] imgInfo = imgInfoString.split("\\|", -1);
							photos.add(new Photo(
									decodeSampledBitmapFromResource(filesInFolder[i].getPath(), 90, 90), 
									imgInfo[0], imgInfo[1], imgInfo[2], ""));
						}
					}
				}
				
			}
		}
		
		return photos;
	}
	
	/**
	 * Gets the picture with the provided name from the folder, 
	 * and scales it down so it's maximum height or width is
	 * 800 px.
	 * @param name
	 * @return
	 */
	public Bitmap getLargeImage(String name){
		return decodeSampledBitmapFromResource(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + imageFolder + "/" + name,
				800, 800);
	}
	
	/**
	 * Deletes the image with the provided name from the folder.
	 * @param name
	 */
	public static void deleteImage(String name){
		File file = new File(Environment.getExternalStoragePublicDirectory(
				Environment.DIRECTORY_PICTURES) + "/" + imageFolder + "/" + name);
		file.delete();
	}
	
	/**
	 * Stores the name of the photo most recently
	 * taken, to use with storing information on the
	 * photo
	 * @param name
	 */
	private void storeLastImageName(String name){
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(LAST_SAVED_IMAGE, name);
		editor.commit();
	}
	
	/**
	 * Stores the information (name and mmsi) contained in the provided photo
	 * to the last photo taken.
	 * @param photo
	 */
	public void storePhotoInfo(Photo photo){
		String lsi = settings.getString(LAST_SAVED_IMAGE, "Null");
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(lsi, lsi + "|" + photo.getShipMmsi() + "|" + photo.getShipName());
		editor.commit();
	}
	
	/** Create a file Uri for saving an image or video */
	public Uri getOutputMediaFileUri(int type){
	      return Uri.fromFile(getOutputMediaFile(type));
	}

	/** Create a File for saving an image or video */
	public File getOutputMediaFile(int type){
	    // To be safe, you should check that the SDCard is mounted
	    // using Environment.getExternalStorageState() before doing this.
	    File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
	              Environment.DIRECTORY_PICTURES), imageFolder);
	    // This location works best if you want the created images to be shared
	    // between applications and persist after your app has been uninstalled.

	    // Create the storage directory if it does not exist
	    if (! mediaStorageDir.exists()){
	        if (! mediaStorageDir.mkdirs()){
	            Log.d("Skipsradar", "failed to create directory");
	            return null;
	        }
	    }

	    // Create a media file name
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    String imgName = "IMG_"+ timeStamp + ".jpg";
	    storeLastImageName(imgName);
	    File mediaFile;
	    if (type == MEDIA_TYPE_IMAGE){
	        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
	        imgName);
	    } else if(type == MEDIA_TYPE_VIDEO) {
	        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
	        "VID_"+ timeStamp + ".mp4");
	    } else {
	        return null;
	    }

	    return mediaFile;
	}
	
	/**
	 * Calculates how much to scale the image down, depending
	 * on the provided dimensions.
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
	    // Raw height and width of image
	    final int height = options.outHeight;
	    final int width = options.outWidth;
	    int inSampleSize = 1;
	
	    if (height > reqHeight || width > reqWidth) {
	
	        // Calculate ratios of height and width to requested height and width
	        final int heightRatio = Math.round((float) height / (float) reqHeight);
	        final int widthRatio = Math.round((float) width / (float) reqWidth);
	
	        // Choose the smallest ratio as inSampleSize value, this will guarantee
	        // a final image with both dimensions larger than or equal to the
	        // requested height and width.
	        inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
	    }
	
	    return inSampleSize;
	}
	
	/**
	 * Loads in a bitmap from the provided url
	 * with the dimensions reqWidth x reqHeight
	 * @param res
	 * @param resId
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static Bitmap decodeSampledBitmapFromResource(String pathName,
	        int reqWidth, int reqHeight) {

	    // First decode with inJustDecodeBounds=true to check dimensions
	    final BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inJustDecodeBounds = true;
	    BitmapFactory.decodeFile(pathName, options);

	    // Calculate inSampleSize
	    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

	    // Decode bitmap with inSampleSize set
	    options.inJustDecodeBounds = false;
	    return BitmapFactory.decodeFile(pathName, options);
	}
}
