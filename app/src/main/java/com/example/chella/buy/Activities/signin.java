package com.example.chella.buy.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.chella.buy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signin extends AppCompatActivity {

    private EditText name;
    private EditText email;
    private EditText pwd;
    private EditText course;
    private Button register;
    private DatabaseReference mDatabaseReference;
    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;
    private ProgressDialog progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference().child("User");
        mAuth = FirebaseAuth.getInstance();
        progress = new ProgressDialog(this);
        name = findViewById(R.id.editText3);
        email = findViewById(R.id.editText4);
        pwd = findViewById(R.id.editText5);
        course = findViewById(R.id.editText6);
        register = findViewById(R.id.button3);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewAccount();
            }
        });
    }

    private void createNewAccount() {
        progress.setMessage("signing in...  ");
        final String naam = name.getText().toString().trim();
        String em = email.getText().toString().trim();
        String password = pwd.getText().toString().trim();
        final String core = course.getText().toString().trim();
        final int pass=password.length();

        if (!TextUtils.isEmpty(naam)&&!TextUtils.isEmpty(em)&&!TextUtils.isEmpty(password)&&!TextUtils.isEmpty(core)&&pass>=6&&em.contains("@smail.iitm.ac.in")){

            progress.show();

            mAuth.createUserWithEmailAndPassword(em,password)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                        if (authResult!=null){

                            String user = mAuth.getCurrentUser().getUid();
                            DatabaseReference currentUserdb = mDatabaseReference.child(user);
                            currentUserdb.child("Name").setValue(naam);
                            currentUserdb.child("Course").setValue(core);
                            mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(signin.this, "Successfully registered", Toast.LENGTH_LONG).show();
                                        finish();
                                        Intent intent = new Intent(signin.this,MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        finish();
                                        Toast.makeText(signin.this, "Verification mail has been sent to your mail.", Toast.LENGTH_LONG).show();
                                        Toast.makeText(signin.this, "please check your smail", Toast.LENGTH_LONG).show();
                                    }
                                    else{
                                        Toast.makeText(signin.this, "try again", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                            progress.dismiss();

                        }
                        else{
                            progress.dismiss();
                            Toast.makeText(signin.this,"email id already exists, try to reset your password by clicking forget password",Toast.LENGTH_LONG).show();
                        }

                                              }}
                    );
        }
        else {
             if(TextUtils.isEmpty(naam)||TextUtils.isEmpty(em)||TextUtils.isEmpty(password)||TextUtils.isEmpty(core)) {
                 Toast.makeText(signin.this, "fill all the sections", Toast.LENGTH_LONG).show();
             }
            else if (pass<6)
            {
                Toast.makeText(signin.this,"password should contain atleast 6 characters",Toast.LENGTH_LONG).show();
            }
            else if (!em.contains("@smail.iitm.ac.in")){
                 Toast.makeText(signin.this,"email must be your smail id",Toast.LENGTH_LONG).show();
        }

        }
    }
}
