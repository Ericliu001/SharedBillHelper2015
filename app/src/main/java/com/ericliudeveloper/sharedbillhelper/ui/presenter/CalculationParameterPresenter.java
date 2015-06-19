package com.ericliudeveloper.sharedbillhelper.ui.presenter;

/**
 * Created by liu on 15/06/15.
 */
public class CalculationParameterPresenter {
    private CalculationParameterFace mCallbacks;


    public CalculationParameterPresenter(CalculationParameterFace callbacks) {
        this.mCallbacks = callbacks;
    }



    public void onActionCalculate() {
        if (BillListPresenter.mSelection.size() == 0 || MemberListPresenter.mSelection.size() == 0) {
            // todo: show error msg
            return;
        }

        // todo checked member unhandled
        mCallbacks.startCalculationResultActivity();
    }

    public interface CalculationParameterFace {
        void startCalculationResultActivity();
    }
}
