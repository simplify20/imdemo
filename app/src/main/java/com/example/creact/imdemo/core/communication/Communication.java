package com.example.creact.imdemo.core.communication;

import com.example.creact.imdemo.core.callback.ConnectCallback;
import com.example.creact.imdemo.core.callback.ReceiveMessageCallback;
import com.example.creact.imdemo.core.callback.SendMessageCallback;

import java.util.List;
import java.util.Map;

/**
 * 底层策略实现
 * Created by Administrator on 2015/11/28.
 */
public interface Communication<T> {

    void connect(Map<String,Object> args,ConnectCallback callback);
    void send(Map<String,Object> args,T message,SendMessageCallback<T> callback);
    void receive(Map<String,Object> args,ReceiveMessageCallback<List<T>> listener);
}
