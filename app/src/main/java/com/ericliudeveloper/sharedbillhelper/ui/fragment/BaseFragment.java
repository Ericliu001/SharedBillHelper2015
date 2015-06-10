package com.ericliudeveloper.sharedbillhelper.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.ericliudeveloper.sharedbillhelper.R;

/**
 * Created by liu on 8/06/15.
 */
public class BaseFragment extends Fragment {
    protected Toolbar mToolbar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
//        setRetainInstance(true); // causes crashes, wait for google to fix it

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);

        if (mToolbar != null) {

            AppCompatActivity activity = ((AppCompatActivity) getActivity());
            activity.setSupportActionBar(mToolbar);
            activity.getSupportActionBar().setHomeButtonEnabled(true);
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            switch (id) {
                case android.R.id.home:
//                    getActivity().finish(); //Never do this!!! getActivity may return null when the Fragment is detached
                    break;
                default:
                    break;
            }
        return super.onOptionsItemSelected(item);
    }
}
