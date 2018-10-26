package com.example.chella.buy.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.chella.buy.Data.adapter4;
import com.example.chella.buy.Data.adapter3;
import com.example.chella.buy.Model.comment;
import com.example.chella.buy.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class each_post extends AppCompatActivity {

    private RecyclerView rv;
    private FirebaseAuth mAuth;
    private DatabaseReference mdataBaseReference,mybase;
    private FirebaseUser muser;
    private FirebaseDatabase mdataBase;
   // private adapter3 adapter;
    private adapter4 adapters;
    private List<comment> comments;
    private FirebaseFirestore db;
    private FloatingActionButton send;
    public CardView cardView;
      String investor;
    public String key;
    private ProgressDialog mprogress;
    private EditText comment;
    private TextView sell;

   @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_each_post);
       SharedPreferences sharedPreferences = getSharedPreferences("chella", MODE_PRIVATE);
       key = sharedPreferences.getString("key", "null");
       Log.d("kola", key);

       mAuth = FirebaseAuth.getInstance();
       muser = mAuth.getCurrentUser();
       mdataBase = FirebaseDatabase.getInstance();
       mdataBaseReference = mdataBase.getReference().child("mBuy").child(key).child("comment");
       mybase = mdataBase.getReference().child("User");
       // db = FirebaseFirestore.getInstance();
       mdataBaseReference.keepSynced(true);
       comment = findViewById(R.id.editText8);
       mprogress = new ProgressDialog(this);
       comments = new ArrayList<>();
       send = findViewById(R.id.floatingActionButton2);
       sell = findViewById(R.id.textView);
       cardView = findViewById(R.id.cardv);
       rv = findViewById(R.id.recycle);
       rv.setHasFixedSize(true);
       rv.setLayoutManager(new LinearLayoutManager(this));

       mdataBase.getReference().child("mBuy").addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {


               String sel = dataSnapshot.child(key).child("seller").getValue(String.class);
               sell.setText( sel);

               //Toast.makeText(each_post.this, investor, Toast.LENGTH_LONG).show();


           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });
       mybase.child(muser.getUid()).addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {


               investor = dataSnapshot.child("Name").getValue(String.class);


               //Toast.makeText(each_post.this, investor, Toast.LENGTH_LONG).show();


           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });
       send.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               startPosting();

           }
       });

   }
           private void startPosting() {
               mprogress.setMessage("Posting...");
               final String commentval = comment.getText().toString().trim();
               final String seller = investor;
               Toast.makeText(each_post.this, investor, Toast.LENGTH_LONG).show();
               if (!TextUtils.isEmpty(commentval)){
                   mprogress.show();


                           //Log.d("hoool",investor);

                           //progressBar.show();
                           //Uri download = taskSnapshot.getDownloadUrl();
                           DatabaseReference newpost = mdataBaseReference.push();
                  // Toast.makeText(each_post.this, investor, Toast.LENGTH_LONG).show();


                           Map<String,String> datatosave = new HashMap<>();
                           datatosave.put("comment",seller+" : "+commentval);
                           datatosave.put("userid",muser.getUid());
                           newpost.setValue(datatosave);
                           mprogress.dismiss();


                           startActivity(new Intent(each_post.this,each_post.class));
                           finish();


                       }
                   }


    @Override
    protected void onStart() {
        super.onStart();


        mdataBaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                comment buy = null;

                Log.d("bye", key);
                    Log.d("hi", "I am happy");
                   // Toast.makeText(each_post.this, "Success", Toast.LENGTH_LONG).show();
                    buy = dataSnapshot.getValue(comment.class);
                    comments.add(buy);

                // buy = dataSnapshot.getValue(Buy_and_sell.class);

                //buy_and_sell.add(buy);
               // Collections.reverse(buy_and_sell);// newly added code suspect
                adapters = new adapter4(each_post.this,comments);
                rv.setAdapter(adapters);
                rv.scrollToPosition(comments.size() - 1);
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


    }
    }

