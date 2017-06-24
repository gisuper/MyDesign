package com.yangxiong.gisuper.mideaplayer.model;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.SparseArray;

import com.yangxiong.gisuper.mideaplayer.global.LogUtil;

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


    /**
     * 获取所有的图片
     *
     * @return SparseArray<ImageBean>
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

            ImageBean imageBean = new ImageBean( );
            imageBean.autoId = i++;
            imageBean.id = id;
            imageBean.name = name;
            imageBean.path = path;
            list.put(imageBean.autoId, imageBean);

        }
        LogUtil.d(TAG, "listSize: " + list.size( ));
        return list;
    }

    public SparseArray<AudioBean> getAudioList() {
        SparseArray<AudioBean> list = new SparseArray<>( );

        String[] projection = {MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.SIZE,MediaStore.Audio.Media.TITLE};
        String orderBy = MediaStore.Audio.Media.DISPLAY_NAME;
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

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
            String tittle = cursor.getString(4);
            AudioBean audioBean = new AudioBean( );
            audioBean.autoId = i++;
            audioBean.id = id;
            audioBean.name = name;
            audioBean.path = path;
            audioBean.tittle = tittle;
            list.put(audioBean.autoId, audioBean);

        }
        LogUtil.d(TAG, "listSize: " + list.size( ));
        return list;
    }
    public SparseArray<VideoBean> getVideoList() {
        SparseArray<VideoBean> list = new SparseArray<>( );

        String[] projection = {MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.SIZE,MediaStore.Video.Media.TITLE};
        String orderBy = MediaStore.Video.Media.ARTIST;
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

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
            String tittle = cursor.getString(4);
            VideoBean videoBean = new VideoBean( );
            videoBean.autoId = i++;
            videoBean.id = id;
            videoBean.name = name;
            videoBean.path = path;
            videoBean.tittle = tittle;
            list.put(videoBean.autoId, videoBean);

        }
        LogUtil.d(TAG, "listSize: " + list.size( ));
        return list;
    }

}
