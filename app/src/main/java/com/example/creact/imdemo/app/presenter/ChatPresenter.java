package com.example.creact.imdemo.app.presenter;

import com.example.creact.imdemo.app.data.MessageData;
import com.example.creact.imdemo.app.data.QuestionData;
import com.example.creact.imdemo.app.presenter.state.ChatState;
import com.example.creact.imdemo.app.view.ChatView;
import com.example.creact.imdemo.core.callback.ConnectCallback;
import com.example.creact.imdemo.core.callback.ReceiveMessageCallback;
import com.example.creact.imdemo.core.callback.SendMessageCallback;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/11/28.
 */
public class ChatPresenter{
    private ChatView view;
    private ChatState state;

    public ChatPresenter(ChatView view,ChatState state) {
        this.view = view;
        this.state = state;

    }

    public ChatState getState() {
        return state;
    }

    public void setState(ChatState state) {
        this.state = state;
    }

    public void connectServer(Map<String, Object> args,ConnectCallback callback){
        state.connectServer(args,callback);
    }

    public void sendMessage(Map<String, Object> args,MessageData messageData,SendMessageCallback<MessageData> callback){
        state.sendMessage(args, messageData, callback);
    }

    public void receiveMessage(Map<String, Object> args){
        state.receiveMessage(args, new ReceiveMessageCallback<List<MessageData>>() {
            @Override
            public void receive(List<MessageData> message) {
                view.receive(message);
            }
        });
    }

    public void loadQuestions(Map<String, Object> args){

        state.loadQuestions(args, new DataCallBack<List<QuestionData>>() {
            @Override
            public void onDataArrive(List<QuestionData> data) {
                view.showQuestions(data);
            }
        });
    }




}
