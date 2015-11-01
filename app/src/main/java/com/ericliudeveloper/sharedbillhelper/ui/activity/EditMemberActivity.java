package com.ericliudeveloper.sharedbillhelper.ui.activity;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import com.ericliudeveloper.sharedbillhelper.model.Member;
import com.ericliudeveloper.sharedbillhelper.ui.fragment.EditMemberFragment;
import com.ericliudeveloper.sharedbillhelper.util.CustomEvents;

import de.greenrobot.event.EventBus;

/**
 * Created by liu on 11/06/15.
 */
public class EditMemberActivity extends ContainerActivity {

    @Override
    protected Fragment getFragment() {
        return new EditMemberFragment();
    }

    @Override
    protected String getFragmentTag() {
        return getClass().getName();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Make sure the request was successful
        if (resultCode == RESULT_OK) {
            // Get the URI that points to the selected contact
            Uri contactUri = data.getData();
            // We only need the NUMBER column, because there will be only one row in the result
            String[] projection = {ContactsContract.Contacts.DISPLAY_NAME_PRIMARY, ContactsContract.CommonDataKinds.Phone.NUMBER};

            // Perform the query on the contact to get the NUMBER column
            // We don't need a selection or sort order (there's only one result for the given URI)
            // CAUTION: The query() method should be called from a separate thread to avoid blocking
            // your app's UI thread. (For simplicity of the sample, this code doesn't do that.)
            // Consider using CursorLoader to perform the query.
            Cursor cursor = getContentResolver()
                    .query(contactUri, projection, null, null, null);
            cursor.moveToFirst();


            int columnName = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY);
            // Retrieve the phone number from the NUMBER column
            int columnNumber = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

            String name = cursor.getString(columnName);
            String number = cursor.getString(columnNumber);

            Member member = new Member();
            member.setFirstName(name);
            member.setPhone(number);

            EventBus.getDefault().postSticky(new CustomEvents.EventActionContactChosen(member));

        }
    }

}
