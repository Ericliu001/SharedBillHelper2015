package com.ericliudeveloper.sharedbillhelper.ui.fragment;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ericliudeveloper.sharedbillhelper.R;
import com.ericliudeveloper.sharedbillhelper.model.Bill;
import com.ericliudeveloper.sharedbillhelper.model.BillDAO;
import com.ericliudeveloper.sharedbillhelper.ui.activity.CalculationParameterActivity;
import com.ericliudeveloper.sharedbillhelper.ui.activity.EditBillActivity;
import com.ericliudeveloper.sharedbillhelper.ui.dialog.DeleteDialog;
import com.ericliudeveloper.sharedbillhelper.util.CustomEvents;
import com.ericliudeveloper.sharedbillhelper.util.Router;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.greenrobot.event.EventBus;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewBillDetailsFragment extends BaseFragment {

    Bill mBill;


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
        mBill = eventViewBill.bill;
        refreshDisplay(mBill);
    }

    public void onEvent(CustomEvents.EventActionDelete eventDeleteBill) {
        if (mBill == null) {
            return;
        }

        int yesDeleted = 1;
        mBill.setDeleted(yesDeleted);
        BillDAO.saveBill(mBill, null);
        if (getActivity() != null) {
            getActivity().finish();
        }
    }


    public void onEvent(CustomEvents.EventFABClicked eventFABClicked) {
        EventBus.getDefault().postSticky(new CustomEvents.EventSetCalculationParameters(mBill));
        Router.startActivity(getActivity(), CalculationParameterActivity.class, null);
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
        String yes = getActivity().getResources().getString(R.string.yes);
        String no = getActivity().getResources().getString(R.string.no);
        String isPaid = (bill.getPaid() > 0) ? yes : no;

        tvType.setText(type);
        tvAmount.setText(amount);
        tvStartDate.setText(start);
        tvEndDate.setText(end);
        tvDueDay.setText(due);
        tvIsPaid.setText(isPaid);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_view_details, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_edit:
                EventBus.getDefault().postSticky(new CustomEvents.EventEditBill(mBill));
                Intent gotoEditIntent = new Intent(getActivity(), EditBillActivity.class);
                getActivity().startActivity(gotoEditIntent);
                if (getActivity() != null) {
                    getActivity().finish();
                }
                break;


            case R.id.action_delete:
                showDeleteBillDialog();

                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDeleteBillDialog() {
        Bundle args = new Bundle();
        args.putString(DeleteDialog.TITLE,
                getResources().getString(R.string.confirm_delete));
        args.putString(DeleteDialog.MESSAGE,
                getResources().getString(R.string.delete_bill));
        DeleteDialog deleteDialog = DeleteDialog.newInstance(args);
        deleteDialog.show(getFragmentManager(), "delete");
    }


}
