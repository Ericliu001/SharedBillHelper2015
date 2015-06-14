package com.ericliudeveloper.sharedbillhelper.ui.presenter;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
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
    private boolean isListSelectionMode = false;

    public BillListPresenter(boolean isSelectionMode) {
        isListSelectionMode = isSelectionMode;
    }

    @Override
    public RecyclerView.ViewHolder getCustomViewHolder(View itemView) {
        return new BillViewHolder(itemView, isListSelectionMode);
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

        private boolean isSelectionMode = false;


        private TextView tvType;
        private TextView tvAmount;
        private TextView tvPaid;
        private TextView tvDueDay;
        private CheckBox checkBox;

        public BillViewHolder(View itemView, boolean isSelectionMode) {
            super(itemView);
            this.isSelectionMode = isSelectionMode;
            tvType = (TextView) itemView.findViewById(R.id.tvType);
            tvAmount = (TextView) itemView.findViewById(R.id.tvAmount);
            tvPaid = (TextView) itemView.findViewById(R.id.tvPaid);
            tvDueDay = (TextView) itemView.findViewById(R.id.tvDueDay);
            checkBox = (CheckBox) itemView.findViewById(android.R.id.checkbox);

            itemView.setOnClickListener(this);

            if (isSelectionMode) {
                tvPaid.setVisibility(View.GONE);
                checkBox.setVisibility(View.VISIBLE);
            } else {
                tvPaid.setVisibility(View.VISIBLE);
                checkBox.setVisibility(View.GONE);
            }
        }


        public void setItem(Bill bill) {
            mBill = bill;

            refreshDisplay(bill);
        }

        public void refreshDisplay(Bill bill) {
            String type = bill.getType();
            String amount = String.valueOf(bill.getAmount());
            String isPaid = bill.getPaid() > 0 ? tvAmount.getResources().getString(R.string.paid)
                    : tvAmount.getResources().getString(R.string.unpaid);

            String dueDay = bill.getDueDate();

            tvType.setText(type);
            tvAmount.setText(amount);
            tvPaid.setText(isPaid);
            tvDueDay.setText(dueDay);
        }


        @Override
        public void onClick(View v) {

            if (isSelectionMode) {

                checkBox.setChecked(!checkBox.isChecked());

            } else {

                CustomEvents.EventViewBill eventViewBill = new CustomEvents.EventViewBill(mBill);

                // send an sticky event to the view detail Fragment
                EventBus.getDefault().postSticky(eventViewBill);

            }
        }


    }
}
