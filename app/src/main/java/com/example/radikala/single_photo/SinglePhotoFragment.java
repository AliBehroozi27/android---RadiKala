package com.example.radikala.single_photo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.radikala.R;
import com.example.radikala.single_photo.contract.SinglePhotoContract;
import com.squareup.picasso.Picasso;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SinglePhotoFragment extends Fragment implements SinglePhotoContract.View {
    @BindView(R.id.image_view)
    ImageView singlePhoto;
    @BindView(R.id.progress_bar)
    ProgressBar loadingProgressBar;

    private static final String PHOTO = "photo";
    private String photoUrl;
    private SinglePhotoPresenter presenter;


    public SinglePhotoFragment() {
    }

    public static SinglePhotoFragment newInstance(String photoUrl) {
        SinglePhotoFragment fragment = new SinglePhotoFragment();
        Bundle args = new Bundle();
        args.putString(PHOTO, photoUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            photoUrl = getArguments().getString(PHOTO);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo, container, false);
        ButterKnife.bind(this, view);
        presenter = new SinglePhotoPresenter(this);
        presenter.setPhotoUrl(photoUrl);
        presenter.showPhoto();
        return view;
    }

    @Override
    public void displayProgressBar(Boolean showFlag) {
        if (showFlag)
            loadingProgressBar.setVisibility(View.VISIBLE);
        else
            loadingProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showPhoto(String photoUrl) {
        Picasso.get().load(photoUrl).into(singlePhoto);
        presenter.displayProgressBar(false);
    }

    @Override
    public void onDestroy() {
        presenter.clean();
        super.onDestroy();
    }
}
