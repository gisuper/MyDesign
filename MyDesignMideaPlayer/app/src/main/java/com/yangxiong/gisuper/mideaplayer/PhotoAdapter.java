package com.yangxiong.gisuper.mideaplayer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.yangxiong.gisuper.mideaplayer.global.LogUtil;
import com.yangxiong.gisuper.mideaplayer.model.ImageBean;

import java.io.File;

/**
 * Created by yangxiong on 2017/6/17/017.
 */

class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoHolder> {
    private static final String TAG = "PhotoAdapter";
    private SparseArray<ImageBean> mImageList;
    private MainActivity context;

    public PhotoAdapter(SparseArray<ImageBean> mImageList, Context context) {
        this.mImageList = mImageList;
        this.context = (MainActivity) context;
    }

    @Override
    public PhotoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ImageView itemView = new ImageView(context);
        return new PhotoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PhotoHolder holder, int position) {
        ImageBean imageBean = mImageList.get(position);
        String path = imageBean.path;
        File file = new File(path);
        LogUtil.d(TAG, path);

        //获取屏幕宽度
        DisplayMetrics outMetrics = new DisplayMetrics( );
        WindowManager manager = context.getWindowManager( );
        manager.getDefaultDisplay( ).getMetrics(outMetrics);
        int width = outMetrics.widthPixels / 2;

//按宽度等比例缩放，不然会OOM
        int[] width_height = getImageWidthHeight(path);
        LogUtil.d(TAG,"width: "+width_height[0]+"height: "+width_height[1]);
        float ratio = (float) ((width_height[0] * 1.0) / (width * 1.0));
        int height = (int) (width_height[1] * 1.0 / ratio);

      /* *//* Glide.with(itemView.getContext())
                .load(url)
                .asBitmap()
                .placeholder(R.drawable.error_pic)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .override(width,height)*//*
        BitmapFactory.Options options = new BitmapFactory.Options( );
        *//**
         * 最关键在此，把options.inJustDecodeBounds = true;
         * 这里再decodeFile()，返回的bitmap为空，但此时调用options.outHeight时，已经包含了图片的高了
         *//*

        LogUtil.d(TAG,"width1: "+width+"height1: "+height);
        options.inJustDecodeBounds = false;
        options.outWidth = 50;
        options.outHeight = 50;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options); // 此时返回的bitmap为null
        holder.itemView.setImageBitmap(bitmap);*/
        RequestBuilder<Drawable> thumbnail = Glide
                .with(context)
                .load(file)
                .thumbnail(0.2f);
                thumbnail.into(holder.itemView);

    }

    @Override
    public int getItemCount() {
        return mImageList.size();
    }

    //在不加载图片情况下获取图片大小
    public static int[] getImageWidthHeight(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options( );
        /**
         * 最关键在此，把options.inJustDecodeBounds = true;
         * 这里再decodeFile()，返回的bitmap为空，但此时调用options.outHeight时，已经包含了图片的高了
         */
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options); // 此时返回的bitmap为null
        /**
         *options.outHeight为原始图片的高
         */
        return new int[]{options.outWidth, options.outHeight};
    }

    class PhotoHolder extends RecyclerView.ViewHolder {
        ImageView itemView;

        public PhotoHolder(View itemView) {
            super(itemView);
            this.itemView = (ImageView) itemView;
        }


    }
}
