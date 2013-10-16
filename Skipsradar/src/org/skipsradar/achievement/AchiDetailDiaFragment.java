package org.skipsradar.achievement;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class AchiDetailDiaFragment extends DialogFragment {
	
	public static final String DETAILS_STRING = "details";
		
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		String message = getArguments().getString(DETAILS_STRING);
		builder.setMessage(message)
			   .setNeutralButton("OK", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					//Do nothing, dialog exit
				}
			
		});
		
		return builder.create();
	}
}
