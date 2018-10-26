package com.example.chella.buy.Data;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class adapter5 extends RecyclerView.Adapter<adapter5.ViewHolder> {
    public long position;
    public String key;
    public String mey;
    public String image;
    private FirebaseStorage mdatabaseStorage;
    private StorageReference photoRef;


    private Context context;
    private List<Buy_and_sell> buy_list;
    private DatabaseReference databaseReference;

    public adapter5(Context context, List<Buy_and_sell> buy_list) {
        this.context = context;
        this.buy_list= buy_list;
    }


    @NonNull
    @Override
    public adapter5.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.mypost_found_row,viewGroup,false);
        return new ViewHolder(v,context);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Buy_and_sell buy = buy_list.get(i);
        //long timecreated = Long.valueOf(buy.getTimestamp());
        String imageurl;



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


        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                key = buy.getKey();
                databaseReference.child(key).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                        image  = (String) dataSnapshot.child("image").getValue();
                        // Toast.makeText(add_post.this,investor,Toast.LENGTH_LONG).show();


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                photoRef = mdatabaseStorage.getReferenceFromUrl(image);
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
            seller = View.findViewById(R.id.sellerf);

            title = View.findViewById(R.id.product_namef);
            timestamp = View.findViewById(R.id.product_timef);
            price = View.findViewById(R.id.product_pricef);
            desc = View.findViewById(R.id.product_descf);
            image = View.findViewById(R.id.productf);
            contact=View.findViewById(R.id.contactInfof);

            delete = View.findViewById(R.id.deletef);

            userid = null;
            View.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //final String id = getItemId();
                    position=getAdapterPosition();

                }
            });

            databaseReference = FirebaseDatabase.getInstance().getReference("mFound");
            mdatabaseStorage=FirebaseStorage.getInstance();




        }

    }
}
