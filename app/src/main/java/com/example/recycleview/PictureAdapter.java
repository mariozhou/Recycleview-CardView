package com.example.recycleview;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

//import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recycleview.post.view.PictureDetail;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.PictureViewHolder> {

    private ArrayList<Picture> pictures;
    private int resource;
    private Activity activity;

    public PictureAdapter(ArrayList<Picture> pictures, int resource, Activity activity) {
        this.pictures = pictures;
        this.resource = resource;
        this.activity = activity;
    }

    //@NonNull
    @Override
    public PictureViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);

        return new PictureViewHolder(view);
    }

    @Override
    public void onBindViewHolder( PictureViewHolder holder, int position) {
        Picture picture = pictures.get(position);

        holder.userName.setText(picture.getUserName());
        holder.time.setText(picture.getTime());
        holder.likeNum.setText(picture.getLike_number());
        Picasso.get().load(picture.getPicture()).into(holder.pictureCard);

        holder.pictureCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, PictureDetail.class);
                activity.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return pictures.size();
    }

    public class PictureViewHolder extends RecyclerView.ViewHolder{
        private ImageView pictureCard;
        private TextView  userName,time,likeNum;

        public PictureViewHolder( View itemView) {
            super(itemView);

            pictureCard = (ImageView)  itemView.findViewById(R.id.pictureCard);
            userName = (TextView) itemView.findViewById(R.id.userNameCard);
            time = (TextView) itemView.findViewById(R.id.timeCard);
            likeNum = (TextView) itemView.findViewById(R.id.firstWord);
        }


    }

}
