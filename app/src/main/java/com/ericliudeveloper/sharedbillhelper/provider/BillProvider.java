package com.ericliudeveloper.sharedbillhelper.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.ericliudeveloper.sharedbillhelper.database.BillDatabaseHelper;
import com.ericliudeveloper.sharedbillhelper.database.DatabaseConstants;

public class BillProvider extends ContentProvider implements DatabaseConstants.TableAndView, DatabaseConstants.CommonColumns {

    public static final String AUTH = BillContract.CONTENT_AUTHORITY;

//	public static final Uri BILL_URI = Uri.parse("content://" + AUTH + "/bill");
//	public static final Uri HOUSEMATE_URI = Uri.parse("content://" + AUTH
//			+ "/housemate");
//	public static final Uri PAYMENT_URI = Uri.parse("content://" + AUTH
//			+ "/payment");
//
//	public static final Uri PAYMENT_INFO_URI = Uri.parse("content://" + AUTH + "/paymentinfo");

    public static final Uri DIALOG_BILL_URI = Uri.parse("content://" + AUTH + "/dialogbill");
    public static final Uri DIALOG_MEMBER_URI = Uri.parse("content://" + AUTH + "/dialogmember");

    public static final Uri PAYMENT_FULL_DETAIL = Uri.parse("content://" + AUTH + "/paymentfulldetail");

    // Basic tables
    private static final int BILLS = 1;
    private static final int BILL_ID = 10;

    private static final int HOUSEMATES = 2;
    private static final int HOUSEMATE_ID = 20;

    private static final int PAYMENTS = 3;
    private static final int PAYMENT_ID = 30;

    private static final int PAYMENT_INFO = 4;
    private static final int PAYMENT_INFO_ID = 40;


    private static final int DIALOG_BILL = 5;
    private static final int DIALOG_MEMBER = 6;

    private static final int PAYMENT_FULL = 7;


    private static final UriMatcher URI_MATCHER;

    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        URI_MATCHER.addURI(AUTH, BillContract.PATH_BILLS, BILLS);
        URI_MATCHER.addURI(AUTH, BillContract.PATH_BILLS + "/#", BILL_ID);

        URI_MATCHER.addURI(AUTH, BillContract.PATH_MEMBERS, HOUSEMATES);
        URI_MATCHER.addURI(AUTH, BillContract.PATH_MEMBERS + "/#", HOUSEMATE_ID);

        URI_MATCHER.addURI(AUTH, BillContract.PATH_PAYMENTS, PAYMENTS);
        URI_MATCHER.addURI(AUTH, "payment/#", PAYMENT_ID);

        URI_MATCHER.addURI(AUTH, BillContract.PATH_PAYMENT_INFOS, PAYMENT_INFO);
        URI_MATCHER.addURI(AUTH, BillContract.PATH_PAYMENT_INFOS + "/#", PAYMENT_INFO_ID);


        URI_MATCHER.addURI(AUTH, "dialogbill", DIALOG_BILL);
        URI_MATCHER.addURI(AUTH, "dialogmember", DIALOG_MEMBER);

        URI_MATCHER.addURI(AUTH, "paymentfulldetail", PAYMENT_FULL);

    }

    private BillDatabaseHelper dbHelper;

    @Override
    public boolean onCreate() {
        dbHelper = new BillDatabaseHelper(getContext());
        return (dbHelper == null) ? false : true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        final SQLiteDatabase db = dbHelper.getReadableDatabase();
        int uriMatch = URI_MATCHER.match(uri);
        final SelectionBuilder sb = new SelectionBuilder();
        sb.where(selection, selectionArgs);

        switch (uriMatch) {
            case BILLS:

                sb.table(TABLE_BILL);
                break;

            case BILL_ID:

//			qb.setTables(TABLE_BILL);
//			qb.appendWhere(COL_ROWID + " = " + uri.getLastPathSegment() );

                sb.table(TABLE_BILL);
                sb.where(COL_ROWID + "=?", uri.getLastPathSegment());
                break;


            case HOUSEMATES:

//			qb.setTables(VIEW_MEMBER);
                sb.table(VIEW_MEMBER);
                break;

            case HOUSEMATE_ID:

//			qb.setTables(TABLE_MEMBER);
//			qb.appendWhere(COL_ROWID + " = " + uri.getLastPathSegment() );

                sb.table(TABLE_MEMBER).where(COL_ROWID + "=?", uri.getLastPathSegment());

                break;


            case PAYMENTS:

//			qb.setTables(TABLE_PAYMENT);

                sb.table(TABLE_PAYMENT);
                break;

            case PAYMENT_ID:

//			qb.setTables(TABLE_PAYMENT);
//			qb.appendWhere(COL_ROWID + " = " + uri.getLastPathSegment() );

                sb.table(TABLE_PAYMENT).where(COL_ROWID + "=?", uri.getLastPathSegment());
                break;

            case PAYMENT_INFO:
//			qb.setTables(VIEW_PAYMENT_INFO);

                sb.table(TABLE_PAYMENT);
                break;

            case PAYMENT_INFO_ID:
//			qb.setTables(TABLE_PAYMENT_INFO);
//			qb.appendWhere(COL_ROWID + "=" + uri.getLastPathSegment());

                sb.table(TABLE_PAYMENT_INFO).where(COL_ROWID + "=?", uri.getLastPathSegment());
                break;


            case DIALOG_BILL:
//			qb.setTables(VIEW_BILL_NAME);
                sb.table(VIEW_BILL_NAME);
                break;

            case DIALOG_MEMBER:
//			qb.setTables(VIEW_MEMBER_NAME);
                sb.table(VIEW_MEMBER_NAME);
                break;

            case PAYMENT_FULL:

//			qb.setTables(VIEW_PAYMENT_FULL);
                sb.table(VIEW_PAYMENT_FULL);
                break;


            default:
                throw new IllegalArgumentException(" Unknow URL " + uri);
        }

//		Cursor c = qb.query(dbHelper.getReadableDatabase(), projection,
//				selection, selectionArgs, null, null, sortOrder);
//		c.setNotificationUri(getContext().getContentResolver(), uri);

        return sb.query(db, projection, sortOrder);
    }

    @Override
    public String getType(Uri uri) {

        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long rowID = 0;
        Uri newUri;
        int uriMatch = URI_MATCHER.match(uri);

        switch (uriMatch) {
            case BILLS:
                rowID = db.insert(TABLE_BILL, null, values);
                newUri = ContentUris.withAppendedId(BillContract.Bills.CONTENT_URI, rowID);
                break;


            case HOUSEMATES:
                rowID = db.insert(TABLE_MEMBER, null, values);
                newUri = ContentUris.withAppendedId(BillContract.Members.CONTENT_URI, rowID);
                break;


            case PAYMENTS:
                rowID = db.insert(TABLE_PAYMENT, null, values);
                newUri = ContentUris.withAppendedId(BillContract.Payments.CONTENT_URI, rowID);
                break;


            case PAYMENT_INFO:
                rowID = db.insert(TABLE_PAYMENT_INFO, null, values);
                newUri = ContentUris.withAppendedId(BillContract.PaymentInfos.CONTENT_URI, rowID);
                break;

            default:
                throw new IllegalArgumentException(" Unknow URL " + uri);
        }


        getContext().getContentResolver().notifyChange(uri, null);
        return rowID > 0 ? newUri : null;

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        SelectionBuilder sb = new SelectionBuilder();


        getContext().getContentResolver().notifyChange(uri, null);

        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        SelectionBuilder sb = new SelectionBuilder();
        sb.where(selection, selectionArgs);


        String idString; // contains rowId
        int count;
        int uriMatch = URI_MATCHER.match(uri);

//		String where;
//		if (TextUtils.isEmpty(selection)) {
//			where = "";
//
//		}else {
//			where = " AND ( " + selection + " )";
//		}

        switch (uriMatch) {

            case BILL_ID:
                idString = uri.getLastPathSegment();

                count = sb.table(TABLE_BILL).where(COL_ROWID + "=?", idString).update(db, values);
                break;


            case HOUSEMATE_ID:
                idString = uri.getLastPathSegment();

                count = sb.table(TABLE_MEMBER).where(COL_ROWID + "=?", idString).update(db, values);
                break;


            case PAYMENT_ID:
                idString = uri.getLastPathSegment();

                count = sb.table(TABLE_PAYMENT).where(COL_ROWID + "=?", idString).update(db, values);
                break;


            case PAYMENT_INFO_ID:
                idString = uri.getLastPathSegment();

                count = sb.table(TABLE_PAYMENT_INFO).where(COL_ROWID + "=?", idString).update(db, values);
                break;

            case BILLS:
                throw new IllegalArgumentException(" Bulk update not supported " + uri);

            case HOUSEMATES:
                throw new IllegalArgumentException(" Bulk update not supported " + uri);

            case PAYMENTS:
                throw new IllegalArgumentException(" Bulk update not supported " + uri);

            case PAYMENT_INFO:
                throw new IllegalArgumentException(" Bulk update not supported " + uri);

            default:
                throw new IllegalArgumentException(" Unknow URL " + uri);
        }


        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

}
