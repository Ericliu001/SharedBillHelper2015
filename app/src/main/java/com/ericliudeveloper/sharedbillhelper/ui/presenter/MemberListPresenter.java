package com.ericliudeveloper.sharedbillhelper.ui.presenter;

import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ericliudeveloper.sharedbillhelper.R;
import com.ericliudeveloper.sharedbillhelper.model.Member;
import com.ericliudeveloper.sharedbillhelper.model.MemberDAO;
import com.ericliudeveloper.sharedbillhelper.util.CustomEvents;
import com.ericliudeveloper.sharedbillhelper.util.ResouceUtils;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by liu on 8/06/15.
 */
public class MemberListPresenter implements ListPresenter {
    private boolean isListSelectionMode = false;


    public MemberListPresenter(boolean isSelectionMode) {
        isListSelectionMode = isSelectionMode;
    }

    @Override
    public RecyclerView.ViewHolder getCustomViewHolder(View itemView) {
        return new MemberViewHolder(itemView, isListSelectionMode);
    }

    @Override
    public void setViewHolderData(RecyclerView.ViewHolder holder, Cursor cursor) {
        Member member = MemberDAO.getMemberFromCursor(cursor);
        if (member != null) {
            ((MemberViewHolder)holder).setItem(member);
        }
    }

    public static class MemberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private boolean isSelectionMode = false;

        private Member mMember;
        CardView cardView;
        TextView  tvFirstName, tvLastName;
        CheckBox checkBox;

        public MemberViewHolder(View itemView, boolean isSelectionMode) {
            super(itemView);
            this.isSelectionMode = isSelectionMode;

            cardView = (CardView) itemView.findViewById(R.id.card_view);
            tvFirstName = (TextView) itemView.findViewById(R.id.tvFristName);
            tvLastName = (TextView) itemView.findViewById(R.id.tvLastName);
            checkBox = (CheckBox) itemView.findViewById(android.R.id.checkbox);

            itemView.setOnClickListener(this);

            if (isSelectionMode) {
                checkBox.setVisibility(View.VISIBLE);
                int pixels = ResouceUtils.getAppResources().getDimensionPixelSize(R.dimen.selectionModeRowItemHeight);
                cardView.getLayoutParams().height = pixels;
            } else {
                checkBox.setVisibility(View.GONE);
            }
        }

        public void setItem(Member member) {
            mMember = member;

            refreshDisplay(member);
        }

        public void refreshDisplay(Member member) {
            String id = String.valueOf(member.getId());
            String first = member.getFirstName();
            String last = member.getLastName();

            tvFirstName.setText(first);
            tvLastName.setText(last);
        }


        @Override
        public void onClick(View v) {

            if (isSelectionMode) {
                checkBox.setChecked(!checkBox.isChecked());
            } else {

                CustomEvents.EventViewMember eventViewMember = new CustomEvents.EventViewMember(mMember);

                EventBus.getDefault().postSticky(eventViewMember);
            }

        }
    }
}
