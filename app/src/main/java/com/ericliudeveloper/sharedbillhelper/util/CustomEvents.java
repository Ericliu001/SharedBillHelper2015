package com.ericliudeveloper.sharedbillhelper.util;

import com.ericliudeveloper.sharedbillhelper.model.Bill;
import com.ericliudeveloper.sharedbillhelper.model.Member;
import com.ericliudeveloper.sharedbillhelper.model.Payment;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by liu on 8/06/15.
 */
public final class CustomEvents {
    private CustomEvents() {
    }



    public static class EventCalculationFinished{
        public final List<Payment> paymentList;

        public EventCalculationFinished(List<Payment> paymentList) {
            this.paymentList = paymentList;
        }
    }


    public static class EventStartCalculating{
        public final HashMap<Long, Bill> checkedBills;
        public final HashMap<Long, Member> checkedMembers;

        public EventStartCalculating(HashMap<Long, Bill> bills, HashMap<Long, Member> members) {
            this.checkedBills = bills;
            this.checkedMembers = members;
        }
    }







    // class to pass event when the user want to set calculation parameters,
    // when user clicks Float Action Button from a view bill page, that bill shall be selected
    // under CalculationParameters View
    public static class EventSetCalculationParameters {
        public final Bill bill;

        public EventSetCalculationParameters(Bill bill) {
            this.bill = bill;
        }

        public Bill getData() {
            return bill;
        }
    }


    // FloatActionBar clicked
    public static class EventFABClicked {
    }

    // class to pass pick date event from the date pick dialog
    public static class EventDatePicked {
        public final Date date;

        public EventDatePicked(Date date) {
            this.date = date;
        }
    }


    // class to pass positive click from the warning dialog shown after the user clicks delete
    public static class EventActionDelete {
    }


    // class to pass event when the user need to re-pick the date
    public static class EventWrongDatePicked {
    }


    public static class EventViewBill {
        public final Bill bill;

        public EventViewBill(Bill bill) {
            this.bill = bill;
        }
    }

    public static class EventEditBill {
        public final Bill bill;

        public EventEditBill(Bill bill) {
            this.bill = bill;
        }
    }

    public static class EventViewMember {
        public final Member member;

        public EventViewMember(Member member) {
            this.member = member;
        }
    }


    public static class EventEditMember {
        public final Member member;

        public EventEditMember(Member member) {
            this.member = member;
        }
    }


}
