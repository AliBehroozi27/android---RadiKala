package com.example.radikala.albums;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.example.radikala.ApiUtils;
import com.example.radikala.albums.contract.AlbumContract;
import com.example.radikala.pojo.Album;
import com.example.radikala.remote.ApiService;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

@RequiresApi(api = Build.VERSION_CODES.M)
public class AlbumPresenter implements AlbumContract.Presenter {
    private AlbumActivity mView;
    private ApiService albumService;
    private ArrayList<Album> albums;
    private CompositeDisposable compositeDisposable;


    AlbumPresenter(AlbumActivity view) {
        mView = view;
        albumService = ApiUtils.getApiService();
        initial();
    }

    @Override
    public void initial() {
        compositeDisposable = new CompositeDisposable();
        displayProgressBar(true);
        getAlbums();
    }


    @Override
    public void displayProgressBar(Boolean showFlag) {
        mView.displayProgressBar(showFlag);
    }


    @Override
    public void updateRecyclerView(ArrayList<Album> albums) {
        mView.updateRecyclerView(albums);
    }


    @Override
    public void getAlbums() {
        albumService.getAlbums().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<Album>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(ArrayList<Album> receivedAlbums) {
                        albums = receivedAlbums;
                        displayProgressBar(false);
                        updateRecyclerView(albums);
                    }

                    @Override
                    public void onError(Throwable e) {
                        makeToast(e.toString());
                    }

                    @Override
                    public void onComplete() {}
                });
    }

    @Override
    public void clean() {
        mView = null;
        compositeDisposable.dispose();
    }

    @Override
    public void makeToast(String message) {
        mView.makeToast(message);
    }

    @Override
    public void showPhotoActivity(int item) {
        mView.showPhotoActivity(albums.get(item).getId());
    }
}
