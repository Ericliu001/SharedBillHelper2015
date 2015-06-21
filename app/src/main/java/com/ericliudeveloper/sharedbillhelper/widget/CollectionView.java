package com.ericliudeveloper.sharedbillhelper.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

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
        private List<Header<T1>> mHeaderList = new ArrayList<>();
        private List<T2> mDataList = new ArrayList<>();
        private int[] headerInsertPositionList;
        private int[] headerListPositionList;


        public Inventory(List headerList, List dataList) {
            this.mHeaderList = headerList;
            this.mDataList = dataList;
            headerInsertPositionList = new int[mHeaderList.size()];
            headerListPositionList = new int[mHeaderList.size()];

            int index1 = 0;
            for (Header<T1> header : mHeaderList) {
                headerInsertPositionList[index1] = header.headerInsertingPosition;
                index1 ++;
            }

            for (int i = 0; i < headerInsertPositionList.length; i++) {
                headerListPositionList[i] = headerInsertPositionList[i] + i;
            }

        }



        public void addHeader(Header header) {
            mHeaderList.add(header);
        }

        public int getTotalItemCount() {
            int total = 0;
            total = mHeaderList.size() + mDataList.size();
            return total;
        }

        public int getHeaderCount() {
            return mHeaderList.size();
        }

        public int getHeaderPositionInList(int index) {
            return headerListPositionList[index];
        }

        public int getHeaderIndex(int position) {
            return indexOf(headerListPositionList, position);
        }




        public int getDataIndex(int position) {
            int afterHeader = 0;
            while (afterHeader < headerListPositionList.length  && headerListPositionList[afterHeader] < position) {
                afterHeader ++;
            }
            return  position - afterHeader;
        }

    }

    private static int indexOf(int[] array, int position) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == position) {
                return i;
            }
        }
        return -1;
    }

    private static boolean contains(int[] array, int position) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == position) {
                return true;
            }
        }
        return false;
    }


    public static class Header<T1> {

        public int headerInsertingPosition;
        public T1 t;

        public Header(int insertingPosition, T1 t) {
            this.headerInsertingPosition = insertingPosition;
            this.t = t;
        }
    }


    public static int ROW_TYPE_HEADER = 0;
    public static int ROW_TYPE_DATA = 1;

    protected class MyListAdapter extends RecyclerView.Adapter {


        @Override
        public int getItemViewType(int position) {
            return contains(mInventory.headerListPositionList, position)
                    ? ROW_TYPE_HEADER
                    : ROW_TYPE_DATA;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ViewHolder holder;
            if (viewType == ROW_TYPE_HEADER) {
              return   mCallback.createHeaderViewHolder(parent);
            } else if (viewType == ROW_TYPE_DATA) {
              return   mCallback.createDataViewHolder(parent);
            }
            return null;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (contains(mInventory.headerListPositionList, position)) {
                int headerIndex = indexOf(mInventory.headerListPositionList, position);
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


        ViewHolder createHeaderViewHolder(ViewGroup parent);

        ViewHolder createDataViewHolder(ViewGroup parent);

        void bindHeaderViewHolder(ViewHolder holder, int headerIndex);

        void bindDataViewHolder(ViewHolder holder, int dataIndex);
    }
}
