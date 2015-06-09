package com.ericliudeveloper.sharedbillhelper.ui.dialog;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;

import com.ericliudeveloper.sharedbillhelper.util.CustomEvents;

import de.greenrobot.event.EventBus;

public class DateWrongDialog extends DialogFragment implements OnClickListener {
	public static final String TITLE = "title";
	public static final String MESSAGE = "message";

	public static DateWrongDialog newInstance(Bundle args){
		 DateWrongDialog dateWrongFrag = new DateWrongDialog();
		 dateWrongFrag.setArguments(args);
		 return dateWrongFrag;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Bundle args = getArguments();
		String title = args.getString(TITLE);
		String message = args.getString(MESSAGE);

		AlertDialog.Builder builder = new Builder(getActivity());

		builder.setTitle(title).setMessage(message);
		builder.setPositiveButton(android.R.string.ok, this);
		return builder.create();
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		EventBus.getDefault().post(new CustomEvents.EventWrongDatePicked());
		this.dismiss();
	}

}
