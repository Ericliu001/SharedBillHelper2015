package com.ericliudeveloper.sharedbillhelper.ui.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import com.ericliudeveloper.sharedbillhelper.R;

/**
 * Created by liu on 14/06/15.
 */
public class CalculationParameterActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculation_parameter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate( R.menu.menu_calcuation, menu);
        return true;
    }
}
