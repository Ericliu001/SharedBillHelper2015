package com.ericliudeveloper.sharedbillhelper.database;
import android.database.sqlite.SQLiteDatabase;

import static com.ericliudeveloper.sharedbillhelper.database.DatabaseConstants.PaymentColumns;


public class TablePayment implements PaymentColumns, DatabaseConstants.TableAndView{



	
	// table creation SQL statement
	private static final String TABLE_CREATE = " create table "
			+ TABLE_PAYMENT
			+ "("
			+ COL_ROWID + " integer primary key autoincrement, "
			+ COL_PAYMENT_INFO_ID + "  integer not null , " 
			+ COL_BILL_ID +  " integer not null references " + TABLE_BILL + "(" + COL_ROWID+"),"
			+ COL_PAYEE_ID + " integer not null references " + TABLE_MEMBER + "("+ COL_ROWID+"),"
			+ COL_PAYEE_DAYS + " integer, "
			+ COL_PAYEE_START_DATE + " datetime, "
			+ COL_PAYEE_END_DATE + " datetime, "
			+ COL_PAYEE_AMOUNT + " numeric "
			+ " ); "
			;


	public static void onCreate(SQLiteDatabase db){
		db.execSQL(TABLE_CREATE);
	}
	
	public static void onUpgrade(SQLiteDatabase db){
		db.execSQL(" DROP TABLE IF EXISTS " + TABLE_PAYMENT);
		onCreate(db);
	}

}
