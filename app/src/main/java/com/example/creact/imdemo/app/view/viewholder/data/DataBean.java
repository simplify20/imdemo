package com.example.creact.imdemo.app.view.viewholder.data;

import com.example.creact.imdemo.app.view.viewholder.BaseViewHolder;

/**
 * Created by Administrator on 2015/11/29.
 */
public interface DataBean<D,VH extends BaseViewHolder> {

    D getData();
    void bindData(VH viewHolder);

}
