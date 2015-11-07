package com.ericliudeveloper.sharedbillhelper.ui.presenter;

import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Intent;
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
import com.ericliudeveloper.sharedbillhelper.util.CustomEvents;
import com.ericliudeveloper.sharedbillhelper.util.DigitUtils;
import com.ericliudeveloper.sharedbillhelper.util.MemberUtil;
import com.ericliudeveloper.sharedbillhelper.util.MyDateUtils;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by liu on 19/06/15.
 */
public class CalculationResultPresenter {
    public List<Bill> billSelections = new ArrayList(BillListPresenter.mSelection.values());
    public List<Member> memberSelections = new ArrayList(MemberListPresenter.mSelection.values());
    double[] memberTotalAmountArray = new double[memberSelections.size()];
    CalculationResultFace mCallbacks;

    static PaymentInfo mInfo;
    List<Payment> mPaymentList = null;
    private Activity context;


    public void refreshDisplay() {

        mCallbacks.showTotalAmount(String.valueOf(mInfo.getTotalAmount()));
        mCallbacks.showNumberOfMembersPaying(String.valueOf(memberSelections.size()));
        mCallbacks.showNumberOfBillsPaid(String.valueOf(billSelections.size()));
    }


    public CalculationResultPresenter(CalculationResultFace callbacks) {
        mCallbacks = callbacks;
    }

    public void calculate() {
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

        // post the event
        EventBus.getDefault().postSticky(new CustomEvents.EventCalculationFinished(mPaymentList));
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
     * calculate how much money each member should pay for this bill
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

            if (payeeStartEndDateArray != null) {
                payeeStartDateArray[index] = payeeStartEndDateArray[0];
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
            if (totalNumOfDays > 0) {
                memberShareArray[i] = amount * memberPayingDayArray[i] / totalNumOfDays;
            } else {
                memberShareArray[i] = 0d;
            }
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

    public void startActionSend() {
        Intent smsIntent = new Intent(Intent.ACTION_SEND);
        smsIntent.putExtra(Intent.EXTRA_TEXT, composeSMS());
        if (smsIntent.resolveActivity(context.getPackageManager()) != null) {
            mCallbacks.startActivity(smsIntent);
        }
    }

    private String composeSMS() {
        StringBuilder builder = new StringBuilder();
        builder.append("Total ");
        builder.append(billSelections.size());
        builder.append(" bills need to pay. ");

        Member member = null;
        double amount;
        for (int i = 0; i < memberSelections.size(); i++) {

            member = memberSelections.get(i);
            amount = memberTotalAmountArray[i];
            builder.append(member.getFirstName());
            if (!TextUtils.isEmpty(member.getLastName())) {
                builder.append(" " +member.getLastName());
            }
            builder.append(" " +DigitUtils.convertToDollarFormat(amount) + "; ");
        }


        return builder.toString();
    }

    public void setContext(Activity context) {
        this.context = context;
    }


    public interface CalculationResultFace {

        void showTotalAmount(String amount);

        void showNumberOfMembersPaying(String numMembers);

        void showNumberOfBillsPaid(String numBills);

        void startActivity(Intent intent);
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

        public void setItemData(Member member, double amount) {

            String payeeFullName = MemberUtil.getFullNameString(member);

            tvPayeeFullName.setText(payeeFullName);

            String amountString = DigitUtils.convertToDollarFormat(amount);
            tvPayeeAmount.setText(amountString);

            if (mInfo != null) {
                int percentage = (int) (100 * amount / mInfo.getTotalAmount());
                String percentageHint = String.valueOf(percentage) + "%";
                tvPercentage.setHint(percentageHint);
                pbPercentage.setProgress(percentage);
            }

        }
    }


}
