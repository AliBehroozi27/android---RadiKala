package com.example.radikala.photos;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.radikala.R;
import com.example.radikala.adaptor.PhotoRC;
import com.example.radikala.photos.contract.PhotoContract;
import com.example.radikala.pojo.Photo;
import com.example.radikala.single_photo.SinglePhotoFragment;

import java.util.ArrayList;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoActivity extends AppCompatActivity implements PhotoContract.View, PhotoRC.OnItemClickListener {
    @BindView(R.id.recycler_view)
    RecyclerView photosRv;
    @BindView(R.id.progress_bar)
    ProgressBar loadingProgressBar;
    @BindString(R.string.photos)
    String TITLE;

    private static final String ALBUM_ID = "album_id";
    private int albumID;
    private PhotoRC mAdapter;
    private PhotoPresenter presenter;
    private SinglePhotoFragment singlePhotoFragment;
    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        ButterKnife.bind(this);
        setActionBarTitle();
        setAlbumId();
        initialFM();
        initialPresenter();
        initialRecyclerView();
    }

    private void setActionBarTitle() {
        getSupportActionBar().setTitle(TITLE);
    }

    private void initialFM() {
        fragmentManager = getSupportFragmentManager();
    }

    private void initialPresenter() {
        presenter = new PhotoPresenter(this);
        presenter.setAlbumId(albumID);
        presenter.initial();
    }


    public void initialRecyclerView() {
        mAdapter = new PhotoRC(new ArrayList<Photo>() {}, this);
        photosRv.setLayoutManager(new LinearLayoutManager(this));
        photosRv.setAdapter(mAdapter);
        photosRv.setItemAnimator(new DefaultItemAnimator());
        photosRv.setHasFixedSize(true);
    }


    @Override
    public void updateRecyclerView(ArrayList<Photo> photos) {
        mAdapter.setPhotos(photos);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void displayProgressBar(Boolean showFlag) {
        if (showFlag)
            loadingProgressBar.setVisibility(View.VISIBLE);
        else
            loadingProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void displayRecyclerView(Boolean showFlag) {
        if (showFlag)
            photosRv.setVisibility(View.VISIBLE);
        else
            photosRv.setVisibility(View.INVISIBLE);
    }


    @Override
    public void setAlbumId() {
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        albumID = bundle.getInt(ALBUM_ID);
    }

    @Override
    public void makeToast(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSinglePhoto(String photoUrl) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        singlePhotoFragment = SinglePhotoFragment.newInstance(photoUrl);
        transaction.add(R.id.container, singlePhotoFragment).commit();
    }


    @Override
    public void removeSinglePhoto() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.remove(singlePhotoFragment).commit();
    }


    @Override
    public void onItemClick(int item) {
        presenter.showSinglePhoto(item);
    }


    @Override
    public void onBackPressed() {
        if (photosRv.getVisibility() == View.INVISIBLE)
            presenter.showSinglePhoto(-1);
        else {
            presenter.clean();
            super.onBackPressed();
        }
    }
}
