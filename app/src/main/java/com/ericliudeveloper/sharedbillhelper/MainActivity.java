package com.ericliudeveloper.sharedbillhelper;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.ericliudeveloper.sharedbillhelper.database.DatabaseConstants;
import com.ericliudeveloper.sharedbillhelper.provider.BillContract;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Uri billUri = BillContract.Bills.CONTENT_URI;
        ContentValues values = new ContentValues();
        values.put(DatabaseConstants.BillColumns.COL_TYPE, "electricity");
        values.put(DatabaseConstants.BillColumns.COL_AMOUNT, "340");
        values.put(DatabaseConstants.BillColumns.COL_BILLING_START,  "2005-01-01");
        values.put(DatabaseConstants.BillColumns.COL_BILLING_END,  "2005-06-01");
        values.put(DatabaseConstants.BillColumns.COL_DUE_DATE,  "2005-08-01");
        values.put(DatabaseConstants.BillColumns.COL_PAID, "0");
        values.put(DatabaseConstants.BillColumns.COL_DELETED, "0");

        Uri rowUri = getContentResolver().insert(billUri, values);
        Log.d("eric", rowUri.toString());

        String[] projectionBill = {DatabaseConstants.BillColumns.COL_ROWID
                , DatabaseConstants.BillColumns.COL_TYPE
                , DatabaseConstants.BillColumns.COL_AMOUNT};

        Cursor billsCursor = getContentResolver().query(billUri, projectionBill, null, null, null);

        int rowCount =  billsCursor.getCount();
        Log.d("eric", "Activity returned row number: " + rowCount);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
