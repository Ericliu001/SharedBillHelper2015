package com.ericliudeveloper.sharedbillhelper.model;

import android.content.AsyncQueryHandler;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;

import com.ericliudeveloper.sharedbillhelper.database.DatabaseConstants;
import com.ericliudeveloper.sharedbillhelper.provider.BillContract;

/**
 * Created by liu on 16/06/15.
 */
public class PaymentInfoDAO implements Dao {

    static String[] projection = BillContract.PaymentInfos.PROJECTION;
    static Uri paymentInfosUri = BillContract.PaymentInfos.CONTENT_URI;


    public static ContentValues getContentValuesFromPaymentInfoInstance(PaymentInfo paymentInfo) {
        if (paymentInfo == null) {
            return null;
        }

        ContentValues values = new ContentValues();
        values.put(DatabaseConstants.PaymentInfoColumns.COL_SERIAL_NUMBER, paymentInfo.getSerialNumber());
        values.put(DatabaseConstants.PaymentInfoColumns.COL_NAME, paymentInfo.getName());
        values.put(DatabaseConstants.PaymentInfoColumns.COL_DESCRIPTION, paymentInfo.getDescription());
        values.put(DatabaseConstants.PaymentInfoColumns.COL_TOTAL_AMOUNT, paymentInfo.getTotalAmount());
        values.put(DatabaseConstants.PaymentInfoColumns.COL_NUMBER_OF_MEMBERS_PAID, paymentInfo.getNumberOfMembersPaid());
        values.put(DatabaseConstants.PaymentInfoColumns.COL_NUMBER_OF_BILLS_PAID, paymentInfo.getNumberOfBillsPaid());
        values.put(DatabaseConstants.PaymentInfoColumns.COL_PAID_TIME, paymentInfo.getPaidTime());
        values.put(DatabaseConstants.PaymentInfoColumns.COL_DELETED, paymentInfo.getDeleted());

        return values;
    }


    public static void savePaymentInfo(PaymentInfo paymentInfo, final Handler handler) {
        final ContentValues values = getContentValuesFromPaymentInfoInstance(paymentInfo);

        AsyncQueryHandler insertPaymentInfoHandler = new AsyncQueryHandler(mContentResolver) {
            @Override
            protected void onInsertComplete(int token, Object cookie, Uri uri) {
                if (handler != null) {
                    Message message = Message.obtain();
                    message.what = MESSAGE_WHAT_SAVED_PAYMENTINFO_URL;
                    message.obj = uri;
                    handler.sendMessage(message);
                }
            }
        };

        insertPaymentInfoHandler.startInsert(0, null, paymentInfosUri, values);
    }
}
