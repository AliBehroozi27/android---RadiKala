package com.example.radikala.remote;


import com.example.radikala.pojo.Album;
import com.example.radikala.pojo.Photo;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiService {
    @GET("albums")
    Observable<ArrayList<Album>> getAlbums(
    );

    @GET("photos")
    Observable<ArrayList<Photo>> getPhotos(
            @Query("albumId") int albumId
    );
}
