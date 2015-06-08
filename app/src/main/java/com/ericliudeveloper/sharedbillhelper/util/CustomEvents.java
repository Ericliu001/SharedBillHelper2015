package com.ericliudeveloper.sharedbillhelper.util;

import java.util.Date;

/**
 * Created by liu on 8/06/15.
 */
public final class CustomEvents {
    private CustomEvents() {
    }


    public static class DatePickedEvent {
        public final Date date;

        public DatePickedEvent(Date date) {
            this.date = date;
        }
    }
}
