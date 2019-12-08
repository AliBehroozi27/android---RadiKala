package com.example.radikala.photos.contract;

import com.example.radikala.pojo.Photo;

import java.util.ArrayList;

public interface PhotoContract {
    public interface View {
        void updateRecyclerView(ArrayList<Photo> photos);

        void displayProgressBar(Boolean showFlag);

        void displayRecyclerView(Boolean showFlag);

        void setAlbumId();

        void makeToast(String message);

        void showSinglePhoto(String photoUrl);

        void removeSinglePhoto();
    }

    public interface Presenter {
        void initial();

        void displayProgressBar(Boolean showFlag);

        void updateRecyclerView(ArrayList<Photo> photos);

        void setAlbumId(int albumId);

        void displayRecyclerView(Boolean showFlag);

        void filterPhotos();

        void clean();

        void getPhotos();

        void makeToast(String message);

        void showSinglePhoto(int item);
    }
}
