package com.example.asfand.androidproject;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {
    private FirebaseAuth mAuth;
    // ...
    private EditText email;
    private EditText name;
    private EditText password;
    private EditText confirmPass;
    private Spinner spinner;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        email = (EditText) findViewById(R.id.emailText);
        name = (EditText) findViewById(R.id.nameText);
        password = (EditText) findViewById(R.id.passText);
        confirmPass = (EditText) findViewById(R.id.conPassText);
        spinner = (Spinner) findViewById(R.id.spinner);


    }

    public void signUp(View view) {
        String i = spinner.getSelectedItem().toString();
        String ema = email.getText().toString();
        String nam = name.getText().toString();
        String pass = password.getText().toString();
        String conpass = confirmPass.getText().toString();

        if (email.getText().toString().isEmpty()|| name.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please fill required fields", Toast.LENGTH_SHORT).show();
        } else {
            if (pass.equals(conpass)) {
                mAuth.createUserWithEmailAndPassword(ema, pass)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    String id = user.getUid();
                                    DatabaseReference myRef = database.getReference("users");

                                    DatabaseReference u=myRef.child(id);
                                    u.child("email").setValue(email.getText().toString());
                                    u.child("department").setValue(spinner.getSelectedItem().toString());
                                    u.child("name").setValue(name.getText().toString());
                                    finish();


                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("abcdefghij", "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(getApplicationContext(), "Authentication failed : "+task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                }

                                // ...
                            }
                        });
            } else {
                Toast.makeText(getApplicationContext(), "Password mismatch", Toast.LENGTH_SHORT).show();
            }
        }

    }
}

