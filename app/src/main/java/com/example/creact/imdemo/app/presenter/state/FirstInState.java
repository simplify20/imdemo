package com.example.creact.imdemo.app.presenter.state;

import com.example.creact.imdemo.app.data.MessageData;
import com.example.creact.imdemo.app.data.QuestionData;
import com.example.creact.imdemo.app.model.LoadQuestionsModel;
import com.example.creact.imdemo.app.presenter.DataCallBack;
import com.example.creact.imdemo.app.presenter.LoopAndPush;
import com.example.creact.imdemo.app.presenter.state.ChatState;
import com.example.creact.imdemo.core.callback.ConnectCallback;
import com.example.creact.imdemo.core.callback.ReceiveMessageCallback;
import com.example.creact.imdemo.core.callback.SendMessageCallback;
import com.example.creact.imdemo.core.strategy.StrategyContext;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/11/29.
 */
public class FirstInState implements ChatState {
    private StrategyContext<MessageData> context;
    private LoadQuestionsModel loadQuestionsModel;
    public FirstInState() {
        this.context = new StrategyContext<>(new LoopAndPush());
        loadQuestionsModel = new LoadQuestionsModel();
    }

    @Override
    public void connectServer(Map<String, Object> args, ConnectCallback callback) {
        context.connect(args,callback);
        //store session id
    }

    @Override
    public void sendMessage(Map<String, Object> args, MessageData messageData, SendMessageCallback<MessageData> callback) {
        context.send(args,messageData,callback);
    }

    @Override
    public void receiveMessage(Map<String, Object> args, ReceiveMessageCallback<List<MessageData>> callback) {
        context.receive(args,callback);
    }

    @Override
    public void loadQuestions(Map<String, Object> args,DataCallBack<List<QuestionData>> callBack) {
        loadQuestionsModel.loadQuestions(args,callBack);
    }
}
