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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class adapter3 extends RecyclerView.Adapter<adapter3.ViewHolder> {
    public long position;
    public String key;


    private Context context;
    private List<Buy_and_sell> buy_list;


    public adapter3(Context context, List<Buy_and_sell> buy_list) {
        this.context = context;
        this.buy_list= buy_list;
    }


    @NonNull
    @Override
    public adapter3.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.lostpost,viewGroup,false);
        //View vi = LayoutInflater.from(context).inflate(R.layout.comment_row,viewGroup,false);
        return new adapter3.ViewHolder(v,context);
        //return new adapter3.ViewHolder(vi,context);
    }
    @Override
    public void onBindViewHolder(@NonNull adapter3.ViewHolder viewHolder, int i) {
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


        String userid;
        public ViewHolder(@NonNull View View, Context context) {
            super(View);
            seller = View.findViewById(R.id.sellerf);

            title = View.findViewById(R.id.product_namef);
            timestamp = View.findViewById(R.id.product_timef);
            price = View.findViewById(R.id.product_pricef);
            desc = View.findViewById(R.id.product_descf);
            image = View.findViewById(R.id.productf);
            contact=View.findViewById(R.id.conf);



            userid = null;
            View.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //final String id = getItemId();
                    position=getAdapterPosition();

                }
            });






        }

    }
}

