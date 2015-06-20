package com.ericliudeveloper.sharedbillhelper.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by liu on 20/06/15.
 */
public class CollectionView extends RecyclerView {
    Inventory mInventory;
    CollectionViewCallbacks mCallback;

    public CollectionView(Context context) {
        super(context);
    }

    public CollectionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CollectionView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    public void setDataAndCallback(Inventory inventory, CollectionViewCallbacks callbacks) {
        mInventory = inventory;
        mCallback = callbacks;

        setAdapter(new MyListAdapter());
    }


    public static class Inventory<T1, T2> {
        private ArrayList<Header<T1>> headerList = new ArrayList<>();
        private ArrayList<T2> dataList = new ArrayList<>();
        private ArrayList<Integer> headerInsertPositionList = new ArrayList<>();
        private ArrayList<Integer> headerListPositionList = new ArrayList<>();


        public Inventory(Inventory<T1, T2> copyFrom) {
            for (Header<T1> header : copyFrom.headerList) {
                headerList.add(header);
                headerInsertPositionList.add(header.headerInsertingPosition);
            }


            for (T2 t2 : copyFrom.dataList) {
                dataList.add(t2);
            }

            for (int i = 0; i < headerInsertPositionList.size(); i++) {
                headerListPositionList.add(headerInsertPositionList.get(i) + i);
            }
        }


        public void addHeader(Header header) {
            headerList.add(header);
        }

        public int getTotalItemCount() {
            int total = 0;
            total = headerList.size() + dataList.size();
            return total;
        }

        public int getHeaderCount() {
            return headerList.size();
        }

        public int getHeaderPositionInList(int index) {
            return headerListPositionList.get(index);
        }

        public int getHeaderIndex(int position) {
            return headerListPositionList.indexOf(position);
        }

        public int getDataIndex(int position) {
            int afterHeader = 0;
            while (headerListPositionList.get(afterHeader) < position) {
                afterHeader ++;
            }

            return  position - afterHeader;
        }

    }


    public static class Header<T1> {

        public int headerInsertingPosition;
        public T1 t;

        public Header(int insertingPosition, T1 t) {
            this.headerInsertingPosition = insertingPosition;
            this.t = t;
        }
    }


    public static int ROW_TYPE_HEADER = 1;
    public static int ROW_TYPE_DATA = 1;

    protected class MyListAdapter extends RecyclerView.Adapter {


        @Override
        public int getItemViewType(int position) {
            return mInventory.headerListPositionList.contains(position)
                    ? ROW_TYPE_HEADER
                    : ROW_TYPE_DATA;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ViewHolder holder;
            if (viewType == ROW_TYPE_HEADER) {
                mCallback.createHeaderViewHolder(parent);
            } else if (viewType == ROW_TYPE_DATA) {
                mCallback.createDataViewHolder(parent);
            }
            return null;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (mInventory.headerListPositionList.contains(position)) {
                int headerIndex = mInventory.headerListPositionList.indexOf(position);
                mCallback.bindHeaderViewHolder(holder, headerIndex);
            } else {
                int dataIndex = mInventory.getDataIndex(position);
                mCallback.bindDataViewHolder(holder, dataIndex);
            }
        }

        @Override
        public int getItemCount() {
            return mInventory.getTotalItemCount();
        }
    }


    public interface CollectionViewCallbacks {

        void onBindViewHolder(ViewHolder holder, int position);

        void createHeaderViewHolder(ViewGroup parent);

        void createDataViewHolder(ViewGroup parent);

        void bindHeaderViewHolder(ViewHolder holder, int headerIndex);

        void bindDataViewHolder(ViewHolder holder, int dataIndex);
    }
}
