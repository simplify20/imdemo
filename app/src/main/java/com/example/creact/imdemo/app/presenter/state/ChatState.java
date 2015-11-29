package com.example.creact.imdemo.app.presenter.state;

import com.example.creact.imdemo.app.data.MessageData;
import com.example.creact.imdemo.app.data.QuestionData;
import com.example.creact.imdemo.app.presenter.DataCallBack;
import com.example.creact.imdemo.core.callback.ConnectCallback;
import com.example.creact.imdemo.core.callback.ReceiveMessageCallback;
import com.example.creact.imdemo.core.callback.SendMessageCallback;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/11/29.
 */
public interface ChatState {

    void connectServer(Map<String, Object> args,ConnectCallback callback);
    void sendMessage(Map<String, Object> args,MessageData messageData,SendMessageCallback<MessageData> callback);
    void receiveMessage(Map<String, Object> args,ReceiveMessageCallback<List<MessageData>> callback);
    void loadQuestions(Map<String,Object> args,DataCallBack<List<QuestionData>> callBack);
}
