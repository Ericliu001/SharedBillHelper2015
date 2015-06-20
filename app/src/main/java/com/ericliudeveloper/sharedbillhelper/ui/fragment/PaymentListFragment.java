package com.ericliudeveloper.sharedbillhelper.ui.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ericliudeveloper.sharedbillhelper.R;
import com.ericliudeveloper.sharedbillhelper.widget.CollectionView;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentListFragment extends Fragment {
    CollectionView mCollectionView;

    public PaymentListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_payment_list, container, false);
        mCollectionView = (CollectionView) root.findViewById(R.id.collectionView);
        return root;
    }



}
