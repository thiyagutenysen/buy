package com.example.chella.buy.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.example.chella.buy.Data.adapter5;
import com.example.chella.buy.Model.Buy_and_sell;
import com.example.chella.buy.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

    public class myPostFound extends AppCompatActivity {
        private BottomNavigationView nav;
        private RecyclerView rv;
        private FirebaseAuth mAuth;
        private DatabaseReference mdataBaseReference,mybase;
        private FirebaseUser muser;
        private FirebaseDatabase mdataBase;
        private adapter5 adapters;
        private List<Buy_and_sell> buy_and_sell;
        private FirebaseFirestore db;
        private Button delete;
        public CardView cardView;
        private String key;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Log.d("mp","0");
            setContentView(R.layout.activity_my_post_found);
            Log.d("mp","0");
            nav = findViewById(R.id.navigationView);
            mAuth = FirebaseAuth.getInstance();
            muser = mAuth.getCurrentUser();
            mdataBase = FirebaseDatabase.getInstance();
            mdataBaseReference = mdataBase.getReference().child("mFound");
            mybase=mdataBase.getReference().child("User");
            db = FirebaseFirestore.getInstance();
            mdataBaseReference.keepSynced(true);
            buy_and_sell = new ArrayList<>();
            delete=findViewById(R.id.delete);
            cardView=findViewById(R.id.cv);


            nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.navigation_account:
                            startActivity(new Intent(myPostFound.this, account.class));
                            finish();
                            break;
                        case R.id.navigation_home:
                            startActivity(new Intent(myPostFound.this, postActivity.class));
                            finish();
                            break;
                        case R.id.mypost:
                            startActivity(new Intent(myPostFound.this, myPost.class));
                            finish();
                            break;
                        case R.id.navigation_found:
                            startActivity(new Intent(myPostFound.this, lostAndFound.class));
                            finish();
                            break;
                    }
                    return true;
                }
            });
            rv = findViewById(R.id.recyclerView);
            rv.setHasFixedSize(true);
            rv.setLayoutManager(new LinearLayoutManager(this));

            mdataBaseReference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Buy_and_sell buy= null;
                    //for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())

                    //Toast.makeText(myPost.this,"Success",Toast.LENGTH_LONG).show();
                    //User user = dataSnapshot1.getValue(User.class);
                    Log.d("hello",dataSnapshot.child("userid").getValue(String.class) );
                    Log.d("bye", muser.getUid());
                    /*for (DataSnapshot data : dataSnapshot.getChildren())
                    {
                        Log.d("hi", "I am happy");
                        if (data.child("userid").getValue()== muser.getUid())
                        {
                            Log.d("kola", "I am happy");
                            buy = data.getValue(Buy_and_sell.class);
                            buy_and_sell.add(buy);
                        }
                    }*/

                    if (dataSnapshot.child("userid").getValue(String.class).equals(muser.getUid()))

                    {
                        Log.d("hi", "I am happy");
                        Toast.makeText(myPostFound.this,"Success",Toast.LENGTH_LONG).show();
                        buy = dataSnapshot.getValue(Buy_and_sell.class);
                        buy_and_sell.add(buy);
                    }
                    // buy = dataSnapshot.getValue(Buy_and_sell.class);

                    //buy_and_sell.add(buy);
                    Collections.reverse(buy_and_sell);// newly added code suspect
                    adapters = new adapter5(myPostFound.this,buy_and_sell);
                    rv.setAdapter(adapters);
                    adapters.notifyDataSetChanged();
                    //final String postkey = getReferrer(cardView).toString();
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

            SharedPreferences sharedPreferences = getSharedPreferences("aa",MODE_PRIVATE);
            key = sharedPreferences.getString("key","null");

        }
    }