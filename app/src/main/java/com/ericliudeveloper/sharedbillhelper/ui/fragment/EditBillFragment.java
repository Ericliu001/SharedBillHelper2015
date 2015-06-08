package com.ericliudeveloper.sharedbillhelper.ui.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.ericliudeveloper.sharedbillhelper.R;
import com.ericliudeveloper.sharedbillhelper.ui.presenter.EditBillPresenter;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditBillFragment extends BaseFragment implements View.OnClickListener {

    EditBillPresenter mPresenter;

    private Spinner spType;
    private EditText etAmount;
    private CheckBox cbPaid;
    private TextView tvStartDate;
    private TextView tvEndDate;
    private TextView tvDueDate;

    private Button btStartDate;
    private Button btEndDate;
    private Button btDueDate;

    public EditBillFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new EditBillPresenter(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_edit_bill, container, false);
        setupViews(root);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.registerEventbusListener();
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.unregisterEventbusListener();
    }

    private void setupViews(View rootView) {

        spType = (Spinner) rootView.findViewById(R.id.spType);
        etAmount = (EditText) rootView.findViewById(R.id.etAmount);
        cbPaid = (CheckBox) rootView.findViewById(R.id.cbPaid);
        tvStartDate = (TextView) rootView.findViewById(R.id.tvStartDate);
        tvEndDate = (TextView) rootView.findViewById(R.id.tvEndDate);
        tvDueDate = (TextView) rootView.findViewById(R.id.tvDueDate);
        btStartDate = (Button) rootView.findViewById(R.id.btStartDate);
        btEndDate = (Button) rootView.findViewById(R.id.btEndDate);
        btDueDate = (Button) rootView.findViewById(R.id.btDueDate);

        btStartDate.setOnClickListener(this);
        btEndDate.setOnClickListener(this);
        btDueDate.setOnClickListener(this);

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(
                getActivity(), android.R.layout.simple_spinner_item,
                getResources().getStringArray(
                        R.array.bill_type_spinner_items));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spType.setAdapter(adapter);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btStartDate:
                mPresenter.startDateButtonClicked();
                break;

            case R.id.btEndDate:
                mPresenter.endDateButtonClicked();
                break;

            case R.id.btDueDate:
                mPresenter.dueDateButtonClicked();
                break;


            default:
                break;
        }

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_edit_bill, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {


            case R.id.action_done:
                mPresenter.startActionDone();
                return true;

            case R.id.action_cancel:
                mPresenter.startActionCancel();
                return true;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
