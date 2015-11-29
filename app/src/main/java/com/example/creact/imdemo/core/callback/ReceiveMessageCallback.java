package com.example.creact.imdemo.core.callback;

/**
 *
 * Created by Administrator on 2015/11/28.
 */
public interface ReceiveMessageCallback<T> {

    void receive(T message);
}
