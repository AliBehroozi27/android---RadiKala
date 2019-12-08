package com.example.radikala.photos;

import android.content.Context;
import android.util.Log;

import com.example.radikala.ApiUtils;
import com.example.radikala.photos.contract.PhotoContract;
import com.example.radikala.pojo.Photo;
import com.example.radikala.remote.ApiService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class PhotoPresenter implements PhotoContract.Presenter {
    private int albumId;
    private PhotoActivity mView;
    private ApiService photoService;
    private ArrayList<Photo> photos;
    private CompositeDisposable compositeDisposable;

    PhotoPresenter(PhotoActivity view) {
        mView = view;
        photoService = ApiUtils.getApiService();
    }

    @Override
    public void initial() {
        compositeDisposable = new CompositeDisposable();
        displayProgressBar(true);
        mView.setAlbumId();
        getPhotos();
    }

    @Override
    public void displayProgressBar(Boolean showFlag) {
        mView.displayProgressBar(showFlag);
    }

    @Override
    public void updateRecyclerView(ArrayList<Photo> photos) {
        mView.updateRecyclerView(photos);
    }

    @Override
    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    @Override
    public void displayRecyclerView(Boolean showFlag) {
        mView.displayRecyclerView(showFlag);
    }

    @Override
    public void filterPhotos() {
        Observable.fromIterable(photos)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .filter(new Predicate<Photo>() {
                    @Override
                    public boolean test(Photo model) throws Exception {
                        return model.getAlbumId() == albumId;
                    }
                }).toList()
                .subscribe(new SingleObserver<List<Photo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(List<Photo> filteredPhotos) {
                        photos = new ArrayList<Photo>(filteredPhotos);
                        displayProgressBar(false);
                        updateRecyclerView(photos);
                    }

                    @Override
                    public void onError(Throwable e) {makeToast(e.toString());}
                });
    }

    @Override
    public void clean() {
        mView = null;
        compositeDisposable.dispose();
    }

    @Override
    public void getPhotos() {
        photoService.getPhotos(albumId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<Photo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(ArrayList<Photo> receivedPhotos) {
                        photos = receivedPhotos;
                        filterPhotos();
                    }

                    @Override
                    public void onError(Throwable e){makeToast(e.toString());}

                    @Override
                    public void onComplete() {
                    }
                });

    }

    @Override
    public void makeToast(String message) {
        mView.makeToast(message);
    }

    @Override
    public void showSinglePhoto(int item) {
        if (item == -1) {
            displayRecyclerView(true);
            mView.removeSinglePhoto();
        } else {
            displayRecyclerView(false);
            mView.showSinglePhoto(photos.get(item).getUrl());
        }
    }


}
