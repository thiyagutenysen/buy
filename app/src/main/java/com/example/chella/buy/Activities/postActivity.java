package com.example.chella.buy.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.Toast;

import com.example.chella.buy.Model.Buy_and_sell;
import com.example.chella.buy.R;
import com.example.chella.buy.Data.adapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class postActivity extends AppCompatActivity {

    private RecyclerView rv;
    private FirebaseAuth mAuth;
    private DatabaseReference mdataBaseReference;
    private FirebaseUser muser;
    private FirebaseDatabase mdataBase;
    private adapter adapters;
    private List<Buy_and_sell> buy_and_sell;
    private FloatingActionButton add;
    private BottomNavigationView nav;
    private FirebaseFirestore db;
    private static final String TAG = "postActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        mAuth=FirebaseAuth.getInstance();
        muser=mAuth.getCurrentUser();
        mdataBase=FirebaseDatabase.getInstance();
        mdataBaseReference=mdataBase.getReference().child("mBuy");
        db=FirebaseFirestore.getInstance();
        add = findViewById(R.id.floatingActionButton);
        mdataBaseReference.keepSynced(true);
        nav =  findViewById(R.id.navigationView);







    }

    @Override
    protected void onStart() {
        super.onStart();

        if (muser.isEmailVerified()) {
            Log.d("check verif ins","1");

        mdataBaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Buy_and_sell buy =dataSnapshot.getValue(Buy_and_sell.class);
                buy_and_sell.add(buy);
                Log.d("abce",buy.getTitle());
                Collections.reverse(buy_and_sell);// newly added code suspect
                adapters = new adapter(postActivity.this,buy_and_sell);
                rv.setAdapter(adapters);
                adapters.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        buy_and_sell = new ArrayList<>();
        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_account:
                        startActivity(new Intent(postActivity.this, account.class));
                        finish();
                        break;
                    case R.id.mypost:
                        startActivity(new Intent(postActivity.this, myPost.class));
                        finish();
                        break;
                    case R.id.mypostfounds:
                        startActivity(new Intent(postActivity.this, myPostFound.class));
                        Log.d("awesome","0");
                        finish();
                        break;
                    case R.id.navigation_found:
                        startActivity(new Intent(postActivity.this, lostAndFound.class));
                        finish();
                        break;
                    }
                return true;
            }
        });
        rv =  findViewById(R.id.recyclerv);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(postActivity.this,add_post.class));


            }
        });
        long cutoff = new Date().getTime() - TimeUnit.MILLISECONDS.convert(30, TimeUnit.DAYS);
        System.out.print(cutoff);
        Query oldItems = mdataBaseReference.orderByChild("timestamp").endAt(cutoff);
        oldItems.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot itemSnapshot: snapshot.getChildren()) {
                    itemSnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        }); }
        else {
            Log.d("check verif ins", "2");

            emailverify();
        }


    }

    public void emailverify() {
        Log.d("verify","3");
        new AlertDialog.Builder(this)
                .setTitle("Email Not Verified")
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        mAuth.signOut();
                        Intent intent = new Intent(postActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setMessage("Please go to your inbox and verify your mail")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mAuth.signOut();
                        Intent intent = new Intent(postActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).create().show();
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation,menu);
        return super.onCreateOptionsMenu(menu);*/
    }
    //@Override
    //public boolean onNavigationItemSelected(MenuItem item) {
      //      switch (item.getItemId()) {
        //        case R.id.navigation_account:
          //          startActivity(new Intent(postActivity.this, account.class));
            //        break;

            //}

       // return super.onOptionsItemSelected(item);
    //}



