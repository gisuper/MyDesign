package com.yangxiong.gisuper.mideaplayer.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yangxiong.gisuper.mideaplayer.MainActivity;


/**
 * Created by yangxiong on 2017/6/24/024.
 */

public abstract class BaseAdapter extends RecyclerView.Adapter<BaseAdapter.BaseHolder> {
    protected String TAG = getClass( ).getSimpleName( ).toString( );
    protected SparseArray mList;
    protected MainActivity context;

    public BaseAdapter(SparseArray mList, Context context) {

        this.mList = mList;
        this.context = (MainActivity) context;
        initData( );
    }

    protected void initData() {
    }

    @Override
    public int getItemCount() {
        return mList.size( );
    }


    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = new TextView(context );
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        itemView.setLayoutParams(lp);
        return new BaseHolder(itemView);
    }



    protected class BaseHolder extends RecyclerView.ViewHolder {
        public View itemView;

        public BaseHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
        }


    }
}
