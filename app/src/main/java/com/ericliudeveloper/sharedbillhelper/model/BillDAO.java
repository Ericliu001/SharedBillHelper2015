package com.ericliudeveloper.sharedbillhelper.model;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;

import com.ericliudeveloper.sharedbillhelper.MyApplication;
import com.ericliudeveloper.sharedbillhelper.database.DatabaseConstants;
import com.ericliudeveloper.sharedbillhelper.provider.BillContract;

/**
 * Created by liu on 7/06/15.
 */
public class BillDAO implements Dao {

    static ContentResolver mContentResolver = MyApplication.getAppContentResolver();
    static String[] projection = BillContract.Bills.PROJECTION;
    static Uri billsUri = BillContract.Bills.CONTENT_URI;

    public BillDAO() {
    }

    /**
     * retrieve a Bill Object from DB and return it by using Handler to send a Message
     *
     * @param id
     * @param handler
     */
    public static void getBill(long id, final Handler handler) {
        Uri uri = BillContract.Bills.buildBillUri(String.valueOf(id));

//        Cursor cursor = mContentResolver.query(uri, projection, null, null, null);

        new AsyncQueryHandler(mContentResolver) {
            @Override
            protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
                if (handler != null && cursor != null) {

                    Bill bill = getBillFromCursor(cursor);
                    Message message = Message.obtain();
                    message.what = MESSAGE_WHAT_GET_BILL;
                    message.obj = bill;
                    handler.sendMessage(message);
                }
            }
        }.startQuery(0, null, uri, projection, null, null, null);
    }


    /**
     * Create a Bill instance from a cursor, assumed the cursor is retrieved from DB thus id field is not null.
     *
     * @param cursor
     * @return
     */
    public static Bill getBillFromCursor(Cursor cursor) {
        if (cursor != null) {
            long billId = cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseConstants.BillColumns.COL_ROWID));
            Bill bill = new Bill(billId);
            bill.setType(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.BillColumns.COL_TYPE)));
            bill.setAmount(cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseConstants.BillColumns.COL_AMOUNT)));
            bill.setStartDate(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.BillColumns.COL_BILLING_START)));
            bill.setEndDate(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.BillColumns.COL_BILLING_END)));
            bill.setDueDate(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.BillColumns.COL_DUE_DATE)));
            bill.setPaid(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseConstants.BillColumns.COL_PAID)));

            return bill;
        }
        return null;
    }

    public static void saveBill(final Bill bill, final Handler handler) {
        long id = bill.getId();
        final ContentValues values = getContentValuesFromBillInstance(bill);

        if (id >= 0) { // this is an update
            final Uri billUriWithId = BillContract.Bills.buildBillUri(String.valueOf(id));

            new AsyncQueryHandler(mContentResolver) {
            }.startUpdate(0, null, billUriWithId, values, null, null);
            return;
        }


        AsyncQueryHandler insertBillHandler = new AsyncQueryHandler(mContentResolver) {
            @Override
            protected void onInsertComplete(int token, Object cookie, Uri uri) {
                if (handler != null) {

                    Message message = Message.obtain();
                    message.what = MESSAGE_WHAT_SAVED_BILL_URL;
                    message.obj = uri;
                    handler.sendMessage(message);
                }
            }
        };

        insertBillHandler.startInsert(0, null, billsUri, values);


    }


    /**
     * Create ContentValues from a Bill Instance, the id field is ignored.
     *
     * @param bill
     * @return
     */
    public static ContentValues getContentValuesFromBillInstance(Bill bill) {
        if (bill == null) {
            return null;
        }
        ContentValues values = new ContentValues();
        values.put(DatabaseConstants.BillColumns.COL_TYPE, bill.getType());
        values.put(DatabaseConstants.BillColumns.COL_AMOUNT, bill.getAmount());
        values.put(DatabaseConstants.BillColumns.COL_BILLING_START, bill.getStartDate());
        values.put(DatabaseConstants.BillColumns.COL_BILLING_END, bill.getEndDate());
        values.put(DatabaseConstants.BillColumns.COL_DUE_DATE, bill.getDueDate());
        values.put(DatabaseConstants.BillColumns.COL_PAID, bill.getPaid());
        values.put(DatabaseConstants.BillColumns.COL_DELETED, bill.getDeleted());
        return values;
    }

}
