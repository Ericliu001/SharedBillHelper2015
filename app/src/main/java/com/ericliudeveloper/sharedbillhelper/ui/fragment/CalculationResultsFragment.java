package com.ericliudeveloper.sharedbillhelper.ui.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ericliudeveloper.sharedbillhelper.R;
import com.ericliudeveloper.sharedbillhelper.ui.presenter.CalculationResultPresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalculationResultsFragment extends BaseFragment implements CalculationResultPresenter.CalculationResultFace {

    CalculationResultPresenter mPresenter;

    private TextView tvSum;
    private TextView tvNumBill;
    private TextView tvNumMember;
    private RecyclerView rvPayment;

    public CalculationResultsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new CalculationResultPresenter(this);
        mPresenter.Calculate();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_calculation_results, container, false);
        setupViews(root);
        return root;
    }

    private void setupViews(View root) {
        tvSum = (TextView) root.findViewById(R.id.tvSum);
        tvNumBill = (TextView) root.findViewById(R.id.tvNumBill);
        tvNumMember = (TextView) root.findViewById(R.id.tvNumMember);
        rvPayment = (RecyclerView) root.findViewById(R.id.list);

        rvPayment.setAdapter(new PaymentListAdapter());

    }




    private class PaymentListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return mPresenter.createViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            mPresenter.bindViewHolder(holder, position);
        }

        @Override
        public int getItemCount() {
            return mPresenter.getItemCount();
        }
    }
}
