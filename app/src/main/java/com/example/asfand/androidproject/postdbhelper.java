package com.example.asfand.androidproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Abbas Nazar on 12/31/2017.
 */

public class postdbhelper extends SQLiteOpenHelper
{
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + postSchema.postEntry.TABLE_NAME + " (" +
                    postSchema.postEntry.COLUMN_postid + " VARCHAR PRIMARY KEY," +
                    postSchema.postEntry.COLUMN_title + " TEXT," +
                    postSchema.postEntry.COLUMN_desc + " TEXT," +
                    postSchema.postEntry.COLUMN_email + " TEXT," +
                    postSchema.postEntry.COLUMN_phone + " TEXT," +
                    postSchema.postEntry.COLUMN_userid + " TEXT," +
                    postSchema.postEntry.COLUMN_category+ " TEXT,"+
                    postSchema.postEntry.COLUMN_time+ " TEXT,"+
                    postSchema.postEntry.COLUMN_Pname+ " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + postSchema.postEntry.TABLE_NAME;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "po.db";

    public postdbhelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }

    public void delete()
    {

    }
}
