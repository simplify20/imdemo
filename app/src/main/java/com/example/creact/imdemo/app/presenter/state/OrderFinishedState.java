package com.example.creact.imdemo.app.presenter.state;

import com.example.creact.imdemo.app.data.MessageData;
import com.example.creact.imdemo.app.data.QuestionData;
import com.example.creact.imdemo.app.model.LoadQuestionsModel;
import com.example.creact.imdemo.app.presenter.DataCallBack;
import com.example.creact.imdemo.app.presenter.LoopAndPush;
import com.example.creact.imdemo.core.callback.ConnectCallback;
import com.example.creact.imdemo.core.callback.ReceiveMessageCallback;
import com.example.creact.imdemo.core.callback.SendMessageCallback;
import com.example.creact.imdemo.core.strategy.StrategyContext;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/11/29.
 */
public class OrderFinishedState implements ChatState {

    private StrategyContext<MessageData> context;
    private LoadQuestionsModel loadQuestionsModel;

    public OrderFinishedState() {
        this.context = new StrategyContext<>(new LoopAndPush());
        loadQuestionsModel = new LoadQuestionsModel();
    }

    @Override
    public void connectServer(Map<String, Object> args, ConnectCallback callback) {
        //if local session id is null ,then connect,else do nothing
        context.connect(args,callback);
    }

    @Override
    public void sendMessage(Map<String, Object> args, MessageData messageData, SendMessageCallback<MessageData> callback) {
        //do nothing
    }

    @Override
    public void receiveMessage(Map<String, Object> args,ReceiveMessageCallback<List<MessageData>> callback) {

        //do nothing
    }

    @Override
    public void loadQuestions(Map<String, Object> args,DataCallBack<List<QuestionData>> callBack) {
        //load only when messageId==0
    }
}
