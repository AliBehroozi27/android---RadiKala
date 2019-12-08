package com.example.radikala.albums.contract;

import com.example.radikala.pojo.Album;

import java.util.ArrayList;

public interface AlbumContract {
    interface View {
        void updateRecyclerView(ArrayList<Album> albums);

        void displayProgressBar(Boolean showFlag);

        void showPhotoActivity(int albumId);

        void makeToast(String message);
    }

    interface Presenter {
        void initial();

        void displayProgressBar(Boolean showFlag);

        void updateRecyclerView(ArrayList<Album> albums);

        void getAlbums();

        void clean();

        void makeToast(String message);

        void showPhotoActivity(int item);
    }
}
