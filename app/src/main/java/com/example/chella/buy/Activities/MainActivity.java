package com.example.chella.buy.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chella.buy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;
    private Button login;
    private Button signin;
    private EditText email;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        login =  findViewById(R.id.button);
        signin = findViewById(R.id.button2);
        email =  findViewById(R.id.editText);
        password = findViewById(R.id.editText2);
        mAuthListener= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user= mAuth.getCurrentUser();
                if (user!=null)
                {
                    Toast.makeText(MainActivity.this,"Successfully signed in",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(MainActivity.this,postActivity.class));
                    finish();
                }
                else{
                    Toast.makeText(MainActivity.this,"not signed in",Toast.LENGTH_LONG).show();
                }
            }
        };






        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(email.getText().toString())&& !TextUtils.isEmpty(password.getText().toString())){
                    String emailid=email.getText().toString();
                    String pwd=password.getText().toString();
                    login(emailid,pwd);
                }
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, com.example.chella.buy.Activities.signin.class));
            }
        });


    }

    public void login(String emailid, String pwd) {
        mAuth.signInWithEmailAndPassword(emailid,pwd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(MainActivity.this,"successfully logged in",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this,postActivity.class));
                            finish();
                        }
                        else {
                            Toast.makeText(MainActivity.this,"Check your internet connection",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);




    }


}
