package com.yangxiong.gisuper.mideaplayer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yangxiong.gisuper.mideaplayer.model.ImageBean;

import yalantis.com.sidemenu.interfaces.ScreenShotable;

/**
 * Created by yangxiong on 2017/6/17/017.
 */

public class PhotoFragment extends Fragment  implements ScreenShotable {


    private MainActivity mMainActivity;
    private SparseArray<ImageBean> mImageList;
    private RecyclerView rvPhoto;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = LayoutInflater.from(getContext( )).inflate(R.layout.fragment_photo, null);
        rvPhoto = (RecyclerView) rootView.findViewById(R.id.rv_photo);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvPhoto.setLayoutManager(linearLayoutManager);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mMainActivity = (MainActivity) getContext( );
        mImageList = mMainActivity.getmImageList( );

        //设置适配器
        PhotoAdapter mAdapter = new PhotoAdapter( mImageList,mMainActivity);
        rvPhoto.setAdapter(mAdapter);
    }
    private View containerView;
    protected ImageView mImageView;
    private Bitmap bitmap;
    @Override
    public void takeScreenShot() {
        containerView = new TextView(getContext());
        Thread thread = new Thread( ) {
            @Override
            public void run() {
                Bitmap bitmap = Bitmap.createBitmap(111,
                        111, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                containerView.draw(canvas);
                PhotoFragment.this.bitmap = bitmap;
    }
};

        thread.start( );
    }

    @Override
    public Bitmap getBitmap() {

        return this.bitmap;
    }
}
