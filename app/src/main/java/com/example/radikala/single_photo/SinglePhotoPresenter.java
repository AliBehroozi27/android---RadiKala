package com.example.radikala.single_photo;

import com.example.radikala.single_photo.contract.SinglePhotoContract;

public class SinglePhotoPresenter implements SinglePhotoContract.Presenter {
    private String photoUrl;
    private SinglePhotoFragment mView;


    SinglePhotoPresenter(SinglePhotoFragment mView) {
        this.mView = mView;
        displayProgressBar(true);
    }


    @Override
    public void showPhoto() {
        mView.showPhoto(photoUrl);
    }


    @Override
    public void displayProgressBar(Boolean showFlag) {
        if (showFlag)
            mView.displayProgressBar(true);
        else
            mView.displayProgressBar(false);
    }


    @Override
    public void clean() {
        mView = null;
    }


    @Override
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
