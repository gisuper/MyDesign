package com.yangxiong.gisuper.mideaplayer;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.SparseArray;

import com.yangxiong.gisuper.mideaplayer.global.BaseActivity;
import com.yangxiong.gisuper.mideaplayer.global.LogUtil;
import com.yangxiong.gisuper.mideaplayer.model.ImageBean;
import com.yangxiong.gisuper.mideaplayer.present.StorageDataManager;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LogUtil.d(TAG,"BaseActivity: onCreate ");
        requestReadExternalPermission( );

        StorageDataManager instance = StorageDataManager.getInstance(this);
        SparseArray<ImageBean> imageList = instance.getImageList( );
        LogUtil.d(TAG,"imageListSize:  "+imageList.size());
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
        }else{
            LogUtil.d(TAG, "SDK Version is : "+ Build.VERSION.SDK_INT );
        }
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
}
