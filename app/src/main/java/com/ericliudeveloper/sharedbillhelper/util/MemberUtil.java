package com.ericliudeveloper.sharedbillhelper.util;

import android.text.TextUtils;

import com.ericliudeveloper.sharedbillhelper.model.Member;

/**
 * Created by liu on 21/06/15.
 */
public class MemberUtil {

    public static String getFullNameString(Member member) {
        String payeeFullName = member.getFirstName();
        String lastName = member.getLastName();
        if (!TextUtils.isEmpty(lastName)) {
            payeeFullName = payeeFullName + " " + lastName;
        }
        return payeeFullName;
    }
}
