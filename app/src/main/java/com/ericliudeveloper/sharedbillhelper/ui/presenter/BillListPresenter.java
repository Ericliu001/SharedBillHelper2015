package com.ericliudeveloper.sharedbillhelper.ui.presenter;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ericliudeveloper.sharedbillhelper.R;
import com.ericliudeveloper.sharedbillhelper.model.Bill;
import com.ericliudeveloper.sharedbillhelper.model.BillDAO;
import com.ericliudeveloper.sharedbillhelper.util.CustomEvents;

import de.greenrobot.event.EventBus;

/**
 * Created by liu on 8/06/15.
 */
public class BillListPresenter implements ListPresenter {

    public BillListPresenter() {
    }

    @Override
    public RecyclerView.ViewHolder getCustomViewHolder(View itemView) {
        return new BillViewHolder(itemView);
    }

    @Override
    public void setViewHolderData(RecyclerView.ViewHolder holder, Cursor cursor) {

        Bill bill = BillDAO.getBillFromCursor(cursor);
        if (bill != null) {
            ((BillViewHolder) holder).setItem(bill);
        }
    }


    public static class BillViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Bill mBill;

        private TextView tvAmount;
        private TextView tvPaid;
        private TextView tvDueDay;

        public BillViewHolder(View itemView) {
            super(itemView);
            tvAmount = (TextView) itemView.findViewById(R.id.tvAmount);
            tvPaid = (TextView) itemView.findViewById(R.id.tvPaid);
            tvDueDay = (TextView) itemView.findViewById(R.id.tvDueDay);

            itemView.setOnClickListener(this);
        }


        public void setItem(Bill bill) {
            mBill = bill;

            refreshDisplay(bill);
        }

        public void refreshDisplay(Bill bill) {
            String amount = String.valueOf(bill.getAmount());
            String isPaid = bill.getPaid() > 0 ? tvAmount.getResources().getString(R.string.paid)
                    : tvAmount.getResources().getString(R.string.unpaid);

            String dueDay = bill.getDueDate();

            tvAmount.setText(amount);
            tvPaid.setText(isPaid);
            tvDueDay.setText(dueDay);
        }


        @Override
        public void onClick(View v) {
            CustomEvents.EventViewBill eventViewBill = new CustomEvents.EventViewBill(mBill);

            // send an sticky event to the view detail Fragment
            EventBus.getDefault().postSticky(eventViewBill);
        }
    }
}
