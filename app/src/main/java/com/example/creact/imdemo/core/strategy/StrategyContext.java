package com.example.creact.imdemo.core.strategy;

import com.example.creact.imdemo.core.callback.ConnectCallback;
import com.example.creact.imdemo.core.callback.ReceiveMessageCallback;
import com.example.creact.imdemo.core.callback.SendMessageCallback;
import com.example.creact.imdemo.core.communication.Communication;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/11/28.
 */
public class StrategyContext<T> implements Communication<T> {

    private Communication<T> strategy;

    public StrategyContext(Communication<T> strategy) {
        this.strategy = strategy;
    }

    public Communication<T> getStrategy() {
        return strategy;
    }

    @Override
    public void connect(Map<String, Object> args,ConnectCallback callback) {
        strategy.connect(args,callback);
    }

    @Override
    public void send(Map<String,Object> args,T message, SendMessageCallback<T> callback) {
        strategy.send(args,message, callback);
    }

    @Override
    public void receive(Map<String,Object> args,ReceiveMessageCallback<List<T>> callback) {
        strategy.receive(args,callback);
    }
}
