package com.ericliudeveloper.sharedbillhelper.util;

import java.text.DecimalFormat;

/**
 * Created by liu on 20/06/15.
 */
public class DigitUtils {
    static DecimalFormat dollarForum = new DecimalFormat("$###,###,###,###.##");


    public static String convertToDollarFormat(double amount) {
        return dollarForum.format(amount);
    }
}
