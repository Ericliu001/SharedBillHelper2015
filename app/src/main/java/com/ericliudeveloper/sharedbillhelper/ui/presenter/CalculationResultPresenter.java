package com.ericliudeveloper.sharedbillhelper.ui.presenter;

import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.net.Uri;
import android.os.RemoteException;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ericliudeveloper.sharedbillhelper.MyApplication;
import com.ericliudeveloper.sharedbillhelper.R;
import com.ericliudeveloper.sharedbillhelper.model.Bill;
import com.ericliudeveloper.sharedbillhelper.model.Member;
import com.ericliudeveloper.sharedbillhelper.model.Payment;
import com.ericliudeveloper.sharedbillhelper.model.PaymentDAO;
import com.ericliudeveloper.sharedbillhelper.model.PaymentInfo;
import com.ericliudeveloper.sharedbillhelper.model.PaymentInfoDAO;
import com.ericliudeveloper.sharedbillhelper.provider.BillContract;
import com.ericliudeveloper.sharedbillhelper.util.MyDateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liu on 19/06/15.
 */
public class CalculationResultPresenter {
    List<Bill> billSelections = new ArrayList(BillListPresenter.mSelection.values());
    List<Member> memberSelections = new ArrayList(MemberListPresenter.mSelection.values());
    double[] memberTotalAmountArray =  new double[memberSelections.size()];
    CalculationResultFace mCallbacks;

    static PaymentInfo mInfo;
    List<Payment> mPaymentList = null;


    public CalculationResultPresenter(CalculationResultFace callbacks) {
        mCallbacks = callbacks;
    }

    public void Calculate() {
        PaymentInfo.Builder infoBuilder = new PaymentInfo.Builder();

        infoBuilder.totalAmount(getTotalAmount());
        infoBuilder.numberOfMembersPaid(getNumberOfMembersPaid());
        infoBuilder.numberOfBillsPaid(getNumberOfBillsPaid());
        infoBuilder.paidTime(getCurrentTime());

        mInfo = infoBuilder.build();

        mPaymentList = new ArrayList<>();

        for (Bill bill : billSelections) {
            calculateMemberShareForEachBill(bill);
        }

        for (int i = 0; i < memberTotalAmountArray.length; i++) {
            for (int j = 0; j < billSelections.size(); j++) {
                memberTotalAmountArray[i] += mPaymentList.get(j * memberSelections.size() + i).getPayee_amount();
            }
        }

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
     *
     * @param bill
     */
    private void calculateMemberShareForEachBill(Bill bill) {
        double amount = bill.getAmount();


        // an Array to store the days each member is eligible for this bill
        int[] memberPayingDayArray = new int[memberSelections.size()];

        // an Array to store each member's start paying date for this bill
        String[] payeeStartDateArray = new String[memberSelections.size()];

        // an Array to store each member's end paying date for this bill
        String[] payeeEndDateArray = new String[memberSelections.size()];

        int index = 0;
        for (Member member : memberSelections) {
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
        double[] memberShareArray = new double[memberSelections.size()];

        // sum all all days all members have been in this bill
        double totalNumOfDays = 0d;
        for (int num : memberPayingDayArray) {
            totalNumOfDays += num;
        }

        for (int i = 0; i < memberShareArray.length; i++) {
            memberShareArray[i] = amount * memberPayingDayArray[i] / totalNumOfDays;
        }


        Member member1 = null; // avoid creating instances in a loop
        for (int i = 0; i < memberSelections.size(); i++) {
            member1 = memberSelections.get(i);
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
        for (Bill bill : billSelections) {
            totalAmount += bill.getAmount();
        }
        return totalAmount;
    }

    public RecyclerView.ViewHolder createViewHolder(ViewGroup parent, int viewType) {
        View rowLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_row, parent, false);
        return new MemberPaymentViewHolder(rowLayout);
    }

    public void bindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MemberPaymentViewHolder) holder).setItemData(memberSelections.get(position), memberTotalAmountArray[position]);
    }

    public int getItemCount() {
        return memberSelections.size();
    }


    public interface CalculationResultFace {

    }

    public static class MemberPaymentViewHolder extends RecyclerView.ViewHolder {
        private TextView tvPayeeFullName, tvPercentage;
        private ProgressBar pbPercentage;
        private TextView tvPayeeAmount;

        public MemberPaymentViewHolder(View itemview) {
            super(itemview);
            tvPayeeFullName = (TextView) itemview
                    .findViewById(R.id.tvPayeeFullName);

            tvPercentage = (TextView) itemview
                    .findViewById(R.id.tvPercentage);
            pbPercentage = (ProgressBar) itemview
                    .findViewById(R.id.pbPercentage);
            tvPayeeAmount = (TextView) itemview
                    .findViewById(R.id.tvPayeeAmout);
        }

        public void setItemData(Member member, double amout) {

            String payeeFullName = member.getFirstName() + " " + member.getLastName();
            tvPayeeFullName.setText(payeeFullName);

            String amountString = String.valueOf(amout);
            tvPayeeAmount.setText(amountString);

            if (mInfo != null) {
                int percentage = (int)(amout/mInfo.getTotalAmount());
                tvPercentage.setText(String.valueOf(percentage));
                pbPercentage.setProgress(percentage);
            }

        }
    }
}
