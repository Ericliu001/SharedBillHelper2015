package com.ericliudeveloper.sharedbillhelper.database;

import android.database.sqlite.SQLiteDatabase;

public class TableBill implements DatabaseConstants.BillColumns, DatabaseConstants.TableAndView{

	
	// table creation SQL statement
	private static final String TABLE_CREATE = " create table "
			+ TABLE_BILL
			+ "("
			+ COL_ROWID + " integer primary key autoincrement, "
			+ COL_TYPE + " text, "
			+ COL_AMOUNT +  " real not null, "
			+ COL_BILLING_START + " datetime default '1900-01-01', "
			+ COL_BILLING_END + " datetime  default '3000-01-01', "
			+ COL_DUE_DATE + " datetime,  "
			+ COL_PAID + " boolean default 0, "
			+ COL_DELETED + " boolean not null default 0 "
			+ " ); "
			;
	
	// create view
	private static final String VIEW_CREATE = "create view "
			+ VIEW_BILL
			+ " as select "
			+ COL_ROWID + " , "
			+ COL_TYPE + " , "
			+ COL_AMOUNT +  " , "
			+ COL_BILLING_START + " , "
			+ COL_BILLING_END + " , "
			+ COL_DUE_DATE + " ,  "
			+ COL_PAID 
			+ " from  "
			+ TABLE_BILL
			+ " where  " + COL_DELETED + " = 0"
			+";";
	
	// create view name
	private static final String VIEW_BILL_NAME_CREATE = " create view "
			+ VIEW_BILL_NAME
			+ " as select "
			+ COL_ROWID + " , "
			+ COL_PAID +  " , "
			+ COL_TYPE + "||' '||" + COL_AMOUNT  + " as  " + COL_BILL_NAME
			+ " from  "
			+ TABLE_BILL
			+ " where  " + COL_DELETED + " = 0"
			+ " and  " + COL_PAID + " = 0 "
			+ ";";
	

	public static void onCreate(SQLiteDatabase db){
		db.execSQL(TABLE_CREATE);
		db.execSQL(VIEW_CREATE);
		db.execSQL(VIEW_BILL_NAME_CREATE);
	}
	
	public static void onUpgrade(SQLiteDatabase db){
		db.execSQL(" DROP TABLE IF EXISTS " + TABLE_BILL);
		onCreate(db);
	}

}
