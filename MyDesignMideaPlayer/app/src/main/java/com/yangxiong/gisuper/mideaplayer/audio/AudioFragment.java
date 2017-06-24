package com.yangxiong.gisuper.mideaplayer.audio;


import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

import com.yangxiong.gisuper.mideaplayer.R;
import com.yangxiong.gisuper.mideaplayer.base.BaseFragment;
import com.yangxiong.gisuper.mideaplayer.model.AudioBean;
import com.yangxiong.gisuper.mideaplayer.model.StorageDataManager;

/**
 * Created by yangxiong on 2017/6/17/017.
 */


public class AudioFragment extends BaseFragment {

    public static AudioFragment newInstance(int resId) {
        AudioFragment contentFragment = new AudioFragment( );
        Bundle bundle = new Bundle( );
        bundle.putInt(Integer.class.getName( ), resId);
        contentFragment.setArguments(bundle);
        return contentFragment;
    }

    @Override
    protected void setRecyclerView(View rootView) {
         rvRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_photo);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL,false);
        rvRecyclerView.setLayoutManager(linearLayoutManager);
        rvRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
    }

    @Override
    protected void setAdapter() {
        SparseArray<AudioBean> mAudioList = StorageDataManager.getInstance(mMainActivity).getAudioList( );
        AudioAdapter adapter = new AudioAdapter(mAudioList,mMainActivity);
        rvRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


}


