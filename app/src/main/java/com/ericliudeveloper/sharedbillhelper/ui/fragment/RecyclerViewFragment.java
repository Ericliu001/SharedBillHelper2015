package com.ericliudeveloper.sharedbillhelper.ui.fragment;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ericliudeveloper.sharedbillhelper.R;
import com.ericliudeveloper.sharedbillhelper.adapter.CursorRecyclerAdapter;
import com.ericliudeveloper.sharedbillhelper.ui.presenter.ListPresenter;

/**
 * Created by liu on 8/06/15.
 */
public abstract class RecyclerViewFragment extends Fragment {
    protected RecyclerView mRecyclerView;
    protected CursorRecyclerAdapter mAdapter;
    protected ListPresenter mPresenter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.list_layout, container, false);
        setupViews(root);

        return root;
    }

    protected void setupViews(View root) {
        mRecyclerView = (RecyclerView) root.findViewById(R.id.list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);


        mAdapter = new CursorRecyclerAdapter(null) {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View itemView;
                RecyclerView.ViewHolder holder;
                itemView = getRowView(parent, viewType);
                holder = mPresenter.getCustomViewHolder(itemView);
                return holder;
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, Cursor cursor) {
                mPresenter.setViewHolderData(holder, cursor);
            }


        };

        mRecyclerView.setAdapter(mAdapter);
    }

    protected abstract View getRowView(ViewGroup parent, int viewType);


}
