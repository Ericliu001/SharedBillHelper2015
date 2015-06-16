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
public class PaymentDAO implements Dao {

    static String[] projection = BillContract.Payments.PROJECTION;
    static Uri paymentsUri = BillContract.Payments.CONTENT_URI;


    public static ContentValues getContentValuesFromPaymentInstance(Payment payment) {
        if (payment == null) {
            return null;
        }
        ContentValues values = new ContentValues();
        values.put(DatabaseConstants.PaymentColumns.COL_PAYMENT_INFO_ID, payment.getPayment_info_serial_number());
        values.put(DatabaseConstants.PaymentColumns.COL_BILL_ID, payment.getBill_id());
        values.put(DatabaseConstants.PaymentColumns.COL_PAYEE_ID, payment.getPayee_id());
        values.put(DatabaseConstants.PaymentColumns.COL_PAYEE_DAYS, payment.getPayee_days());
        values.put(DatabaseConstants.PaymentColumns.COL_PAYEE_START_DATE, payment.getPayee_start_date());
        values.put(DatabaseConstants.PaymentColumns.COL_PAYEE_END_DATE, payment.getPayee_end_date());
        values.put(DatabaseConstants.PaymentColumns.COL_PAYEE_AMOUNT, payment.getPayee_amount());

        return values;
    }


    public static void savePayment(Payment payment, final Handler handler) {
        final ContentValues values = getContentValuesFromPaymentInstance(payment);

        AsyncQueryHandler insertPaymentHandler = new AsyncQueryHandler(mContentResolver) {
            @Override
            protected void onInsertComplete(int token, Object cookie, Uri uri) {
                if (handler != null) {
                    Message message = Message.obtain();
                    message.what = MESSAGE_WHAT_SAVED_PAYMENT_URL;
                    message.obj = uri;
                    handler.sendMessage(message);
                }
            }
        };

        insertPaymentHandler.startInsert(0, null, paymentsUri, values);
    }

}
