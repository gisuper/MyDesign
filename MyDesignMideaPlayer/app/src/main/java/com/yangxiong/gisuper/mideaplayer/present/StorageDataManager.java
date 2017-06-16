package com.yangxiong.gisuper.mideaplayer.present;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.SparseArray;

import com.yangxiong.gisuper.mideaplayer.global.LogUtil;
import com.yangxiong.gisuper.mideaplayer.model.ImageBean;

/**
 * Created by yangxiong on 2017/6/16.
 */

public class StorageDataManager {
    private static final String TAG = "StorageDataManager";
    private static StorageDataManager instance;
    private Context context;
    private final ContentResolver mResolver;

    private StorageDataManager(Context context) {
        this.context = context;
        mResolver = context.getContentResolver( );
    }

    public static StorageDataManager getInstance(Context context) {
        if (instance == null) {
            synchronized (LogUtil.class) {
                if (instance == null) {
                    instance = new StorageDataManager(context);
                }
            }
        }
        return instance;
    }


    /**获取所有的图片
     * @return
     */
    public SparseArray<ImageBean> getImageList() {
        SparseArray<ImageBean> list = new SparseArray<>( );
        String[] projection = {MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATA};
        String orderBy = MediaStore.Images.Media.DISPLAY_NAME;
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = mResolver.query(uri, projection, null, null, orderBy);
        if (null == cursor) {
            LogUtil.d(TAG, "getImageList:  cursor is null");
            return null;
        }
        int i = 0;
        while (cursor.moveToNext( )) {
            String id = cursor.getString(0);
            String name = cursor.getString(1);
            String path = cursor.getString(2);

            LogUtil.d(TAG, "id: " + id + "name: " + name + "path: " + path);
            ImageBean imageBean = new ImageBean( );
            imageBean.autoId = i++;
            imageBean.id = id;
            imageBean.name = name;
            imageBean.path = path;
            list.put(imageBean.autoId, imageBean);

        }
        return list;
    }
}
