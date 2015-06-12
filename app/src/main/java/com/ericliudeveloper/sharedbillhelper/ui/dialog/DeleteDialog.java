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

public class DeleteDialog extends DialogFragment implements OnClickListener {

    public static final String TITLE = "title";
    public static final String MESSAGE = "message";


    public static DeleteDialog newInstance(Bundle args) {
        DeleteDialog deleteDialog = new DeleteDialog();
        deleteDialog.setArguments(args);

        return deleteDialog;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();
        String title = args.getString(TITLE);
        String message = args.getString(MESSAGE);

        AlertDialog.Builder builder = new Builder(getActivity());

        builder.setTitle(title).setMessage(message);
        builder.setPositiveButton(android.R.string.ok, this).setNegativeButton(
                android.R.string.cancel, this);

        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case AlertDialog.BUTTON_POSITIVE:
                // todo handle yes to delete

                EventBus.getDefault().post(new CustomEvents.EventActionDelete());
                break;

            case AlertDialog.BUTTON_NEGATIVE:
                dismiss();
                break;
            default:
                break;
        }
    }

}
