package com.ericliudeveloper.sharedbillhelper.ui.presenter;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ericliudeveloper.sharedbillhelper.model.Member;

/**
 * Created by liu on 8/06/15.
 */
public class MemberListPresenter implements ListPresenter {
    @Override
    public RecyclerView.ViewHolder getCustomViewHolder(View itemView) {
        return new MemberViewHolder(itemView);
    }

    @Override
    public void setViewHolderData(RecyclerView.ViewHolder holder, Cursor cursor) {

    }

    public static class MemberViewHolder extends RecyclerView.ViewHolder {

        public MemberViewHolder(View itemView) {
            super(itemView);
        }

        public void setItem(Member member) {

        }

        public void refreshDisplay(Member member) {

        }


    }
}
