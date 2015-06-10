package com.ericliudeveloper.sharedbillhelper.ui.activity;

import android.app.Fragment;
import android.view.MenuItem;

import com.ericliudeveloper.sharedbillhelper.R;
import com.ericliudeveloper.sharedbillhelper.ui.fragment.EditBillFragment;

public class EditBillActivity extends ContainerActivity {


    @Override
    protected Fragment getFragment() {
        return new EditBillFragment();
    }

    @Override
    protected String getFragmentTag() {
        return getClass().getName();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {


            case R.id.action_done:
                finish();
                break;

            case R.id.action_cancel:
                finish();
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
