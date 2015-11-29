package com.example.creact.imdemo.core.callback;

import com.example.creact.imdemo.core.communication.Connection;

/**
 * Created by Administrator on 2015/11/28.
 */
public interface ConnectCallback {

    void onConnectFailed(Connection connection);
    void onConnectSuccess(Connection connection);
}
