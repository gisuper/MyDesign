package com.yangxiong.gisuper.mideaplayer.global;

import android.app.Application;

/**
 * Created by yangxiong on 2017/6/16.
 */

public class GlobalApplication extends Application {
    private static final String TAG = "GlobalApplication";

    public GlobalApplication() {
        super( );
        LogUtil.d(TAG, "GlobalApplication() created");
    }

    @Override
    public void onCreate() {
        super.onCreate( );
        LogUtil.d(TAG, "onCreate() created");
    }


}
