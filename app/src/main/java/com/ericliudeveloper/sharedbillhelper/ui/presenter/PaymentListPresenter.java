package com.ericliudeveloper.sharedbillhelper.ui.presenter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ericliudeveloper.sharedbillhelper.R;
import com.ericliudeveloper.sharedbillhelper.model.Bill;
import com.ericliudeveloper.sharedbillhelper.model.Member;
import com.ericliudeveloper.sharedbillhelper.model.Payment;
import com.ericliudeveloper.sharedbillhelper.util.CustomEvents;
import com.ericliudeveloper.sharedbillhelper.widget.CollectionView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liu on 21/06/15.
 */
public class PaymentListPresenter extends BasePresenter implements CollectionView.CollectionViewCallbacks {
    List<Payment> paymentList;
    List<Bill> billList = CalculationResultPresenter.billSelections;
    List<Member> memberList = CalculationResultPresenter.memberSelections;
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
        View billRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.bill_row_layout, parent, false);
        return new BillListPresenter.BillViewHolder(billRow, false);
    }

    @Override
    public RecyclerView.ViewHolder createDataViewHolder(ViewGroup parent) {
        // todo replace it with payment row
        View paymentRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.member_row_layout, parent, false);
        return new MemberListPresenter.MemberViewHolder(paymentRow, false);
    }

    @Override
    public void bindHeaderViewHolder(RecyclerView.ViewHolder holder, int headerIndex) {
        Bill bill = billList.get(headerIndex);
        ((BillListPresenter.BillViewHolder) holder).setItem(bill);
    }

    @Override
    public void bindDataViewHolder(RecyclerView.ViewHolder holder, int dataIndex) {

    }


    public interface PaymentListFace {
        void setCollectionViewParameters(CollectionView.Inventory inventory, CollectionView.CollectionViewCallbacks callbacks);
    }

}
