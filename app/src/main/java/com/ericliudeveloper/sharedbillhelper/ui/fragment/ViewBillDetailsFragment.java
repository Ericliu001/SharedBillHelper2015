package com.ericliudeveloper.sharedbillhelper.ui.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ericliudeveloper.sharedbillhelper.R;
import com.ericliudeveloper.sharedbillhelper.model.Bill;
import com.ericliudeveloper.sharedbillhelper.util.CustomEvents;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.greenrobot.event.EventBus;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewBillDetailsFragment extends BaseFragment {

    @InjectView(R.id.tvType)
    TextView tvType;
    @InjectView(R.id.tvAmount)
    TextView tvAmount;
    @InjectView(R.id.tvStartDate)
    TextView tvStartDate;
    @InjectView(R.id.tvEndDate)
    TextView tvEndDate;
    @InjectView(R.id.tvDueDay)
    TextView tvDueDay;
    @InjectView(R.id.tvIsPaid)
    TextView tvIsPaid;

    public ViewBillDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_view_bill_details, container, false);
        ButterKnife.inject(this, root);

        return root;
    }


    @Override
    public void onResume() {
        EventBus.getDefault().registerSticky(this);
        super.onResume();
    }

    public void onEvent(CustomEvents.EventViewBill eventViewBill) {
        Bill bill = eventViewBill.bill;
        refreshDisplay(bill);
    }


    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }


    private void refreshDisplay(Bill bill) {
        String type = bill.getType();
        String amount = String.valueOf(bill.getAmount());
        String start = bill.getStartDate();
        String end = bill.getEndDate();
        String due = bill.getDueDate();
        String yes = getActivity().getResources().getString(android.R.string.yes);
        String no = getActivity().getResources().getString(android.R.string.no);
        String isPaid = (bill.getPaid() > 0) ? yes : no;

        tvType.setText(type);
        tvAmount.setText(amount);
        tvStartDate.setText(start);
        tvEndDate.setText(end);
        tvDueDay.setText(due);
        tvIsPaid.setText(isPaid);
    }


}
