package com.yangxiong.gisuper.mideaplayer.video;


import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

import com.yangxiong.gisuper.mideaplayer.R;
import com.yangxiong.gisuper.mideaplayer.base.BaseFragment;
import com.yangxiong.gisuper.mideaplayer.model.StorageDataManager;
import com.yangxiong.gisuper.mideaplayer.model.VideoBean;

/**
 * Created by yangxiong on 2017/6/17/017.
 */


public class VideoFragment extends BaseFragment {

    public static VideoFragment newInstance(int resId) {
        VideoFragment contentFragment = new VideoFragment( );
        Bundle bundle = new Bundle( );
        bundle.putInt(Integer.class.getName( ), resId);
        contentFragment.setArguments(bundle);
        return contentFragment;
    }

    @Override
    protected void setRecyclerView(View rootView) {
        rvRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_photo);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext( ),
                LinearLayoutManager.VERTICAL, false);
        rvRecyclerView.setLayoutManager(linearLayoutManager);
        rvRecyclerView.addItemDecoration(new DividerItemDecoration(getContext( ),
                DividerItemDecoration.VERTICAL));
    }

    @Override
    protected void setAdapter() {
        SparseArray<VideoBean> mAudioList = StorageDataManager.getInstance
                (mMainActivity).getVideoList();
        VideoAdapter adapter = new VideoAdapter(mAudioList, mMainActivity);
        rvRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged( );
    }


}


