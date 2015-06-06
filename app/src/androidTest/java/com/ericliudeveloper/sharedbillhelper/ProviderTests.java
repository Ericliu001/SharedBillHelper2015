package com.ericliudeveloper.sharedbillhelper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.test.ProviderTestCase2;
import android.test.mock.MockContentResolver;
import android.util.Log;

import com.ericliudeveloper.sharedbillhelper.database.DatabaseConstants;
import com.ericliudeveloper.sharedbillhelper.provider.BillContract;
import com.ericliudeveloper.sharedbillhelper.provider.BillProvider;

/**
 * Created by liu on 5/06/15.
 */
public class ProviderTests extends ProviderTestCase2<BillProvider> {

    private MockContentResolver mResolver;
    // Contains an SQLite database, used as test data
    private SQLiteDatabase mDb;

    Uri rowUri1, rowUri2;
    Uri billUri = BillContract.Bills.CONTENT_URI;

    String[] projectionBill = {DatabaseConstants.BillColumns.COL_ROWID
            , DatabaseConstants.BillColumns.COL_TYPE
            , DatabaseConstants.BillColumns.COL_AMOUNT};


    /**
     * Constructor.
     *
     * @param providerClass     The class name of the provider under test
     * @param providerAuthority The provider's authority string
     */
    public ProviderTests() {
        super(BillProvider.class, BillContract.CONTENT_AUTHORITY);
    }


    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mResolver = getMockContentResolver();

    }


    public void testInsertBill() {
        assertNotNull("The Uri for bill is null.", billUri);

        ContentValues values1 = new ContentValues();
        values1.put(DatabaseConstants.BillColumns.COL_TYPE, "electricity");
        values1.put(DatabaseConstants.BillColumns.COL_AMOUNT, "340");
        values1.put(DatabaseConstants.BillColumns.COL_BILLING_START, "2005-01-01");
        values1.put(DatabaseConstants.BillColumns.COL_BILLING_END, "2005-06-01");
        values1.put(DatabaseConstants.BillColumns.COL_DUE_DATE, "2005-08-01");
        values1.put(DatabaseConstants.BillColumns.COL_PAID, "0");
        values1.put(DatabaseConstants.BillColumns.COL_DELETED, "0");

        rowUri1 = mResolver.insert(billUri, values1);
        Log.d("eric", rowUri1.toString());
        assertNotNull("The inserted row uri is null.", rowUri1);

        // insert more rows
        ContentValues values2 = new ContentValues();
        values2.put(DatabaseConstants.BillColumns.COL_TYPE, "water");
        values2.put(DatabaseConstants.BillColumns.COL_AMOUNT, "120");
        values2.put(DatabaseConstants.BillColumns.COL_BILLING_START, "2005-01-01");
        values2.put(DatabaseConstants.BillColumns.COL_BILLING_END, "2005-06-01");
        values2.put(DatabaseConstants.BillColumns.COL_DUE_DATE, "2005-08-01");
        values2.put(DatabaseConstants.BillColumns.COL_PAID, "1");
        values2.put(DatabaseConstants.BillColumns.COL_DELETED, "0");

        rowUri2 = mResolver.insert(billUri, values2);
        Log.d("eric", rowUri2.toString());
        assertNotNull("The inserted row uri is null.", rowUri2);

    }

    private void insertBills() {
        ContentValues values1 = new ContentValues();
        values1.put(DatabaseConstants.BillColumns.COL_TYPE, "electricity");
        values1.put(DatabaseConstants.BillColumns.COL_AMOUNT, "340");
        values1.put(DatabaseConstants.BillColumns.COL_BILLING_START, "2005-01-01");
        values1.put(DatabaseConstants.BillColumns.COL_BILLING_END, "2005-06-01");
        values1.put(DatabaseConstants.BillColumns.COL_DUE_DATE, "2005-08-01");
        values1.put(DatabaseConstants.BillColumns.COL_PAID, "0");
        values1.put(DatabaseConstants.BillColumns.COL_DELETED, "0");

        rowUri1 = mResolver.insert(billUri, values1);

        ContentValues values2 = new ContentValues();
        values2.put(DatabaseConstants.BillColumns.COL_TYPE, "water");
        values2.put(DatabaseConstants.BillColumns.COL_AMOUNT, "120");
        values2.put(DatabaseConstants.BillColumns.COL_BILLING_START, "2005-01-01");
        values2.put(DatabaseConstants.BillColumns.COL_BILLING_END, "2005-06-01");
        values2.put(DatabaseConstants.BillColumns.COL_DUE_DATE, "2005-08-01");
        values2.put(DatabaseConstants.BillColumns.COL_PAID, "1");
        values2.put(DatabaseConstants.BillColumns.COL_DELETED, "0");

        rowUri2 = mResolver.insert(billUri, values2);
    }


    public void testQueryOperations() {

        insertBills(); // very important!!!
        Cursor billsCursor = mResolver.query(billUri, projectionBill, null, null, null);

        int rowCount =  billsCursor.getCount();
        assertTrue("No results returned.", rowCount > 0);
        Log.d("eric", "returned row number: " + rowCount);
    }


    public void testUpdateOperations(){
        insertBills();

        ContentValues values = new ContentValues();
        values.put(DatabaseConstants.BillColumns.COL_TYPE, "internet");
        values.put(DatabaseConstants.BillColumns.COL_AMOUNT, "73.4");
        values.put(DatabaseConstants.BillColumns.COL_BILLING_START, "2005-01-01");
        values.put(DatabaseConstants.BillColumns.COL_BILLING_END, "2005-06-01");
        values.put(DatabaseConstants.BillColumns.COL_DUE_DATE, "2005-08-01");
        values.put(DatabaseConstants.BillColumns.COL_PAID, "0");
        values.put(DatabaseConstants.BillColumns.COL_DELETED, "0");
        int count = mResolver.update(rowUri1, values, null, null);
        assertTrue("Updated row is 0", count > 0);


        Cursor cursor = mResolver.query(rowUri1, projectionBill, null, null, null);
        String updatedType = "";
        String updatedAmount = "";
        if (cursor.moveToFirst()) {
            updatedType = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.BillColumns.COL_TYPE));
            updatedAmount = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.BillColumns.COL_AMOUNT));
        }

        assertEquals("Type is wrong", "internet", updatedType);
        assertEquals("Amount is wrong", "73.4", updatedAmount);
    }


}
