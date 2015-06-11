package com.ericliudeveloper.sharedbillhelper.util;

import com.ericliudeveloper.sharedbillhelper.model.Bill;
import com.ericliudeveloper.sharedbillhelper.model.Member;

import java.util.Date;

/**
 * Created by liu on 8/06/15.
 */
public final class CustomEvents {
    private CustomEvents() {
    }

    // class to pass pick date event from the date pick dialog
    public static class EventDatePicked {
        public final Date date;

        public EventDatePicked(Date date) {
            this.date = date;
        }
    }


    // class to pass positive click from the dialog shown after the user clicks delete bill
    public static class EventDeleteBill {
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
