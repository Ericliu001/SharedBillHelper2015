package com.ericliudeveloper.sharedbillhelper.ui.presenter;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by liu on 8/06/15.
 */
public interface ListPresenter {


    public void setViewHolderData(RecyclerView.ViewHolder holder, Cursor cursor);

    RecyclerView.ViewHolder getCustomViewHolder(ViewGroup parent, int viewType);
}
