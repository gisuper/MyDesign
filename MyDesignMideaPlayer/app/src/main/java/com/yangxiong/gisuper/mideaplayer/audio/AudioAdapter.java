package com.yangxiong.gisuper.mideaplayer.audio;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import com.yangxiong.gisuper.mideaplayer.base.BaseAdapter;
import com.yangxiong.gisuper.mideaplayer.model.AudioBean;

import java.io.IOException;

/**
 * Created by yangxiong on 2017/6/17/017.
 */

class AudioAdapter extends BaseAdapter {

    private MediaPlayer player;

    public AudioAdapter(SparseArray<AudioBean> mList, Context context) {
        super(mList, context);
    }

    @Override
    protected void initData() {
        player = new MediaPlayer( );
    }
    @Override
    public void onBindViewHolder(BaseHolder holder, final int position) {
        final AudioBean audioBean = (AudioBean) mList.get(position);

        TextView itemView = (TextView) holder.itemView;
        itemView.setText(audioBean.autoId+"       "+audioBean.tittle);
        itemView.setFocusable(true);
        itemView.setTextColor(Color.WHITE);
        itemView.setClickable(true);
        itemView.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
               /* Intent intent = new Intent(context, PhotoShowActivity.class);
                intent.putExtra("position", position);
                context.startActivity(intent);*/

                try {
                    if (player.isPlaying( )) {
                        player.stop( );
                        player.release( );
                        player = new MediaPlayer( );
                    }
                    player.setDataSource(audioBean.path);
                    player.prepare( );

                    player.setOnPreparedListener(new MediaPlayer.OnPreparedListener( ) {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mp.start( );
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace( );
                }
            }
        });

    }
}
