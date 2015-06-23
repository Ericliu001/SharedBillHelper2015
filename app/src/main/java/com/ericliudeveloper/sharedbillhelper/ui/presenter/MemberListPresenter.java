package com.ericliudeveloper.sharedbillhelper.ui.presenter;

import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.ericliudeveloper.sharedbillhelper.R;
import com.ericliudeveloper.sharedbillhelper.model.Member;
import com.ericliudeveloper.sharedbillhelper.model.MemberDAO;
import com.ericliudeveloper.sharedbillhelper.util.CustomEvents;
import com.ericliudeveloper.sharedbillhelper.util.ResouceUtils;

import java.util.HashMap;

import de.greenrobot.event.EventBus;

/**
 * Created by liu on 8/06/15.
 */
public class MemberListPresenter implements ListPresenter {
    private boolean isListSelectionMode = false;

    public static HashMap<Long, Member> mSelection = new HashMap<>();


    public MemberListPresenter(boolean isSelectionMode) {
        isListSelectionMode = isSelectionMode;
    }

    @Override
    public RecyclerView.ViewHolder getCustomViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.member_row_layout, parent, false);
        return new MemberViewHolder(itemView, isListSelectionMode);
    }

    @Override
    public void setViewHolderData(RecyclerView.ViewHolder holder, Cursor cursor) {
        Member member = MemberDAO.getMemberFromCursor(cursor);
        if (member != null) {
            ((MemberViewHolder) holder).setItem(member);
        }
    }


    public static class MemberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
        private boolean isSelectionMode = false;

        private Member mMember;
        CardView cardView;
        TextView tvFirstName, tvLastName;
        public final CheckBox checkBox;

        public MemberViewHolder(View itemView, boolean isSelectionMode) {
            super(itemView);
            this.isSelectionMode = isSelectionMode;

            cardView = (CardView) itemView.findViewById(R.id.card_view);
            tvFirstName = (TextView) itemView.findViewById(R.id.tvFristName);
            tvLastName = (TextView) itemView.findViewById(R.id.tvLastName);
            checkBox = (CheckBox) itemView.findViewById(android.R.id.checkbox);

            itemView.setOnClickListener(this);
            checkBox.setOnCheckedChangeListener(this);

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

            checkBox.setChecked(mSelection.containsKey(member.getId()));
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

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                if (!mSelection.containsKey(mMember.getId())) {
                    mSelection.put(mMember.getId(), mMember);
                }
            } else {
                if (mSelection.containsKey(mMember.getId())) {
                    mSelection.remove(mMember.getId());
                }
            }
        }
    }
}
