package com.yangxiong.gisuper.mideaplayer;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yangxiong.gisuper.mideaplayer.model.ImageBean;

import yalantis.com.sidemenu.interfaces.ScreenShotable;

/**
 * Created by yangxiong on 2017/6/17/017.
 */


public class ContentFragment extends Fragment implements ScreenShotable {
    public static final String CLOSE = "Close";
    public static final String BUILDING = "Building";
    public static final String BOOK = "Book";
    public static final String PAINT = "Paint";
    public static final String CASE = "Case";
    public static final String SHOP = "Shop";
    public static final String PARTY = "Party";
    public static final String MOVIE = "Movie";

    private View containerView;
    protected ImageView mImageView;
    protected int res;
    private Bitmap bitmap;
    private RecyclerView rvTest;
    private MainActivity mMainActivity;
    private SparseArray<ImageBean> mImageList;

    public static ContentFragment newInstance(int resId) {
        ContentFragment contentFragment = new ContentFragment( );
        Bundle bundle = new Bundle( );
        bundle.putInt(Integer.class.getName( ), resId);
        contentFragment.setArguments(bundle);
        return contentFragment;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.containerView = view.findViewById(R.id.container);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        res = getArguments( ).getInt(Integer.class.getName( ));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mImageView = (ImageView) rootView.findViewById(R.id.image_content);
        ////////////////////
        rvTest = (RecyclerView) rootView.findViewById(R.id.rv_test);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(getContext(),3);
        //linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvTest.setLayoutManager(linearLayoutManager);
        ////////////////////////
        mImageView.setClickable(true);
        mImageView.setFocusable(true);
        mImageView.setImageResource(res);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mMainActivity = (MainActivity) getContext( );
    }

    @Override
    public void onStart() {
        super.onStart( );
        mImageList = mMainActivity.getmImageList( );
        PhotoAdapter adapter = new PhotoAdapter(mImageList,mMainActivity);
        rvTest.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void takeScreenShot() {
        Thread thread = new Thread( ) {
            @Override
            public void run() {
                Bitmap bitmap = Bitmap.createBitmap(containerView.getWidth( ),
                        containerView.getHeight( ), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                containerView.draw(canvas);
                ContentFragment.this.bitmap = bitmap;
            }
        };

        thread.start( );

    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }
}


