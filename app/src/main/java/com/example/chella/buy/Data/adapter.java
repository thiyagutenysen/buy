package com.example.chella.buy.Data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chella.buy.Model.Buy_and_sell;
import com.example.chella.buy.R;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class adapter extends RecyclerView.Adapter<adapter.ViewHolder>{

    private Context context;
    private List<Buy_and_sell> buy_list;

    public adapter(Context context, List<Buy_and_sell> buy_list) {
        this.context = context;
        this.buy_list= buy_list;
    }

    @NonNull
    @Override
    public adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.post_row,viewGroup,false);
        return new ViewHolder(v,context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Buy_and_sell buy = buy_list.get(i);
        String imageurl;
        viewHolder.title.setText(buy.getTitle());
        viewHolder.desc.setText(buy.getDesc());
        viewHolder.price.setText(buy.getPrice());

        viewHolder.title.setText(buy.getTitle());

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
        public TextView title;
        public TextView price;
        public TextView desc;
        public TextView timestamp;
        public ImageView image;
        String userid;
        public ViewHolder(@NonNull View View, Context context) {
            super(View);

            title = View.findViewById(R.id.product_name);
            timestamp = View.findViewById(R.id.product_time);
            price = View.findViewById(R.id.product_price);
            desc = View.findViewById(R.id.product_desc);
            image = View.findViewById(R.id.product);
            userid = null;
            View.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });


        }
    }
}
