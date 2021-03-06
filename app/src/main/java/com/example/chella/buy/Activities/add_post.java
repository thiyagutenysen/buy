package com.example.chella.buy.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.chella.buy.Model.Buy_and_sell;
import com.example.chella.buy.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;

public class add_post extends AppCompatActivity {
    private ImageButton img;
    private EditText con;
    private EditText title;
    private EditText price;
    private EditText desc;
    private Button submit;
    private FirebaseDatabase database;
    private StorageReference mstorageReference;
    private DatabaseReference mdataBaseReference,mybase;
    private FirebaseUser muser;
    private FirebaseAuth mAuth;
    private Uri imguri=null;
    private FirebaseFirestore db;
    private static final int code=1;
    private ProgressBar progressBar;
    private ProgressDialog mprogress;
     String investor ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        mAuth = FirebaseAuth.getInstance();
        muser = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        mdataBaseReference = database.getReference().child("mBuy");
        mybase = database.getReference().child("User");
        img = findViewById(R.id.imageButton);
        db = FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.progressBar2);
        title = findViewById(R.id.editText7);
        price = findViewById(R.id.editText9);
        desc = findViewById(R.id.editText10);
        con = findViewById(R.id.ci);
        submit = findViewById(R.id.button4);
        mprogress = new ProgressDialog(this);

        mstorageReference = FirebaseStorage.getInstance().getReference();
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
                gallery.setType("image/*");
                startActivityForResult(gallery, code);

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mybase.child(muser.getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                        investor = (String) dataSnapshot.child("Name").getValue();
                        // Toast.makeText(add_post.this,investor,Toast.LENGTH_LONG).show();


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                startPosting();

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==code && resultCode==RESULT_OK){
                imguri=data.getData();
                img.setImageURI(imguri);

        }
    }


    private void startPosting() {
        mprogress.setMessage("Posting...");
        final String titleval = title.getText().toString().trim();
        final String priceval = price.getText().toString().trim();
        final String descval = desc.getText().toString().trim();
        final String conval = con.getText().toString().trim();

        if (!TextUtils.isEmpty(titleval)&&!TextUtils.isEmpty(priceval)){
            mprogress.show();
            StorageReference filepath = mstorageReference.child("product").child(imguri.getLastPathSegment());
            filepath.putFile(imguri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Log.d("1","1");
                    progressBar.setVisibility(View.VISIBLE);
                    //progressBar.show();
                    Uri download = taskSnapshot.getDownloadUrl();
                    DatabaseReference newpost = mdataBaseReference.push();

                    /*mybase.child(muser.getUid()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {



                                 investor = (String)dataSnapshot.child("Name").getValue();
                           // Toast.makeText(add_post.this,investor,Toast.LENGTH_LONG).show();


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });*/
                    final String seller = investor;
                    Map<String,String> datatosave = new HashMap<>();
                    datatosave.put("title",titleval);
                    datatosave.put("price","₹"+priceval);
                    datatosave.put("desc","Description : "+descval);
                    datatosave.put("seller","Seller: "+seller);
                    datatosave.put("key",newpost.getKey());
                    datatosave.put("contact","Contact Info: "+conval);
                    datatosave.put("timestamp",String.valueOf(java.lang.System.currentTimeMillis()));
                    datatosave.put("image", String.valueOf(download));


                    datatosave.put("userid",muser.getUid());
                   newpost.setValue(datatosave);
                   mprogress.dismiss();
                    /*db.collection("products").add(datatosave).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(add_post.this,"Successssss",Toast.LENGTH_LONG).show();
                        }
                    });*/

                    startActivity(new Intent(add_post.this,postActivity.class));
                    finish();
                    progressBar.setVisibility(View.GONE);

                }
            });


            }
        }
    }

