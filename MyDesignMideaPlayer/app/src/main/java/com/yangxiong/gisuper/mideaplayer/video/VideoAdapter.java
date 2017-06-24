package com.yangxiong.gisuper.mideaplayer.video;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import com.yangxiong.gisuper.mideaplayer.VideoShowActivity;
import com.yangxiong.gisuper.mideaplayer.base.BaseAdapter;
import com.yangxiong.gisuper.mideaplayer.model.VideoBean;

/**
 * Created by yangxiong on 2017/6/17/017.
 */

class VideoAdapter extends BaseAdapter {

    private MediaPlayer player;

    public VideoAdapter(SparseArray<VideoBean> mList, Context context) {
        super(mList, context);
    }

    @Override
    protected void initData() {
        player = new MediaPlayer( );
    }
    @Override
    public void onBindViewHolder(BaseHolder holder, final int position) {
        final VideoBean videoBean = (VideoBean) mList.get(position);

        TextView itemView = (TextView) holder.itemView;
        itemView.setText(videoBean.autoId+"       "+videoBean.tittle);
        itemView.setFocusable(true);
        itemView.setTextColor(Color.WHITE);
        itemView.setClickable(true);
        itemView.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(context, VideoShowActivity.class);
                intent.putExtra("position", position);
                context.startActivity(intent);

            }
        });

    }
}
