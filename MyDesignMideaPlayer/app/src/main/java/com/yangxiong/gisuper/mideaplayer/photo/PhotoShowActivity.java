package com.yangxiong.gisuper.mideaplayer.photo;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yangxiong.gisuper.mideaplayer.R;
import com.yangxiong.gisuper.mideaplayer.global.LogUtil;
import com.yangxiong.gisuper.mideaplayer.model.ImageBean;
import com.yangxiong.gisuper.mideaplayer.model.StorageDataManager;

import java.io.File;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class PhotoShowActivity extends AppCompatActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private static final String TAG = "PhotoShowActivity";
    private final Handler mHideHandler = new Handler( );
    private final Runnable mHidePart2Runnable = new Runnable( ) {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            hvpViewPager.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable( ) {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar( );
            TextView textView = new TextView(PhotoShowActivity.this);
            textView.setTextSize(55);
            textView.setText("ActionBar");
            textView.setTextColor(Color.RED);

            if (actionBar != null) {
                actionBar.setCustomView(textView);
                actionBar.show( );
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable( ) {
        @Override
        public void run() {
            hide( );
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener( ) {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };
    private int mCurrentPosition;
    private SparseArray<ImageBean> mImageList;
    private ViewPager hvpViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_photo_show);

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);

        hvpViewPager = (ViewPager) findViewById(R.id.hvp_viewpager);


        // Set up the user interaction to manually show or hide the system UI.
        hvpViewPager.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                toggle( );
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);

        mCurrentPosition = getIntent( ).getIntExtra("position",-1);

        mImageList = StorageDataManager.getInstance(this).getImageList( );

        hvpViewPager.setAdapter(new ImageAdapter());
        //为ViewPager当前page的数字
        hvpViewPager.setCurrentItem(mCurrentPosition);

        LogUtil.d(TAG,"  mCurrentPosition:  "+ mCurrentPosition);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    private void toggle() {
        if (mVisible) {
            hide( );
        } else {
            show( );
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar( );
        if (actionBar != null) {
            actionBar.hide( );
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        hvpViewPager.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    public class ImageAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return null == mImageList ? 0 : mImageList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //创建显示图片的控件
            PhotoView photoView = new PhotoView(container.getContext());
            //设置背景颜色
            photoView.setBackgroundColor(Color.BLACK);
            //把photoView添加到viewpager中，并设置布局参数
            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            //加载当前PhtotoView要显示的数据
            ImageBean imageBean = mImageList.get(position);

            if (imageBean!=null) {
                //使用使用Glide进行加载图片进行加载图片
                Glide.with(PhotoShowActivity.this).load(new File(imageBean.path)).into(photoView);
            }

            //图片单击事件的处理
            photoView.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
                @Override
                public void onViewTap(View view, float x, float y) {
                   // finish();
                    toggle();
                }
            });
            return photoView;
        }

    }
}
