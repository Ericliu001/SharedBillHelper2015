package com.ericliudeveloper.sharedbillhelper.util;

import com.ericliudeveloper.sharedbillhelper.model.Bill;

import java.util.Date;

/**
 * Created by liu on 8/06/15.
 */
public final class CustomEvents {
    private CustomEvents() {
    }


    public static class EventDatePicked {
        public final Date date;

        public EventDatePicked(Date date) {
            this.date = date;
        }
    }


    public static class EventSendBill{
        public final Bill bill;

        public EventSendBill(Bill bill) {
            this.bill = bill;
        }
    }


    public static class EventWrongDatePicked{
    }
}
