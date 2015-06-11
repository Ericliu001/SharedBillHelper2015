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
import com.ericliudeveloper.sharedbillhelper.ui.activity.ViewMemberDetailsActivity;
import com.ericliudeveloper.sharedbillhelper.ui.presenter.ListPresenter;
import com.ericliudeveloper.sharedbillhelper.ui.presenter.MemberListPresenter;
import com.ericliudeveloper.sharedbillhelper.util.CustomEvents;

import de.greenrobot.event.EventBus;


/**
 * A simple {@link Fragment} subclass.
 */
public class MemberListFragment extends RecyclerViewFragment implements LoaderManager.LoaderCallbacks<Cursor> {


    private int mMemberQueryToken = 2;

    public MemberListFragment() {
        // Required empty public constructor
    }


    @Override
    protected ListPresenter getPresenter() {
        return new MemberListPresenter();
    }

    @Override
    protected View getRowView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.member_row_layout, parent, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getLoaderManager().initLoader(mMemberQueryToken, null, this);
    }


    private void checkListEmpty() {
        if (mAdapter.getItemCount() == 0) {
            ImageView ivEmptyBillList = new ImageView(getActivity());
            ivEmptyBillList.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_action_face_unlock));
            mEmptyView.addView(ivEmptyBillList);
            mEmptyView.setVisibility(View.VISIBLE);
        } else {
            mEmptyView.setVisibility(View.GONE);
            mEmptyView.removeAllViews();
        }
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Loader<Cursor> loader = null;
        Uri uri = BillContract.Members.CONTENT_URI;
        String[] projection = BillContract.Members.PROJECTION;
        loader = new CursorLoader(getActivity(), uri, projection, null, null, null );
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
        checkListEmpty();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }



    @Override
    public void onResume() {
        EventBus.getDefault().register(this);
        super.onResume();
    }

    /**
     * Handle user click on List items
     * @param eventViewMember
     */
    public void onEvent(CustomEvents.EventViewMember eventViewMember) {
        Intent viewMemberDetailsIntent = new Intent(getActivity(), ViewMemberDetailsActivity.class);
        getActivity().startActivity(viewMemberDetailsIntent);
    }


    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }
}
