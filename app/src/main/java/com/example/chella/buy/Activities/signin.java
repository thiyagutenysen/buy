package com.example.chella.buy.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.chella.buy.R;
import com.google.android.gms.tasks.OnSuccessListener;
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
        if (!TextUtils.isEmpty(naam)&&!TextUtils.isEmpty(em)&&!TextUtils.isEmpty(password)&&!TextUtils.isEmpty(core)){
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

                            Intent intent = new Intent(signin.this,postActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            progress.dismiss();
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Toast.makeText(signin.this,"try again",Toast.LENGTH_LONG).show();
                        }
                                              }
                                          }
                    );
        }
    }
}
