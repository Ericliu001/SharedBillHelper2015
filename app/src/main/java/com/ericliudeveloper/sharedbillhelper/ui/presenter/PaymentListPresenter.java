package com.ericliudeveloper.sharedbillhelper.ui.presenter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ericliudeveloper.sharedbillhelper.R;
import com.ericliudeveloper.sharedbillhelper.model.Bill;
import com.ericliudeveloper.sharedbillhelper.model.Member;
import com.ericliudeveloper.sharedbillhelper.model.Payment;
import com.ericliudeveloper.sharedbillhelper.util.CustomEvents;
import com.ericliudeveloper.sharedbillhelper.util.DigitUtils;
import com.ericliudeveloper.sharedbillhelper.util.MemberUtil;
import com.ericliudeveloper.sharedbillhelper.widget.CollectionView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liu on 21/06/15.
 */
public class PaymentListPresenter extends BasePresenter implements CollectionView.CollectionViewCallbacks {
    List<Payment> paymentList;
    List<Bill> billList = new ArrayList(BillListPresenter.mSelection.values());
    List<Member> memberList = new ArrayList(MemberListPresenter.mSelection.values());
    PaymentListFace mCallbacks;
    CollectionView.Inventory<Bill, Payment> mInventory;


    public PaymentListPresenter(PaymentListFace callbacks) {
        mCallbacks = callbacks;
    }

    public void onEvent(CustomEvents.EventCalculationFinished eventCalculationFinished) {
        paymentList = eventCalculationFinished.paymentList;
//        EventBus.getDefault().removeStickyEvent(eventCalculationFinished);
        createHeaders(paymentList);

    }

    private void createHeaders(List<Payment> paymentList) {
        if (billList.size() == 0 || paymentList.size() == 0) {
            // todo error nothing to show
            return;
        }

        List<CollectionView.Header> headerList = new ArrayList<>();

        for (int i = 0; i < billList.size(); i++) {
            int insertPosition = i * memberList.size();
            CollectionView.Header<Bill> header = new CollectionView.Header<Bill>(insertPosition, billList.get(i));
            headerList.add(header);
        }

        mInventory = new CollectionView.Inventory<Bill, Payment>(headerList, paymentList);

        mCallbacks.setCollectionViewParameters(mInventory, this);
    }


    @Override
    public RecyclerView.ViewHolder createHeaderViewHolder(ViewGroup parent) {
        View billRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_bill_row_layout, parent, false);
        return new HeaderBillViewHolder(billRow);
    }

    @Override
    public RecyclerView.ViewHolder createDataViewHolder(ViewGroup parent) {
        View paymentRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_row_layout, parent, false);
        return new PaymenViewHolder(paymentRow);
    }

    @Override
    public void bindHeaderViewHolder(RecyclerView.ViewHolder holder, int headerIndex) {
        Bill bill = billList.get(headerIndex);
        ((HeaderBillViewHolder) holder).setItem(bill);
    }

    @Override
    public void bindDataViewHolder(RecyclerView.ViewHolder holder, int dataIndex) {
        Payment payment = paymentList.get(dataIndex);
        ((PaymenViewHolder)holder).setItem(payment);
    }


    public static class HeaderBillViewHolder extends RecyclerView.ViewHolder {

        private TextView tvType, tvAmount, tvPaid, tvStartDay, tvEndDate ;

        public HeaderBillViewHolder(View itemView) {
            super(itemView);

            tvType = (TextView) itemView.findViewById(R.id.tvType);
            tvAmount = (TextView) itemView.findViewById(R.id.tvAmount);
            tvPaid = (TextView) itemView.findViewById(R.id.tvPaid);
            tvStartDay = (TextView) itemView.findViewById(R.id.tvStartDate);
            tvEndDate = (TextView) itemView.findViewById(R.id.tvEndDate);
        }

        public void setItem(Bill bill) {
            refreshDisplay(bill);
        }

        public void refreshDisplay(Bill bill) {
            String type = bill.getType();
            String amount = amount = DigitUtils.convertToDollarFormat(bill.getAmount());
            String isPaid = bill.getPaid() > 0 ? tvAmount.getResources().getString(R.string.paid)
                    : tvAmount.getResources().getString(R.string.unpaid);

            String startDay = bill.getStartDate();
            String endDay = bill.getEndDate();

            tvType.setText(type);
            tvAmount.setText(amount);
            tvPaid.setText(isPaid);
            tvStartDay.setText(startDay);
            tvEndDate.setText(endDay);
        }
    }

    public static class PaymenViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvAmount, tvPayingDays, tvStartDate, tvEndDate;

        public PaymenViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvAmount = (TextView) itemView.findViewById(R.id.tvAmount);
            tvPayingDays = (TextView) itemView.findViewById(R.id.tvPayingDays);
            tvStartDate = (TextView) itemView.findViewById(R.id.tvStartDate);
            tvEndDate = (TextView) itemView.findViewById(R.id.tvEndDate);
        }

        public void setItem(Payment payment) {
            long memberId = payment.getPayee_id();
            Member member = MemberListPresenter.mSelection.get(memberId);
            if (member == null) {
                return;
            }

            String fullName = MemberUtil.getFullNameString(member);
            tvName.setText(fullName);
            String amount = DigitUtils.convertToDollarFormat(payment.getPayee_amount());
            tvAmount.setText(amount);
            String payingDays = String.valueOf(payment.getPayee_days());
            tvPayingDays.setText(payingDays);
            tvStartDate.setText(payment.getPayee_start_date());
            tvEndDate.setText(payment.getPayee_end_date());
        }
    }


    public interface PaymentListFace {
        void setCollectionViewParameters(CollectionView.Inventory inventory, CollectionView.CollectionViewCallbacks callbacks);
    }

}
