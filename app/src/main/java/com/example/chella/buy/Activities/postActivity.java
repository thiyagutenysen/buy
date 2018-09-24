package com.example.chella.buy.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;

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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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
                        break;}
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


    }

    @Override
    protected void onStart() {
        super.onStart();

        mdataBaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Buy_and_sell buy =dataSnapshot.getValue(Buy_and_sell.class);
                buy_and_sell.add(buy);
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



