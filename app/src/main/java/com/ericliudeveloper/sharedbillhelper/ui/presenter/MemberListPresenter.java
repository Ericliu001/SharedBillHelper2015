package com.ericliudeveloper.sharedbillhelper.ui.presenter;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ericliudeveloper.sharedbillhelper.R;
import com.ericliudeveloper.sharedbillhelper.model.Member;
import com.ericliudeveloper.sharedbillhelper.model.MemberDAO;
import com.ericliudeveloper.sharedbillhelper.util.CustomEvents;

import de.greenrobot.event.EventBus;

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
        Member member = MemberDAO.getMemberFromCursor(cursor);
        if (member != null) {
            ((MemberViewHolder)holder).setItem(member);
        }
    }

    public static class MemberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Member mMember;
        TextView tvId, tvFirstName, tvLastName;

        public MemberViewHolder(View itemView) {
            super(itemView);
            tvId = (TextView) itemView.findViewById(R.id.tvId);
            tvFirstName = (TextView) itemView.findViewById(R.id.tvFristName);
            tvLastName = (TextView) itemView.findViewById(R.id.tvLastName);

            itemView.setOnClickListener(this);
        }

        public void setItem(Member member) {
            mMember = member;

            refreshDisplay(member);
        }

        public void refreshDisplay(Member member) {
            String id = String.valueOf(member.getId());
            String first = member.getFirstName();
            String last = member.getLastName();

            tvId.setText(id);
            tvFirstName.setText(first);
            tvLastName.setText(last);
        }


        @Override
        public void onClick(View v) {
            CustomEvents.EventViewMember eventViewMember = new CustomEvents.EventViewMember(mMember);

            EventBus.getDefault().postSticky(eventViewMember);
        }
    }
}
