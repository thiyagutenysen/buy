package com.example.chella.buy.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.chella.buy.Data.adapter3;
import com.example.chella.buy.Model.Buy_and_sell;
import com.example.chella.buy.R;
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
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class lostAndFound extends AppCompatActivity {

    private RecyclerView rv;
    private FirebaseAuth mAuth;
    private DatabaseReference mdataBaseReference;
    private FirebaseUser muser;
    private FirebaseDatabase mdataBase;
    private adapter3 adapters;
    private List<Buy_and_sell> buy_and_sell;
    private FloatingActionButton add;
    private BottomNavigationView nav;
    private FirebaseFirestore db;
    private static final String TAG = "postActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_and_found);
        mAuth=FirebaseAuth.getInstance();
        muser=mAuth.getCurrentUser();
        mdataBase=FirebaseDatabase.getInstance();
        mdataBaseReference=mdataBase.getReference().child("mFound");
        db=FirebaseFirestore.getInstance();
        add = findViewById(R.id.floatingActionButton3);
        mdataBaseReference.keepSynced(true);
        nav =  findViewById(R.id.navigationView);

        mdataBaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Buy_and_sell buy =dataSnapshot.getValue(Buy_and_sell.class);
                buy_and_sell.add(buy);
                Log.d("abce",buy.getTitle());
                Collections.reverse(buy_and_sell);// newly added code suspect
                adapters = new adapter3(lostAndFound.this,buy_and_sell);
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
                        startActivity(new Intent(lostAndFound.this, account.class));
                        finish();
                        break;
                    case R.id.mypost:
                        startActivity(new Intent(lostAndFound.this, myPost.class));
                        finish();
                        break;
                    case R.id.navigation_home:
                        startActivity(new Intent(lostAndFound.this, postActivity.class));
                        finish();
                        break;
                    case R.id.mypostfounds:
                        startActivity(new Intent(lostAndFound.this, myPostFound.class));
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
                startActivity(new Intent(lostAndFound.this,addPostFound.class));


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
        });


    }

}
