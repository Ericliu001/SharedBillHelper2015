package com.ericliudeveloper.sharedbillhelper.ui.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ericliudeveloper.sharedbillhelper.R;
import com.ericliudeveloper.sharedbillhelper.ui.presenter.PaymentListPresenter;
import com.ericliudeveloper.sharedbillhelper.widget.CollectionView;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentListFragment extends BaseFragment implements PaymentListPresenter.PaymentListFace {
    CollectionView mCollectionView;
    PaymentListPresenter mPresenter;

    public PaymentListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new PaymentListPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_payment_list, container, false);
        mCollectionView = (CollectionView) root.findViewById(R.id.collectionView);
        mCollectionView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return root;
    }


    @Override
    public void onResume() {
        super.onResume();
        mPresenter.registerStickyEventBusListener();
    }


    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unregisterEventBusListener();
    }

    @Override
    public void setCollectionViewParameters(CollectionView.Inventory inventory, CollectionView.CollectionViewCallbacks callbacks) {
        mCollectionView.setDataAndCallback(inventory, callbacks);
    }
}
