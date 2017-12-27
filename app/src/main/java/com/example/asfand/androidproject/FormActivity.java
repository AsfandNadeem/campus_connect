package com.example.asfand.androidproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FormActivity extends AppCompatActivity {
    EditText title,des,phone,email;
    Spinner sp;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    SimpleDateFormat sdf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        title=(EditText)findViewById(R.id.actTitile);
        des=(EditText)findViewById(R.id.actDescription);
        phone=(EditText)findViewById(R.id.actPhone);
        email=(EditText)findViewById(R.id.actEmail);
        sdf=new SimpleDateFormat("EEEE,h:mm");
    }

    public void create(View view)
    {
        DatabaseReference myRef = database.getReference("post");
        myRef=myRef.child("abc-"+title.getText().toString());
        myRef.child("title").setValue(title.getText().toString());
        myRef.child("banda").setValue("abc");
        myRef.child("time").setValue(sdf.format(Calendar.getInstance().getTime()));
        myRef.child("desc").setValue(des.getText().toString());
        myRef.child("phone").setValue(phone.getText().toString());
        myRef.child("email").setValue(email.getText().toString());
    }
}
