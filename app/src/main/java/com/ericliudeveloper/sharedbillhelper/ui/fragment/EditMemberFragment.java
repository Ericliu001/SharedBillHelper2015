package com.ericliudeveloper.sharedbillhelper.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ericliudeveloper.sharedbillhelper.R;
import com.ericliudeveloper.sharedbillhelper.ui.presenter.EditMemberPresenter;

/**
 * Created by liu on 11/06/15.
 */
public class EditMemberFragment extends BaseFragment implements View.OnClickListener {

    EditMemberPresenter mPresenter = new EditMemberPresenter();


    private EditText etFirstName;
    private EditText etLastName;
    private EditText etPhone;
    private EditText etEmail;
    private TextView tvMoveInDate;
    private TextView tvMoveOutDate;
    private Button btPickMoveIndate;
    private Button btPickMoveOutdate;


    public EditMemberFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_edit_member, container, false);
        setupViews(root);
        return root;
    }

    private void setupViews(View rootView) {

        etFirstName = (EditText) rootView.findViewById(R.id.etFirstName);

        etLastName = (EditText) rootView.findViewById(R.id.etLastName);
        etPhone = (EditText) rootView.findViewById(R.id.etPhone);
        etEmail = (EditText) rootView.findViewById(R.id.etEmail);
        tvMoveInDate = (TextView) rootView.findViewById(R.id.tvMoveInDate);
        tvMoveOutDate = (TextView) rootView
                .findViewById(R.id.tvMoveOutDate);
        btPickMoveIndate = (Button) rootView
                .findViewById(R.id.btPickMoveInDate);
        btPickMoveOutdate = (Button) rootView
                .findViewById(R.id.btPickMoveOutDate);

        btPickMoveIndate.setOnClickListener(this);
        btPickMoveOutdate.setOnClickListener(this);

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_edit, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {


            case R.id.action_done:
//                mPresenter.startActionDone();
                return true;

            case R.id.action_cancel:
//                mPresenter.startActionCancel();
                return true;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

    }
}
