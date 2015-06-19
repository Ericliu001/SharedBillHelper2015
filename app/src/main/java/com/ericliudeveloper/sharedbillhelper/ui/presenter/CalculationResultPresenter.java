package com.ericliudeveloper.sharedbillhelper.ui.presenter;

import com.ericliudeveloper.sharedbillhelper.model.PaymentInfo;

import java.util.HashMap;

/**
 * Created by liu on 19/06/15.
 */
public class CalculationResultPresenter {
    HashMap billSelections = BillListPresenter.mSelection;
    HashMap memberSelections = MemberListPresenter.mSelection;

    public void Calculate(){
        PaymentInfo.Builder infoBuilder = new PaymentInfo.Builder();

    }
}
