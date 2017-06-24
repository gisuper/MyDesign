package com.yangxiong.gisuper.mideaplayer.photo;


import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

import com.yangxiong.gisuper.mideaplayer.R;
import com.yangxiong.gisuper.mideaplayer.base.BaseFragment;
import com.yangxiong.gisuper.mideaplayer.model.ImageBean;
import com.yangxiong.gisuper.mideaplayer.widget.DividerGridItemDecoration;

/**
 * Created by yangxiong on 2017/6/17/017.
 */


public class PhotoFragment extends BaseFragment {


    public static PhotoFragment newInstance(int resId) {
        PhotoFragment contentFragment = new PhotoFragment( );
        Bundle bundle = new Bundle( );
        bundle.putInt(Integer.class.getName( ), resId);
        contentFragment.setArguments(bundle);
        return contentFragment;
    }

    @Override
    protected void setRecyclerView(View rootView) {
        rvRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_photo);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(getContext(),3);
        rvRecyclerView.setLayoutManager(linearLayoutManager);
        rvRecyclerView.addItemDecoration(new DividerGridItemDecoration(getContext()));
    }

    @Override
    protected void setAdapter() {
        SparseArray<ImageBean> mImageList = mMainActivity.getmImageList( );
        PhotoAdapter adapter = new PhotoAdapter(mImageList,mMainActivity);
        rvRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}


