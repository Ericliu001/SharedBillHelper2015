package com.ericliudeveloper.sharedbillhelper.ui.presenter;

import com.ericliudeveloper.sharedbillhelper.model.Bill;
import com.ericliudeveloper.sharedbillhelper.model.Member;
import com.ericliudeveloper.sharedbillhelper.util.CustomEvents;

import java.util.HashMap;

import de.greenrobot.event.EventBus;

/**
 * Created by liu on 15/06/15.
 */
public class CalculationParameterPresenter {
    private HashMap<Long, Bill> checkedBills = new HashMap();
    private HashMap<Long, Member> checkedMembers = new HashMap<>();


    public void registerEventBusListener() {
        EventBus.getDefault().register(this);
    }


    public void onEvent(CustomEvents.EventBillChecked eventBillChecked) {
        Bill bill = eventBillChecked.bill;
        long id = bill.getId();
        if (!checkedBills.containsKey(id)) {
            checkedBills.put(id, bill);
        }
    }

    public void onEvent(CustomEvents.EventBillUnchecked eventBillUnchecked) {
        Bill bill = eventBillUnchecked.bill;
        long id = bill.getId();
        if (checkedBills.containsKey(id)) {
            checkedBills.remove(id);
        }
    }


    public void onEvent(CustomEvents.EventMemberChecked eventMemberChecked) {
        Member member = eventMemberChecked.member;
        long id = member.getId();
        if (!checkedMembers.containsKey(id)) {
            checkedMembers.put(id, member);
        }

    }

    public void onEvent(CustomEvents.EventMemberUnchecked eventMemberUnchecked) {
        Member member = eventMemberUnchecked.member;
        long id = member.getId();
        if (checkedMembers.containsKey(id)) {
            checkedMembers.remove(id);
        }
    }

    public void unregisterEventBusListener() {
        EventBus.getDefault().unregister(this);
    }

    public void onActionCalculate() {
        if (checkedMembers.size() == 0 || checkedBills.size() == 0) {
            // todo: show error msg
            return;
        }

        for (Bill bill : checkedBills.values()) {

        }
    }
}
