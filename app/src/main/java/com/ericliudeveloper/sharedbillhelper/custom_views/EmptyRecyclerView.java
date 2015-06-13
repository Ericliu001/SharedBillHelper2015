package com.ericliudeveloper.sharedbillhelper.custom_views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by liu on 13/06/15.
 */
public class EmptyRecyclerView extends RecyclerView {
    private final AdapterDataObserver mObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            checkEmptyState();
        }
    } ;


    /**
     * View to show if there are no items to show.
     */
    private View mEmptyView;

    public EmptyRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public EmptyRecyclerView(Context context) {
        super(context);
    }

    public EmptyRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public void setmEmptyView(View emptyView) {
        mEmptyView = emptyView;
        checkEmptyState();
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setEmptyView(View emptyView) {
        mEmptyView = emptyView;
        // If not explicitly specified this view is important for accessibility.
        if (emptyView != null
                && emptyView.getImportantForAccessibility() == IMPORTANT_FOR_ACCESSIBILITY_AUTO) {
            emptyView.setImportantForAccessibility(IMPORTANT_FOR_ACCESSIBILITY_YES);
        }
        checkEmptyState();
    }

    private void checkEmptyState() {
        final Adapter adapter = getAdapter();
        final boolean empty = ((adapter == null) || (adapter.getItemCount() == 0));
        updateEmptyStatus(empty);
    }



    /**
     * When the current adapter is empty, the AdapterView can display a special view
     * called the empty view. The empty view is used to provide feedback to the user
     * that no data is available in this AdapterView.
     *
     * @return The view to show if the adapter is empty.
     */
    public View getEmptyView() {
        return mEmptyView;
    }


    /**
     * Update the status of the list based on the empty parameter.  If empty is true and
     * we have an empty view, display it.  In all the other cases, make sure that the listview
     * is VISIBLE and that the empty view is GONE (if it's not null).
     */
    private void updateEmptyStatus(boolean empty) {
//        if (isInFilterMode()) {
//            empty = false;
//        }
        if (empty) {
            if (mEmptyView != null) {
                mEmptyView.setVisibility(View.VISIBLE);
                setVisibility(View.GONE);
            } else {
                // If the caller just removed our empty view, make sure the list view is visible
                setVisibility(View.VISIBLE);
            }
            // We are now GONE, so pending layouts will not be dispatched.
            // Force one here to make sure that the state of the list matches
            // the state of the adapter.
//            if (mDataChanged) {
//                this.onLayout(false, mLeft, mTop, mRight, mBottom);
//            }
        } else {
            if (mEmptyView != null) mEmptyView.setVisibility(View.GONE);
            setVisibility(View.VISIBLE);
        }
    }





    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        final Adapter oldAdapter = getAdapter();

        if (oldAdapter != null) {
            oldAdapter.unregisterAdapterDataObserver(mObserver);
        }

        if (adapter != null) {
            adapter.registerAdapterDataObserver(mObserver);
        }
    }


    private class RecyclerViewDataObserver extends AdapterDataObserver {

    }
}
