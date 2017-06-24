package com.yangxiong.gisuper.mideaplayer;

import android.animation.Animator;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.yangxiong.gisuper.mideaplayer.audio.AudioFragment;
import com.yangxiong.gisuper.mideaplayer.base.BaseActivity;
import com.yangxiong.gisuper.mideaplayer.global.GlobalConfig;
import com.yangxiong.gisuper.mideaplayer.global.LogUtil;
import com.yangxiong.gisuper.mideaplayer.model.ImageBean;
import com.yangxiong.gisuper.mideaplayer.model.StorageDataManager;
import com.yangxiong.gisuper.mideaplayer.photo.PhotoFragment;
import com.yangxiong.gisuper.mideaplayer.video.VideoFragment;

import java.util.ArrayList;
import java.util.List;

import yalantis.com.sidemenu.interfaces.Resourceble;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.model.SlideMenuItem;
import yalantis.com.sidemenu.util.ViewAnimator;


public class MainActivity extends BaseActivity implements ViewAnimator.ViewAnimatorListener {

    private static final String TAG = "MainActivity";
    private SparseArray<ImageBean> mImageList;
    private StorageDataManager mDataManager;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private List<SlideMenuItem> list = new ArrayList<>( );
    private ContentFragment contentFragment;
    private ViewAnimator viewAnimator;
    private int res = R.drawable.content_music;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow( ).setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow( ).addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_main);
        LogUtil.d(TAG, "BaseActivity: onCreate ");
        initData( );
    }

    public SparseArray<ImageBean> getmImageList() {
        // if (mImageList != null)
        return mImageList;
        // else {
        //   throw new RuntimeException("!!!!!!RuntimeException");
        //}
    }

    private void initData() {
        mDataManager = StorageDataManager.getInstance(this);
        mImageList = mDataManager.getImageList( );

        contentFragment = ContentFragment.newInstance(R.drawable.content_music);
        getSupportFragmentManager( ).beginTransaction( )
                .replace(R.id.content_frame, contentFragment)
                .commit( );
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        linearLayout = (LinearLayout) findViewById(R.id.left_drawer);
        linearLayout.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers( );
            }
        });


        setActionBar( );
        createMenuList( );
        viewAnimator = new ViewAnimator<>(this, list, contentFragment, drawerLayout, this);
    }

    private void createMenuList() {
        SlideMenuItem menuItem0 = new SlideMenuItem(GlobalConfig.CLOSE, R.drawable.icn_close);
        list.add(menuItem0);
        SlideMenuItem menuItem = new SlideMenuItem(GlobalConfig.PHOTO, R.drawable.icn_1);
        list.add(menuItem);
        SlideMenuItem menuItem2 = new SlideMenuItem(GlobalConfig.AUDIO, R.drawable.icn_2);
        list.add(menuItem2);
        SlideMenuItem menuItem3 = new SlideMenuItem(GlobalConfig.VIDEO, R.drawable.icn_3);
        list.add(menuItem3);
        SlideMenuItem menuItem4 = new SlideMenuItem(GlobalConfig.CASE, R.drawable.icn_4);
        list.add(menuItem4);
        SlideMenuItem menuItem5 = new SlideMenuItem(GlobalConfig.SHOP, R.drawable.icn_5);
        list.add(menuItem5);
        SlideMenuItem menuItem6 = new SlideMenuItem(GlobalConfig.PARTY, R.drawable.icn_6);
        list.add(menuItem6);
        SlideMenuItem menuItem7 = new SlideMenuItem(GlobalConfig.MOVIE, R.drawable.icn_7);
        list.add(menuItem7);
    }

    private void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        FrameLayout.LayoutParams param = (FrameLayout.LayoutParams) toolbar.getLayoutParams( );
        param.setMargins(0, 51, 0, 0);
        toolbar.setLayoutParams(param);
        toolbar.setBackgroundColor(Color.TRANSPARENT);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar( ).setHomeButtonEnabled(true);
        getSupportActionBar( ).setDisplayHomeAsUpEnabled(true);
        drawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                toolbar,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                linearLayout.removeAllViews( );
                linearLayout.invalidate( );
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (slideOffset > 0.6 && linearLayout.getChildCount( ) == 0)
                    viewAnimator.showMenuContent( );
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 0: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted
                    // request successfully, handle you transactions
                    mDataManager = StorageDataManager.getInstance(this);
                    mImageList = mDataManager.getImageList( );
                } else {
                    // permission denied
                    // request failed
                }
                return;
            }
            default:
                break;
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState( );
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater( ).inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId( )) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private ScreenShotable replaceFragment(Resourceble slideMenuItem, ScreenShotable screenShotable, int topPosition) {
        this.res = this.res == R.drawable.content_music ? R.drawable.content_films : R.drawable.content_music;
        View view = findViewById(R.id.content_frame);
        int finalRadius = Math.max(view.getWidth( ), view.getHeight( ));
        Animator animator = ViewAnimationUtils.createCircularReveal(view, 0, topPosition, 0, finalRadius);
        animator.setInterpolator(new AccelerateInterpolator( ));
        animator.setDuration(ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);

        findViewById(R.id.content_overlay).setBackgroundDrawable(new BitmapDrawable(getResources( ), screenShotable.getBitmap( )));
        animator.start( );
        // ContentFragment contentFragment = ContentFragment.newInstance(this.res);
        switch (slideMenuItem.getName( )) {
            case GlobalConfig.PHOTO:
                PhotoFragment photoFragment = PhotoFragment.newInstance(this.res);
                getSupportFragmentManager( ).beginTransaction( ).replace(R.id.content_frame, photoFragment).commit( );
                return photoFragment;
            case GlobalConfig.AUDIO:
                AudioFragment audioFragment = AudioFragment.newInstance(this.res);
                getSupportFragmentManager( ).beginTransaction( ).replace(R.id.content_frame, audioFragment).commit( );
                return audioFragment;
            case GlobalConfig.VIDEO:
                VideoFragment videoFragment = VideoFragment.newInstance(this.res);
                getSupportFragmentManager( ).beginTransaction( ).replace(R.id.content_frame, videoFragment).commit( );
                return videoFragment;

        }

        return null;
    }

    @Override
    public ScreenShotable onSwitch(Resourceble slideMenuItem, ScreenShotable screenShotable, int position) {
        LogUtil.d(TAG, "itemName:" + slideMenuItem.getName( ));
        switch (slideMenuItem.getName( )) {
            case GlobalConfig.CLOSE:
                return screenShotable;
            default:
                return replaceFragment(slideMenuItem, screenShotable, position);
        }
    }

    @Override
    public void disableHomeButton() {
        getSupportActionBar( ).setHomeButtonEnabled(false);

    }

    @Override
    public void enableHomeButton() {
        getSupportActionBar( ).setHomeButtonEnabled(true);
        drawerLayout.closeDrawers( );

    }

    @Override
    public void addViewToContainer(View view) {
        linearLayout.addView(view);
    }

}
