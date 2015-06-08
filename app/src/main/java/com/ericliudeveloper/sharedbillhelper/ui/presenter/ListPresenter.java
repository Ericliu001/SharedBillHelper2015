package com.ericliudeveloper.sharedbillhelper.ui.presenter;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by liu on 8/06/15.
 */
public interface ListPresenter {

    public RecyclerView.ViewHolder getCustomViewHolder(View itemView);

    public void setViewHolderData(RecyclerView.ViewHolder holder, Cursor cursor);
}
