package com.yangxiong.gisuper.mideaplayer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.ybq.parallaxviewpager.Mode;
import com.github.ybq.parallaxviewpager.ParallaxViewPager;
import com.yangxiong.gisuper.mideaplayer.global.LogUtil;

import me.relex.circleindicator.CircleIndicator;

public class WelcomeActivity extends AppCompatActivity {

    private static final String TAG = "WelcomeActivity";
    private ParallaxViewPager mParallaxViewPager;
    @SuppressWarnings("SpellCheckingInspection")
    private String[] mImages = new String[]{
            "https://drscdn.500px.org/photo/127892951/h%3D600_k%3D1_a%3D1/3487a549dbbe46e2d803a37281543322",
            "https://drscdn.500px.org/photo/127893495/h%3D600_k%3D1_a%3D1/8462ac67727eecd23c104612ab998633",
            "https://drscdn.500px.org/photo/127891921/h%3D600_k%3D1_a%3D1/c5aec47c6c924d733f58cec483dc41a6",
            "https://drscdn.500px.org/photo/127900863/h%3D600_k%3D1_a%3D1/e63c59888014392bac32cfb9c383bb9e",
            "https://drscdn.500px.org/photo/127870627/h%3D600_k%3D1_a%3D1/df562860314d42dd9a4f8bf4ee0ac0e5",
    };
    private CircleIndicator mIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d(TAG, "WelcomeActivity: onCreate ");
        setContentView(R.layout.activity_welcome);
        mIndicator = (CircleIndicator) findViewById(R.id.indicator);
        initViewPager( );
        mParallaxViewPager.setMode(Mode.LEFT_OVERLAY);
        mParallaxViewPager.setCurrentItem(2,true);
        mParallaxViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener( ) {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                LogUtil.d(TAG,"position: "+position+"    mImages.length: "+mImages.length);
                if (mImages.length-1 == position){
                    startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        requestReadExternalPermission();
    }

    private void initViewPager() {
        mParallaxViewPager = (ParallaxViewPager) findViewById(R.id.pager);

        PagerAdapter adapter = new PagerAdapter( ) {

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object obj) {
                container.removeView((View) obj);
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view = View.inflate(container.getContext( ), R.layout.pager_item, null);
                ImageView imageView = (ImageView) view.findViewById(R.id.item_img);
                Glide.with(WelcomeActivity.this).load(mImages[position % mImages.length]).into(imageView);
                container.addView(view, ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                return view;
            }

            @Override
            public int getCount() {
                return mImages.length;
            }
        };
        mParallaxViewPager.setAdapter(adapter);
        mIndicator.setViewPager(mParallaxViewPager);
    }

    private void requestReadExternalPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                LogUtil.d(TAG, "READ permission IS NOT granted...");

                if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    LogUtil.d(TAG, "11111111111111");
                } else {
                    // 0 是自己定义的请求coude
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
                    LogUtil.d(TAG, "222222222222");
                }
            } else {
                LogUtil.d(TAG, "READ permission is granted...");


            }
        } else {
            LogUtil.d(TAG, "SDK Version is : " + Build.VERSION.SDK_INT);


        }


    }
}
