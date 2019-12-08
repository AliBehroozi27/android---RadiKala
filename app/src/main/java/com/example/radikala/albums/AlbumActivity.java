package com.example.radikala.albums;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.radikala.R;
import com.example.radikala.adaptor.AlbumRC;
import com.example.radikala.albums.contract.AlbumContract;
import com.example.radikala.photos.PhotoActivity;
import com.example.radikala.pojo.Album;

import java.util.ArrayList;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

@RequiresApi(api = Build.VERSION_CODES.M)
public class AlbumActivity extends AppCompatActivity implements AlbumContract.View, AlbumRC.OnItemClickListener {
    @BindView(R.id.recycler_view)
    RecyclerView albumsRv;
    @BindView(R.id.progress_bar)
    ProgressBar loadingProgressBar;
    @BindString(R.string.albums)
    String TITLE;

    private static final String ALBUM_ID = "album_id";
    private AlbumRC mAdapter;
    private AlbumPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        ButterKnife.bind(this);
        setActionBarTitle();
        initialPresenter();
        initialRecyclerView();
    }

    private void setActionBarTitle() {
        getSupportActionBar().setTitle(TITLE);
    }

    private void initialPresenter() {
        presenter = new AlbumPresenter(this);
    }

    public void initialRecyclerView() {
        mAdapter = new AlbumRC( new ArrayList<Album>() {} , this);
        albumsRv.setLayoutManager(new LinearLayoutManager(this));
        albumsRv.setAdapter(mAdapter);
        albumsRv.setItemAnimator(new DefaultItemAnimator());
        albumsRv.setHasFixedSize(true);
    }

    @Override
    public void updateRecyclerView(ArrayList<Album> albums) {
        mAdapter.setAlbums(albums);
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
    public void showPhotoActivity(int albumId) {
        Intent intent = new Intent(this , PhotoActivity.class);
        intent.putExtra(ALBUM_ID , albumId);
        startActivity(intent);
    }

    @Override
    public void makeToast(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(int item) {
        presenter.showPhotoActivity(item);
    }

    @Override
    protected void onDestroy() {
        presenter.clean();
        super.onDestroy();
    }
}
