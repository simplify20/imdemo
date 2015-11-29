package com.example.creact.imdemo.app.view.viewholder.data;

import com.example.creact.imdemo.app.data.MessageData;
import com.example.creact.imdemo.app.view.viewholder.MessageViewHolder;

/**
 * Created by Administrator on 2015/11/28.
 */
public class MessageBean implements DataBean<MessageData,MessageViewHolder> {

    private MessageData data;

    public MessageBean(MessageData data) {
        this.data = data;
    }

    public void setData(MessageData data) {
        this.data = data;
    }

    @Override
    public MessageData getData() {
        return data;
    }

    @Override
    public void bindData(MessageViewHolder viewHolder) {
        viewHolder.bindData(data);
    }
}
