package com.ericliudeveloper.sharedbillhelper.model;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;

import com.ericliudeveloper.sharedbillhelper.MyApplication;
import com.ericliudeveloper.sharedbillhelper.database.DatabaseConstants;
import com.ericliudeveloper.sharedbillhelper.provider.BillContract;

/**
 * Created by liu on 8/06/15.
 */
public class MemberDAO implements Dao {

    static ContentResolver mContentResolver = MyApplication.getAppContentResolver();
    static String[] projection = BillContract.Members.PROJECTION;
    static Uri membersUri = BillContract.Members.CONTENT_URI;


    public MemberDAO() {
        mContentResolver = MyApplication.getAppContentResolver();
    }

    /**
     * Create a Member instance from a cursor, assumed the cursor is retrieved from DB thus id field is not null.
     * Be careful: you must position the cursor before calling this method or it will throw CursorIndexOutOfBoundsException
     * @param cursor
     * @return
     */
    public static Member getMemberFromCursor(Cursor cursor) {
        if (cursor != null) {
            long membrId = cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseConstants.MemberColumns.COL_ROWID));
            Member member = new Member(membrId);
            member.setFirstName(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.MemberColumns.COL_FIRSTNAME)));
            member.setLastName(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.MemberColumns.COL_LASTNAME)));
            member.setPhone(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.MemberColumns.COL_PHONE)));
            member.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.MemberColumns.COL_EMAIL)));
            member.setMoveInDate(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.MemberColumns.COL_MOVE_IN_DATE)));
            member.setMoveOutDate(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.MemberColumns.COL_MOVE_OUT_DATE)));
            member.setDeleted(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseConstants.MemberColumns.COL_DELETED)));

            return member;
        }

        return null;
    }

    /**
     * generate ContentValues from a Member instance, the id field is ignored.
     *
     * @param member
     * @return
     */
    public static ContentValues getContentValuesFromMemberInstance(Member member) {
        if (member == null) {
            return null;
        }
        ContentValues values = new ContentValues();
        values.put(DatabaseConstants.MemberColumns.COL_FIRSTNAME, member.getFirstName());
        values.put(DatabaseConstants.MemberColumns.COL_LASTNAME, member.getLastName());
        values.put(DatabaseConstants.MemberColumns.COL_PHONE, member.getPhone());
        values.put(DatabaseConstants.MemberColumns.COL_EMAIL, member.getEmail());
        values.put(DatabaseConstants.MemberColumns.COL_MOVE_IN_DATE, member.getMoveInDate());
        values.put(DatabaseConstants.MemberColumns.COL_MOVE_OUT_DATE, member.getMoveOutDate());
        values.put(DatabaseConstants.MemberColumns.COL_DELETED, member.getDeleted());
        return values;
    }

    public static void saveMember(Member member, final Handler handler) {
        long id = member.getId();
        final ContentValues values = getContentValuesFromMemberInstance(member);

        if (id >= 0) {
            // This is an update
            final Uri memberUriWithId = BillContract.Members.buildMemberUri(String.valueOf(id));

            new AsyncQueryHandler(mContentResolver) {
            }.startUpdate(0, null, memberUriWithId, values, null, null);

            return;
        }

        AsyncQueryHandler insertMemberHandler = new AsyncQueryHandler(mContentResolver) {
            @Override
            protected void onInsertComplete(int token, Object cookie, Uri uri) {
                if (handler != null) {
                    Message message = Message.obtain();
                    message.what = MESSAGE_WHAT_SAVED_MEMBER_URL;
                    message.obj = uri;
                    handler.sendMessage(message);
                }
            }
        };

        insertMemberHandler.startInsert(0, null, membersUri, values);
    }
}
