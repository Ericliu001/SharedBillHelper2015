package com.ericliudeveloper.sharedbillhelper.ui.presenter;

import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.net.Uri;
import android.os.RemoteException;
import android.text.TextUtils;

import com.ericliudeveloper.sharedbillhelper.MyApplication;
import com.ericliudeveloper.sharedbillhelper.model.Bill;
import com.ericliudeveloper.sharedbillhelper.model.Member;
import com.ericliudeveloper.sharedbillhelper.model.Payment;
import com.ericliudeveloper.sharedbillhelper.model.PaymentDAO;
import com.ericliudeveloper.sharedbillhelper.model.PaymentInfo;
import com.ericliudeveloper.sharedbillhelper.model.PaymentInfoDAO;
import com.ericliudeveloper.sharedbillhelper.provider.BillContract;
import com.ericliudeveloper.sharedbillhelper.util.MyDateUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Created by liu on 19/06/15.
 */
public class CalculationResultPresenter {
    HashMap billSelections = BillListPresenter.mSelection;
    HashMap memberSelections = MemberListPresenter.mSelection;
    CalculationResultFace mCallbacks;

    PaymentInfo mInfo;
    List<Payment> mPaymentList = new ArrayList<>();


    public CalculationResultPresenter(CalculationResultFace callbacks) {
        mCallbacks = callbacks;
    }

    public void Calculate() {
        PaymentInfo.Builder infoBuilder = new PaymentInfo.Builder();

        infoBuilder.description(mCallbacks.getDescription());
        infoBuilder.totalAmount(getTotalAmount());
        infoBuilder.numberOfMembersPaid(getNumberOfMembersPaid());
        infoBuilder.numberOfBillsPaid(getNumberOfBillsPaid());
        infoBuilder.paidTime(getCurrentTime());

        mInfo = infoBuilder.build();


        String paymentInfoSerialNumber = mInfo.getSerialNumber();

        Collection<Bill> bills = billSelections.values();


        Bill bill = null;


        while (bills.iterator().hasNext()) {
            bill = bills.iterator().next();
            calculateMemberShareForEachBill(bill);
        }
        
        saveEverythingToDB();

    }

    private void saveEverythingToDB() {
        ArrayList<ContentProviderOperation> batch = new ArrayList<>();
        // save the PaymentInfo
        PaymentInfoDAO.savePaymentInfo(mInfo, null);


        // do a bulk insert with the help of ContentProviderOperation
        for (Payment payment : mPaymentList) {
            ContentValues values = PaymentDAO.getContentValuesFromPaymentInstance(payment);
            Uri uri = BillContract.Payments.CONTENT_URI;
            ContentProviderOperation operation = ContentProviderOperation.newInsert(uri).withValues(values).build();
            batch.add(operation);
        }

        try {
            MyApplication.getAppContentResolver().applyBatch(BillContract.CONTENT_AUTHORITY, batch);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (OperationApplicationException e) {
            e.printStackTrace();
        }


    }

    /**
     * Calculate how much money each member should pay for this bill
     * and other necessary info
     * @param bill
     */
    private void calculateMemberShareForEachBill(Bill bill) {
        double amount = bill.getAmount();
        Collection<Member> members = memberSelections.values();
        ArrayList<Member> memberList = new ArrayList<>();


        // an Array to store the days each member is eligible for this bill
        int[] memberPayingDayArray = new int[members.size()];

        // an Array to store each member's start paying date for this bill
        String[] payeeStartDateArray = new String[members.size()];

        // an Array to store each member's end paying date for this bill
        String[] payeeEndDateArray = new String[members.size()];

        Member member = null;
        int index = 0;
        while (members.iterator().hasNext()) {
            member = members.iterator().next();
            memberList.add(member);
            int payingDaysForThisBill = MyDateUtils.calculateMemberPayingDays(member.getMoveInDate()
                    , member.getMoveOutDate()
                    , bill.getStartDate()
                    , bill.getEndDate());


            String[] payeeStartEndDateArray = MyDateUtils.getPayeeStartEndDates(
                    member.getMoveInDate()
                    , member.getMoveOutDate()
                    , bill.getStartDate()
                    , bill.getEndDate()
            );

            if (payeeStartDateArray != null) {
                payeeStartDateArray[index] = payeeStartDateArray[0];
                payeeEndDateArray[index] = payeeStartEndDateArray[1];
            }


            if (payingDaysForThisBill > 0) {
                memberPayingDayArray[index] = payingDaysForThisBill;
            }

            index++;
        }

        // an Array to store how much money for each member to pay for this bill
        double[] memberShareArray = new double[members.size()];

        // sum all all days all members have been in this bill
        double totalNumOfDays = 0d;
        for (int num : memberPayingDayArray) {
            totalNumOfDays += num;
        }

        for (int i = 0; i < memberShareArray.length; i++) {
            memberShareArray[i] = amount * memberPayingDayArray[i] / totalNumOfDays;
        }


        Member member1 = null; // avoid creating instances in a loop
        for (int i = 0; i < memberList.size(); i++) {
            member1 = memberList.get(i);
            Payment.Builder paymentBuilder = new Payment.Builder(mInfo.getSerialNumber(), bill.getId(), member1.getId());
            int payeeDays = memberPayingDayArray[i];
            paymentBuilder.payeeDays(payeeDays);

            double payeeAmount = memberShareArray[i];
            paymentBuilder.payee_amount(payeeAmount);

            String payeeStartDate = payeeStartDateArray[i];
            if (!TextUtils.isEmpty(payeeStartDate)) {
                paymentBuilder.payeeStartDate(payeeStartDate);
            }

            String payeeEndDate = payeeEndDateArray[i];
            if (!TextUtils.isEmpty(payeeEndDate)) {
                paymentBuilder.payeeEndDate(payeeEndDate);
            }

            mPaymentList.add(paymentBuilder.build());
        }

    }

    private String getCurrentTime() {
        return MyDateUtils.getCurrentTimeString();
    }

    private int getNumberOfBillsPaid() {
        return billSelections.size();
    }

    private int getNumberOfMembersPaid() {
        return memberSelections.size();
    }

    private double getTotalAmount() {
        double totalAmount = 0d;
        for (Payment payment : mPaymentList) {
            totalAmount += payment.getPayee_amount();
        }
        return totalAmount;
    }


    public interface CalculationResultFace {

        String getDescription();
    }
}
