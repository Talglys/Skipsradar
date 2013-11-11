package org.skipsradar;

import org.mixare.MixView;
import org.mixare.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * The list that appears when the user takes a picture
 * with more than one marker on screen. This is to select
 * which ship should be used with the photo.
 * @author Andreas
 *
 */
public class CameraListDiaFragment extends DialogFragment {

public static final String CAMERA_SEL_CHOICES = "camera_sel_choices";
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		final String[] ships = getArguments().getStringArray(CAMERA_SEL_CHOICES);
		builder.setTitle(R.string.marker_sel_title)
        	.setItems(ships, new DialogInterface.OnClickListener() {
        		public void onClick(DialogInterface dialog, int which) {
        			// The 'which' argument contains the index position
        			// of the selected item
        			((MixView)getActivity()).storePhotoInfo(which);
        		}
			
		});
		return builder.create();
	}
}
