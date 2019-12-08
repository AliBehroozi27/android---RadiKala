package com.example.radikala.single_photo.contract;

public interface SinglePhotoContract {
    public interface View {
        void displayProgressBar(Boolean showFlag);

        void showPhoto(String photoUrl);
    }

    public interface Presenter {
        void showPhoto();

        void clean();

        void displayProgressBar(Boolean showFlag);

        void setPhotoUrl(String photoUrl);

    }
}
