package com.ericliudeveloper.sharedbillhelper.ui.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ericliudeveloper.sharedbillhelper.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalculationResultsFragment extends Fragment {


    public CalculationResultsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_calculation_results, container, false);

        return root;
    }


}
