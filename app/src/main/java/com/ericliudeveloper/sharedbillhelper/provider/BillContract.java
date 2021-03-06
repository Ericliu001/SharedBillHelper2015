package com.ericliudeveloper.sharedbillhelper.provider;

import android.net.Uri;

import com.ericliudeveloper.sharedbillhelper.database.DatabaseConstants;

/**
 * Created by liu on 5/06/15.
 */
public class BillContract {


    public static final String CONTENT_AUTHORITY = "com.ericliu.billshare.provdier";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);


    public static final String PATH_BILLS = "bills";
    public static final String PATH_MEMBERS = "memebers";
    public static final String PATH_PAYMENTS = "payments";
    public static final String PATH_PAYMENT_INFOS = "paymentInfos";

    public static class Bills implements DatabaseConstants.BillColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_BILLS).build();

        public static Uri buildBillUri(String billId) {
            return CONTENT_URI.buildUpon().appendPath(billId).build();
        }


        public static String[] PROJECTION = {
                COL_ROWID
                , COL_TYPE
                , COL_AMOUNT
                , COL_BILLING_START
                , COL_BILLING_END
                , COL_DUE_DATE
                , COL_PAID
                , COL_DELETED
        };
    }


    public static class Members implements DatabaseConstants.MemberColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MEMBERS).build();

        public static Uri buildMemberUri(String memberId) {
            return CONTENT_URI.buildUpon().appendPath(memberId).build();
        }

        public static String[] PROJECTION = {
                COL_ROWID
                , COL_FIRSTNAME
                , COL_LASTNAME
                , COL_PHONE
                , COL_EMAIL
                , COL_MOVE_IN_DATE
                , COL_MOVE_OUT_DATE
                , COL_CURRENT_MEMBER
                , COL_DELETED
        };
    }

    public static class Payments implements DatabaseConstants.PaymentColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_PAYMENTS).build();


        public static Uri buildPaymentUri(String paymentId) {
            return CONTENT_URI.buildUpon().appendPath(paymentId).build();
        }

        public static final String[] PROJECTION = {
                COL_ROWID
                , COL_PAYMENT_INFO_SERIAL_NUMBER
                ,COL_BILL_ID
                ,COL_PAYEE_ID
                ,COL_PAYEE_DAYS
                ,COL_PAYEE_START_DATE
                ,COL_PAYEE_END_DATE
                ,COL_PAYEE_AMOUNT
        };

    }


    public static class PaymentInfos implements DatabaseConstants.PaymentInfoColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_PAYMENT_INFOS).build();


        public static Uri buildPaymentInfoUri(String paymentInfoId) {
            return CONTENT_URI.buildUpon().appendPath(paymentInfoId).build();
        }

        public static final String[] PROJECTION = {
                COL_ROWID
                ,COL_SERIAL_NUMBER
                ,COL_NAME
                ,COL_DESCRIPTION
                ,COL_TOTAL_AMOUNT
                ,COL_NUMBER_OF_MEMBERS_PAID
                ,COL_NUMBER_OF_BILLS_PAID
                ,COL_PAID_TIME
                ,COL_DELETED
        };
    }


}
