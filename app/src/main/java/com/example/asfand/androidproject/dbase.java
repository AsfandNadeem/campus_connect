package com.example.asfand.androidproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Abbas Nazar on 12/31/2017.
 */

public class dbase
{
    SQLiteDatabase db;
    postdbhelper mDbHelper;

    public dbase(Context context)
    {
        mDbHelper = new postdbhelper(context);
        db = mDbHelper.getWritableDatabase();
    }

    public void dataInsert(ContentValues values)
    {
        //db.delete(postSchema.postEntry.TABLE_NAME,null,null);
        long newRowId = db.insert(postSchema.postEntry.TABLE_NAME, null, values);
    }

    public Cursor dataRead()
    {
        String[] projection = {
                postSchema.postEntry.COLUMN_userid,
                postSchema.postEntry.COLUMN_postid,
                postSchema.postEntry.COLUMN_email,
                postSchema.postEntry.COLUMN_phone,
                postSchema.postEntry.COLUMN_desc,
                postSchema.postEntry.COLUMN_category,
                postSchema.postEntry.COLUMN_title,
                postSchema.postEntry.COLUMN_time,
                postSchema.postEntry.COLUMN_Pname
        };

/*// Filter results WHERE "title" = 'My Title'
        String selection = FeedEntry.COLUMN_NAME_TITLE + " = ?";
        String[] selectionArgs = { "My Title" };*/

// How you want the results sorted in the resulting Cursor
//        String sortOrder =
//                postSchema.postEntry.COLUMN_time + " DESC";

        Cursor cursor = db.query(
                postSchema.postEntry.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );
    return cursor;
    }
}
