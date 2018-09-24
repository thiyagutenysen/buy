package com.example.chella.buy.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.chella.buy.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class account extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private Button signout;
    private BottomNavigationView nav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        signout=findViewById(R.id.button5);
        nav=findViewById(R.id.navigationView);
        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        startActivity(new Intent(account.this, postActivity.class));
                        finish();
                        break;
                    case R.id.mypost:
                        startActivity(new Intent(account.this, myPost.class));
                        finish();
                        break;}
                return true;
            }
        });
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAuth!=null&&user!=null){
                    mAuth.signOut();

                }
                startActivity(new Intent(account.this,MainActivity.class));
                finish();
            }
        });


    }}

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation,menu);
        return super.onCreateOptionsMenu(menu);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.navigation_home:
                startActivity(new Intent(account.this,postActivity.class));
                break;


        }
        return super.onOptionsItemSelected(item);
    }
}*/
