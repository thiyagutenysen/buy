package com.example.chella.buy.Data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chella.buy.Model.comment;
import com.example.chella.buy.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class adapter4 extends RecyclerView.Adapter<adapter4.ViewHolder> {
    public long position;
    public String key;


    private Context context;
    private List<comment> comments;
    private DatabaseReference databaseReference;

    public adapter4(Context context, List<comment> comments) {
        this.context = context;
        this.comments= comments;
    }


    @NonNull
    @Override
    public adapter4.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.comment_row,viewGroup,false);
        return new adapter4.ViewHolder(v,context);
        //return new adapter3.ViewHolder(vi,context);
    }
    @Override
    public void onBindViewHolder(@NonNull adapter4.ViewHolder viewHolder, int i) {
        final comment buy = comments.get(i);
        //long timecreated = Long.valueOf(buy.getTimestamp());
        String imageurl;

        viewHolder.comment.setText(buy.getComment());












    }


    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView comment;


        //public Button delete;
        String userid;
        public ViewHolder(@NonNull View View, Context context) {
            super(View);
            comment = View.findViewById(R.id.comment);



            userid = null;
            View.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //final String id = getItemId();
                    position=getAdapterPosition();

                }
            });

            databaseReference = FirebaseDatabase.getInstance().getReference("mBuy");




        }

    }
}
