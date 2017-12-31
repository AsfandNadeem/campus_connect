package com.example.asfand.androidproject;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MyPosts extends AppCompatActivity {
    ListView myListView;
    ArrayList<ListShow> myRowItems;
    CustomAdapter myAdapter;
    dbase DB;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_posts);
        myListView=(ListView)findViewById(R.id.myposts);
        myRowItems=new ArrayList<ListShow>();
        myAdapter=new CustomAdapter(getApplicationContext(),myRowItems);
        myListView.setAdapter(myAdapter);
        sh= PreferenceManager.getDefaultSharedPreferences(this);
        DB = new dbase(getApplicationContext());
        fillarraylist();
    }
    public void fillarraylist(){
        Cursor cursor=DB.dataReadMyPosts(sh.getString("ID",""));

        while (cursor.moveToNext()) {
            ListShow row_two = new ListShow();

            row_two.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(postSchema.postEntry.COLUMN_category)));
            row_two.setDate(cursor.getString(cursor.getColumnIndexOrThrow(postSchema.postEntry.COLUMN_time)));
            row_two.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(postSchema.postEntry.COLUMN_title)));
            row_two.setName(cursor.getString(cursor.getColumnIndexOrThrow(postSchema.postEntry.COLUMN_Pname)));
            //Log.d("abcd",""+childd.child("time").getValue(String.class).toString()+ i);
            myRowItems.add(row_two);
        }
        cursor.close();
        myAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
