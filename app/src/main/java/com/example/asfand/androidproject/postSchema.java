package com.example.asfand.androidproject;

import android.provider.BaseColumns;

/**
 * Created by Abbas Nazar on 12/31/2017.
 */

public class postSchema
{
    private postSchema(){}

    public static class postEntry implements BaseColumns {
        public static final String TABLE_NAME = "posts";
        public static final String COLUMN_postid = "pid";
        public static final String COLUMN_userid = "uid";
        public static final String COLUMN_title = "title";
        public static final String COLUMN_category = "category";
        public static final String COLUMN_desc = "desc";
        public static final String COLUMN_email = "email";
        public static final String COLUMN_phone = "phone";
        public static final String COLUMN_time = "tim";
        public static final String COLUMN_Pname = "Pname";
    }
}
