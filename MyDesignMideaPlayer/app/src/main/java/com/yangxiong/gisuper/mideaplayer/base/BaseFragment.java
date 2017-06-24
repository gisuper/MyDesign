package com.yangxiong.gisuper.mideaplayer.base;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yangxiong.gisuper.mideaplayer.MainActivity;
import com.yangxiong.gisuper.mideaplayer.R;

import yalantis.com.sidemenu.interfaces.ScreenShotable;

/**
 * Created by yangxiong on 2017/6/17/017.
 */


public abstract class  BaseFragment extends Fragment implements ScreenShotable {

    protected View containerView;
    protected int res;
    protected Bitmap bitmap;
    protected RecyclerView rvRecyclerView;
    protected MainActivity mMainActivity;



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.containerView = view.findViewById(R.id.fl_framelayout);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        res = getArguments( ).getInt(Integer.class.getName( ));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_photo, container, false);
        ////////////////////
        setRecyclerView(rootView);
        ////////////////////////
        return rootView;
    }

    protected abstract void setRecyclerView(View rootView) ;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mMainActivity = (MainActivity) getContext( );
    }

    @Override
    public void onStart() {
        super.onStart( );
        setAdapter( );
    }

    protected abstract void setAdapter();

    @Override
    public void takeScreenShot() {
        Thread thread = new Thread( ) {
            @Override
            public void run() {
                Bitmap bitmap = Bitmap.createBitmap(containerView.getWidth( ),
                        containerView.getHeight( ), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                containerView.draw(canvas);
                BaseFragment.this.bitmap = bitmap;
            }
        };

        thread.start( );

    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }
}


