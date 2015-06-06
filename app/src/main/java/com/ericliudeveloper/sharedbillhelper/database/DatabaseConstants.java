package com.ericliudeveloper.sharedbillhelper.database;

import android.provider.BaseColumns;

public final class DatabaseConstants {
    private DatabaseConstants() {
    }

    public interface TableAndView {

        String TABLE_BILL = "table_bill";
        String TABLE_PAYMENT = "table_payment";
        String TABLE_PAYMENT_INFO = "table_payment_info";
        String TABLE_MEMBER = "table_member";


        String VIEW_PAYMENT_INFO = "view_payment_info";
        String VIEW_MEMBER = "view_member";
        String VIEW_MEMBER_NAME = "view_member_name";
        String VIEW_BILL = "view_bill";
        String VIEW_BILL_NAME_CREATE = "view_bill_name_create";
        String VIEW_BILL_NAME = "view_bill_name";
        String VIEW_PAYMENT_FULL = "view_payment_full";
    }


    public interface CommonColumns extends BaseColumns {
        String COL_ROWID = _ID;
        String COL_DELETED = "deleted";
        String COL_DESCRIPTION = "description";
        String COL_AMOUNT = "amount";
        String COL_CHECKED = "checked";

    }


    // constants for table bill


    public interface BillColumns extends CommonColumns {

        String COL_BILL_NAME = "bill_name";
        String COL_TYPE = "bill_type";
        String COL_BILLING_START = "billing_period_start_date";
        String COL_BILLING_END = "billing_period_end_date";
        String COL_DUE_DATE = "bill_due_date";
        String COL_PAID = "paid";
    }


    public interface PaymentColumns extends CommonColumns {
        String COL_PAYMENT_INFO_ID = "info_id";
        String COL_BILL_ID = "bill_id";
        String COL_PAYEE_ID = "payee_id";
        String COL_PAYEE_DAYS = "payee_days";
        String COL_PAYEE_START_DATE = "payee_start_days";
        String COL_PAYEE_END_DATE = "payee_end_days";
        String COL_PAYEE_AMOUNT = "payee_amount";
    }


    public interface PaymentInfoColumns extends CommonColumns {

        public static final String COL_SERIAL_NUMBER = "serial_number";
        public static final String COL_NAME = "payment_name";
        public static final String COL_PAID_TIME = "paid_time";
        public static final String COL_TOTAL_AMOUNT = "total_amount";
        public static final String COL_NUMBER_OF_MEMBERS_PAID = "number_of_members_paid";
        public static final String COL_NUMBER_OF_BILLS_PAID = "number_of_bills_paid";
    }


    public interface MemberColumns extends CommonColumns {

        public static final String COL_MEMBER_FULLNAME = "full_name";
        public static final String COL_FIRSTNAME = "firstname";
        public static final String COL_LASTNAME = "lastname";
        public static final String COL_PHONE = "telephone";
        public static final String COL_EMAIL = "email";
        public static final String COL_MOVE_IN_DATE = "move_in_date";
        public static final String COL_MOVE_OUT_DATE = "move_out_date";
        public static final String COL_CURRENT_MEMBER = "current_member";
    }


}
