package com.example.asfand.androidproject;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;

public class PostInfo extends AppCompatActivity {
    TextView desc,title,name,phone,email;
    dbase DB;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_info);
        Intent a=getIntent();
      id =a.getStringExtra("1234");
        Log.d("post",id);
        desc=(TextView) findViewById(R.id.infoDesc);
        title=(TextView) findViewById(R.id.infoTitle);
        name=(TextView) findViewById(R.id.infoBanda);
        phone=(TextView) findViewById(R.id.infoPhone);
        email=(TextView) findViewById(R.id.infoEmail);
        DB=new dbase(getApplicationContext());

        Cursor cursor1 = DB.dataReadInfo(id);
        while (cursor1.moveToNext()) {

            desc.setText(cursor1.getString(cursor1.getColumnIndexOrThrow(postSchema.postEntry.COLUMN_desc)));
            desc.setMovementMethod(new ScrollingMovementMethod());
            title.setText(cursor1.getString(cursor1.getColumnIndexOrThrow(postSchema.postEntry.COLUMN_title)));
            name.setText(cursor1.getString(cursor1.getColumnIndexOrThrow(postSchema.postEntry.COLUMN_Pname)));
            phone.setText(cursor1.getString(cursor1.getColumnIndexOrThrow(postSchema.postEntry.COLUMN_phone)));
            email.setText(cursor1.getString(cursor1.getColumnIndexOrThrow(postSchema.postEntry.COLUMN_email)));

        }
        cursor1.close();


    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
