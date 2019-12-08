package com.example.radikala.adaptor;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.radikala.R;
import com.example.radikala.pojo.Album;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AlbumRC extends RecyclerView.Adapter<AlbumRC.ViewHolder> {
    private List<Album> albums;
    private final OnItemClickListener listener;

    public AlbumRC(List<Album> albums , OnItemClickListener onItemClickListener) {
        this.albums = albums;
        listener = onItemClickListener;
    }

    public void setAlbums(ArrayList<Album> albums) {
        this.albums = albums;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_view_album, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Album album = albums.get(position);
        holder.title.setText(album.getTitle());
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.title)
        TextView title;

        ViewHolder(View itemView) {
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

