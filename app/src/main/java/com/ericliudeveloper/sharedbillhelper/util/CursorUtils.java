package com.ericliudeveloper.sharedbillhelper.util;

import android.database.Cursor;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liu on 15/06/15.
 */
public final class CursorUtils {
    private CursorUtils(){}

    public static List<Long> getIdListFromCursor(Cursor cursor) {
        long id = -1;
        List<Long> idList = new ArrayList<>();

        if (cursor != null && cursor.moveToPosition(-1)) {
            while (cursor.moveToNext()) {
                id = cursor.getLong(cursor.getColumnIndexOrThrow(BaseColumns._ID));
                if (id >= 0) {
                    idList.add(id);
                }
            }
            return idList;
        }
        return null;
    }
}
