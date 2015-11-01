package com.ericliudeveloper.sharedbillhelper.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ericliudeveloper.sharedbillhelper.R;
import com.ericliudeveloper.sharedbillhelper.ui.dialog.MessageDialog;
import com.ericliudeveloper.sharedbillhelper.ui.presenter.EditMemberPresenter;

/**
 * Created by liu on 11/06/15.
 */
public class EditMemberFragment extends BaseFragment implements View.OnClickListener, EditMemberPresenter.EditMemberFace {

    EditMemberPresenter mPresenter = new EditMemberPresenter(this);


    private EditText etFirstName;
    private EditText etLastName;
    private EditText etPhone;
    private EditText etEmail;
    private Button btPickMoveIndate;
    private Button btPickMoveOutdate;


    public EditMemberFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mPresenter.setActivity(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_edit_member, container, false);
        setupViews(root);
        mPresenter.refreshDisplayFromMemberInstance();
        return root;
    }

    private void setupViews(View rootView) {

        etFirstName = (EditText) rootView.findViewById(R.id.etFirstName);

        etLastName = (EditText) rootView.findViewById(R.id.etLastName);
        etPhone = (EditText) rootView.findViewById(R.id.etPhone);
        etEmail = (EditText) rootView.findViewById(R.id.etEmail);
        btPickMoveIndate = (Button) rootView
                .findViewById(R.id.btPickMoveInDate);
        btPickMoveOutdate = (Button) rootView
                .findViewById(R.id.btPickMoveOutDate);

        btPickMoveIndate.setOnClickListener(this);
        btPickMoveOutdate.setOnClickListener(this);

    }


    @Override
    public void onStart() {
        super.onStart();
        mPresenter.registerStickyEventBusListener();
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.unregisterEventBusListener();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_edit_member, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_pick_contact:
                mPresenter.startActionPickContact();
                return true;
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

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btPickMoveInDate:
                mPresenter.onPickMoveInDateButtonClicked();
                break;

            case R.id.btPickMoveOutDate:
                mPresenter.onPickMoveOutDateButtonClicked();
                break;

            default:
                break;
        }
    }

    @Override
    public void showPickedMoveInDate(String pickedDate) {
        btPickMoveIndate.setText(pickedDate);
    }

    @Override
    public void showPickedMoveOutDate(String pickedDate) {
        btPickMoveOutdate.setText(pickedDate);
    }

    @Override
    public void showMemberFirstName(String firstName) {
        etFirstName.setText(firstName);
    }

    @Override
    public void showMemberLastName(String lastName) {
        etLastName.setText(lastName);
    }

    @Override
    public void showMemberPhoneNumber(String phone) {
        if (!TextUtils.isEmpty(phone)) {
            etPhone.setText(phone);
        }
    }

    @Override
    public void showMemberEmail(String email) {
        etEmail.setText(email);
    }

    @Override
    public String getFirstNameInput() {
        return etFirstName.getText().toString();
    }

    @Override
    public String getLastNameInput() {
        return etLastName.getText().toString();
    }

    @Override
    public String getPhoneNumberInput() {
        return etPhone.getText().toString();
    }

    @Override
    public String getEmailInput() {
        return etEmail.getText().toString();
    }

    @Override
    public void showErroDialog() {
        Bundle args = new Bundle();
        String title = getActivity().getResources()
                .getString(R.string.fields_empty);
        args.putString(MessageDialog.TITLE, title);

        String must_fill_fields_member = getActivity().getResources()
                .getString(R.string.must_fill_fields_member);
        args.putString(MessageDialog.MESSAGE, must_fill_fields_member);
        MessageDialog messageDialog = MessageDialog
                .newInstance(args);
        messageDialog.show(getActivity().getFragmentManager(), "message");
    }
}
