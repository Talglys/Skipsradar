package org.skipsradar;

import org.mixare.MixView;
import org.mixare.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Lists the markers that are stacked, if the user happens
 * to press a stack of markers.
 * @author Andreas
 *
 */
public class MarkerListDiaFragment extends DialogFragment {
	
	public static final String MARKER_SEL_CHOICES = "marker_sel_choices";
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		//The ClickBundle is gotten from MixView, and contains important information
		//related to the selection of markers.
		ClickBundle bundle = ((MixView) getActivity()).getFragmentBundle();
		if(!(bundle == null)){
			String[] markerNames = new String[bundle.getMarkers().size()];
			for (int i = 0; i < markerNames.length; i++) {
				markerNames[i] = bundle.getMarkers().get(i).getTitle();
			}
			builder.setTitle(R.string.marker_sel_title)
	        	.setItems(markerNames, new DialogInterface.OnClickListener() {
	        		public void onClick(DialogInterface dialog, int which) {
	        			// The 'which' argument contains the index position
	        			// of the selected item
	        			ClickBundle bundle = ((MixView) getActivity()).getFragmentBundle();
	        			bundle.getMarkers().get(which).listClick(bundle.getCoords()[0], 
	        					bundle.getCoords()[1], bundle.getContext(), 
	        					bundle.getState());
	        			((MixView) getActivity()).ClearClickData();
	        		}
				
			});
		}
		//If somehow the ClickBundle in MixView is empty,
		//an error appears.
		else{
			builder.setTitle("Error")
				.setMessage(R.string.marker_sel_error)
				.setNeutralButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						//Do nothing, dialog exit
					}
				});
		}
		return builder.create();
	}

}
