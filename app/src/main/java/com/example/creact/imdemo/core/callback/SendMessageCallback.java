package com.example.creact.imdemo.core.callback;

/**
 * Created by Administrator on 2015/11/28.
 */
public interface SendMessageCallback<T> {
    void onSendSucess(SendResponse<T> response);
    void onSendFailed(SendResponse<T> response);
}
