package com.ericliudeveloper.sharedbillhelper.ui.fragment;


import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ericliudeveloper.sharedbillhelper.R;
import com.ericliudeveloper.sharedbillhelper.provider.BillContract;
import com.ericliudeveloper.sharedbillhelper.ui.activity.ViewBillDetailsActivity;
import com.ericliudeveloper.sharedbillhelper.ui.presenter.BillListPresenter;
import com.ericliudeveloper.sharedbillhelper.ui.presenter.ListPresenter;
import com.ericliudeveloper.sharedbillhelper.util.CursorUtils;
import com.ericliudeveloper.sharedbillhelper.util.CustomEvents;

import java.util.List;

import de.greenrobot.event.EventBus;


/**
 * A simple {@link Fragment} subclass.
 */
public class BillListFragment extends RecyclerViewFragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private int mBillQueryToken = 1;
    protected boolean isListSelectionMode = false;
    protected List<Long> mIdList;


    @Override
    protected ListPresenter getPresenter() {
        return new BillListPresenter(isListSelectionMode);
    }

    @Override
    protected View getRowView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.bill_row_layout, parent, false);
    }


    public BillListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mPresenter = new BillListPresenter();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        getLoaderManager().initLoader(mBillQueryToken, null, this);
    }


    protected boolean isListEmpty() {
        if (mAdapter.getItemCount() == 0) {
            return true;
        } else {
            return false;
        }
    }

    protected void displayEmptyView(boolean isEmpty) {
        if (isEmpty) {
            ImageView ivEmptyBillList = new ImageView(getActivity());
            ivEmptyBillList.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_action_assignment));
            mEmptyView.addView(ivEmptyBillList);
            mEmptyView.setVisibility(View.VISIBLE);
        } else {
            mEmptyView.setVisibility(View.GONE);
            mEmptyView.removeAllViews();
        }

    }

    public List<Long> getIdList() {
        return mIdList;
    }


    @Override
    public void onResume() {
        EventBus.getDefault().register(this);
        super.onResume();
    }

    /**
     * Handle user click on List items
     *
     * @param eventViewBill
     */
    public void onEvent(CustomEvents.EventViewBill eventViewBill) {
        Intent viewBillDetailsIntent = new Intent(getActivity(), ViewBillDetailsActivity.class);
        getActivity().startActivity(viewBillDetailsIntent);
    }


    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Loader<Cursor> loader = null;
        Uri uri = BillContract.Bills.CONTENT_URI;
        String[] projection = BillContract.Bills.PROJECTION;
        loader = new CursorLoader(getActivity(), uri, projection, null, null, null);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
        if (isListEmpty()) {
            displayEmptyView(true);
        } else {
            displayEmptyView(false);
            mIdList = CursorUtils.getIdListFromCursor(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
        mIdList = null;
    }


}
