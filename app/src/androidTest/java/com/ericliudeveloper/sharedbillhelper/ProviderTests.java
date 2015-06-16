package com.ericliudeveloper.sharedbillhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.test.ProviderTestCase2;
import android.test.mock.MockContentResolver;
import android.util.Log;

import com.ericliudeveloper.sharedbillhelper.database.DatabaseConstants;
import com.ericliudeveloper.sharedbillhelper.model.Bill;
import com.ericliudeveloper.sharedbillhelper.model.BillDAO;
import com.ericliudeveloper.sharedbillhelper.model.Member;
import com.ericliudeveloper.sharedbillhelper.model.MemberDAO;
import com.ericliudeveloper.sharedbillhelper.model.Payment;
import com.ericliudeveloper.sharedbillhelper.model.PaymentDAO;
import com.ericliudeveloper.sharedbillhelper.model.PaymentInfo;
import com.ericliudeveloper.sharedbillhelper.model.PaymentInfoDAO;
import com.ericliudeveloper.sharedbillhelper.provider.BillContract;
import com.ericliudeveloper.sharedbillhelper.provider.BillProvider;

/**
 * Created by liu on 5/06/15.
 */
public class ProviderTests extends ProviderTestCase2<BillProvider> {

    private Context mContext;
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
        mContext = getMockContext();
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


    static String[] memberProjection = BillContract.Members.PROJECTION;
    static Uri membersUri = BillContract.Members.CONTENT_URI;
    public void testInsertMember() {
        Member member = new Member();
        member.setFirstName("Eric");
        member.setLastName("Liu");
        member.setEmail("someone@qq.com");
        member.setPhone("12345");
        member.setMoveInDate("2005-01-01");
        member.setMoveOutDate("2005-02-01");

        ContentValues values = MemberDAO.getContentValuesFromMemberInstance(member);

        Uri uri = mResolver.insert(membersUri, values);
        Log.d("eric", uri.toString());

        assertNotNull("no uri returned when inserting member.", uri);

        Cursor cursor = mResolver.query(uri, memberProjection, null , null, null);
        assertTrue("Nothing in the cursor when quering member.", cursor.getCount() > 0);

//        assertTrue("must fail", false);

        cursor.moveToFirst();
        Member retrievedMember = MemberDAO.getMemberFromCursor(cursor);
        assertEquals("Retrieved member instance is different from the one been saved. ", "Eric", retrievedMember.getFirstName());
    }


    static String[] paymentsProjection = BillContract.Payments.PROJECTION;
    static Uri paymentsUri = BillContract.Payments.CONTENT_URI;

    public void testInsertPayment() {

        // create a PaymentInfo instance
        PaymentInfo.Builder paymentInfoBuilder = new PaymentInfo.Builder();
        PaymentInfo paymentInfo = paymentInfoBuilder.description("I'm ok")
                .name("Sunday payment")
                .numberOfBillsPaid(3)
                .numberOfMembersPaid(3)
                .paidTime("1994-02-03")
                .totalAmount(32135.34)
                .build();

        ContentValues paymentInfoValues = PaymentInfoDAO.getContentValuesFromPaymentInfoInstance(paymentInfo);
        Uri paymentInfoUri = mResolver.insert(BillContract.PaymentInfos.CONTENT_URI, paymentInfoValues);
        assertNotNull("Returned null when inserting paymentInfo", paymentInfoUri);


        // create an Payee
        Member member = new Member();
        member.setFirstName("Eric");
        member.setLastName("Liu");
        member.setEmail("someone@qq.com");
        member.setPhone("12345");
        member.setMoveInDate("2005-01-01");
        member.setMoveOutDate("2005-02-01");

        ContentValues payeeValues = MemberDAO.getContentValuesFromMemberInstance(member);

        Uri payeeUri = mResolver.insert(membersUri, payeeValues);
        assertNotNull("Returned null when inserting Payee", payeeUri);


        Bill bill = new Bill();
        bill.setAmount(324.23);
        bill.setType("Weapons");
        bill.setEndDate("2015-06-30");
        bill.setStartDate("2014-05-01");

        ContentValues billValues = BillDAO.getContentValuesFromBillInstance(bill);

        Uri billUri = mResolver.insert(BillContract.Bills.CONTENT_URI, billValues);

        assertNotNull("Returned null when inserting bill", billUri);


        long paymentInfoId = Long.valueOf(paymentInfoUri.getLastPathSegment());
        long billId = Long.valueOf(billUri.getLastPathSegment());
        long memberId = Long.valueOf(payeeUri.getLastPathSegment());

        Payment.Builder builder = new Payment.Builder(paymentInfoId, billId, memberId);
        builder.payeeDays(10);
        builder.payeeStartDate("1981-10-10");
        builder.payeeEndDate("1981-10-20");
        builder.payee_amount(212.2);

        Payment payment = builder.build();

        ContentValues paymentValues = PaymentDAO.getContentValuesFromPaymentInstance(payment);
        Uri uri = mResolver.insert(paymentsUri, paymentValues);
        assertNotNull("returns null uri when inserting payment", uri);


        Cursor cursor = mResolver.query(uri, paymentsProjection, null, null, null);
        cursor.moveToFirst();

    }



}
