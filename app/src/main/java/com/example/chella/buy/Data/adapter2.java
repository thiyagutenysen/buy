package com.example.chella.buy.Data;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chella.buy.Activities.each_post;
import com.example.chella.buy.Activities.myPost;
import com.example.chella.buy.Model.Buy_and_sell;
import com.example.chella.buy.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static android.support.constraint.Constraints.TAG;


public class adapter2 extends RecyclerView.Adapter<adapter2.ViewHolder> {
public long position;
public String key;
public String mey;


    private Context context;
    private List<Buy_and_sell> buy_list;
    private DatabaseReference databaseReference;
    private StorageReference mstorageReference,photoRef;
    private String image;


    public adapter2(Context context, List<Buy_and_sell> buy_list) {
        this.context = context;
        this.buy_list= buy_list;
    }


    @NonNull
    @Override
    public adapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.mypost_row,viewGroup,false);
        return new ViewHolder(v,context);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Buy_and_sell buy = buy_list.get(i);
        //long timecreated = Long.valueOf(buy.getTimestamp());
        final String imageurl;



        viewHolder.title.setText(buy.getTitle());
        viewHolder.desc.setText(buy.getDesc());
        viewHolder.price.setText(buy.getPrice());
        viewHolder.seller.setText(buy.getSeller());
        viewHolder.contact.setText(buy.getContact());

        DateFormat dateFormat = DateFormat.getDateInstance();
        String formattedDate = dateFormat.format(new Date(Long.valueOf(buy.getTimestamp())).getTime());
        viewHolder.timestamp.setText(formattedDate);
        imageurl=buy.getImage();
        Picasso.with(context)
                .load(imageurl)
                .into(viewHolder.image);
        //final SharedPreferences sharedPreferences = context.getSharedPreferences("aa",Context.MODE_PRIVATE);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mey = buy.getKey();
                SharedPreferences sharedPreferences= context.getSharedPreferences("chella",MODE_PRIVATE);
                SharedPreferences.Editor editor= sharedPreferences.edit();
                editor.putString("key",mey);
                editor.commit();
                Intent intent = new Intent(context,each_post.class);
                context.startActivity(intent);

               Toast.makeText(context,mey,Toast.LENGTH_LONG).show();
            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference().child("mBuy");
        mstorageReference = FirebaseStorage.getInstance().getReference().child("product");

        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                key = buy.getKey();
                databaseReference.child(key).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        Log.d("dev",dataSnapshot.child("image").getValue(String.class));
                      image  = dataSnapshot.child("image").getValue(String.class);
                        // Toast.makeText(add_post.this,investor,Toast.LENGTH_LONG).show();

                        Log.d("imggggg",image);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                photoRef = FirebaseStorage.getInstance().getReferenceFromUrl(image);
                photoRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // File deleted successfully
                        Toast.makeText(context,"successfully deleted",Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Uh-oh, an error occurred!
                        Toast.makeText(context,"unsuccessful, try again",Toast.LENGTH_LONG).show();
                    }
                });
                FirebaseDatabase.getInstance().getReference().child("mBuy").child(key).removeValue();
                Intent intent = new Intent(context,myPost.class);
                context.startActivity(intent);

            }
        });






    }


    @Override
    public int getItemCount() {
        return buy_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView seller;
        public TextView title;
        public TextView price;
        public TextView desc;
        public TextView timestamp;
        public ImageView image;
        public TextView contact;

        public Button delete;
        String userid;
        public ViewHolder(@NonNull View View, Context context) {
            super(View);
            seller = View.findViewById(R.id.seller);

            title = View.findViewById(R.id.product_name);
            timestamp = View.findViewById(R.id.product_time);
            price = View.findViewById(R.id.product_price);
            desc = View.findViewById(R.id.product_desc);
            image = View.findViewById(R.id.product);
            contact=View.findViewById(R.id.contactInfo);

            delete = View.findViewById(R.id.delete);

            userid = null;
            View.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //final String id = getItemId();
                    position=getAdapterPosition();

                }
            });

            databaseReference = FirebaseDatabase.getInstance().getReference().child("mBuy");
            mstorageReference = FirebaseStorage.getInstance().getReference("product");




        }

        }
}
