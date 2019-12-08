package com.example.radikala.adaptor;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.radikala.R;
import com.example.radikala.pojo.Photo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoRC extends RecyclerView.Adapter<PhotoRC.ViewHolder> {
    private final OnItemClickListener listener;
    public List<Photo> photos;


    public PhotoRC(List<Photo> photos , OnItemClickListener onItemClickListener) {
        this.photos = photos;
        listener = onItemClickListener;
    }


    public void setPhotos(ArrayList<Photo> photos) {
        this.photos = photos;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_view_photo, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Photo photo = photos.get(position);
        holder.title.setText(photo.getTitle());
        Picasso.get().load( photo.getThumbnailUrl()).into(holder.thumbnail);

    }


    @Override
    public int getItemCount() {
        return photos.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.thumbnail)
        ImageView thumbnail;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            listener.onItemClick(getLayoutPosition());

        }
    }


    public interface OnItemClickListener {
        void onItemClick(int item);
    }
}
