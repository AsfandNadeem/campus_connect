package com.example.asfand.androidproject;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class FormActivity extends AppCompatActivity {
    EditText title,des,phone,email;
    Spinner sp;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    SimpleDateFormat sdf;
    String department= "department";
    String category;
    SharedPreferences list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        list = PreferenceManager.getDefaultSharedPreferences(this);
        title=(EditText)findViewById(R.id.actTitile);
        des=(EditText)findViewById(R.id.actDescription);
        phone=(EditText)findViewById(R.id.actPhone);
        email=(EditText)findViewById(R.id.actEmail);
        sdf=new SimpleDateFormat("EEEE,h:mm");
        sp = (Spinner) findViewById(R.id.actspinner);

       String[] categoryList= {"General",list.getString(department,"") };

// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, categoryList);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        sp.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    public void create(View view)
    {
//        DatabaseReference myRef = database.getReference("post");
//        myRef=myRef.child("abc-"+title.getText().toString());
//        myRef.child("title").setValue(title.getText().toString());
//        myRef.child("banda").setValue("abc");
//        myRef.child("time").setValue(sdf.format(Calendar.getInstance().getTime()));
//        myRef.child("desc").setValue(des.getText().toString());
//        myRef.child("phone").setValue(phone.getText().toString());
//        myRef.child("email").setValue(email.getText().toString());
//        myRef.child("category").setValue(sp.getSelectedItem().toString());
        category=sp.getSelectedItem().toString();

            DatabaseReference myRef = database.getReference("post");
            DatabaseReference u=myRef.child(category);

            u=u.child("abc-"+title.getText().toString());
            u.child("title").setValue(title.getText().toString());
            u.child("banda").setValue("abc");
            u.child("time").setValue(sdf.format(Calendar.getInstance().getTime()));
            u.child("desc").setValue(des.getText().toString());
            u.child("phone").setValue(phone.getText().toString());
            u.child("email").setValue(email.getText().toString());
            u.child("category").setValue(sp.getSelectedItem().toString());


        DatabaseReference myRefA = database.getReference("post");
        myRefA=myRefA.child("abc-"+title.getText().toString());
        myRefA.child("title").setValue(title.getText().toString());
        myRefA.child("banda").setValue("abc");
        myRefA.child("time").setValue(sdf.format(Calendar.getInstance().getTime()));
        myRefA.child("desc").setValue(des.getText().toString());
        myRefA.child("phone").setValue(phone.getText().toString());
        myRefA.child("email").setValue(email.getText().toString());
        myRefA.child("category").setValue(sp.getSelectedItem().toString());

        finish();
    }

    public void cancleClick(View view)
    {
                finish();
    }
}
