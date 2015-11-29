package com.example.creact.imdemo.app.view;

import com.example.creact.imdemo.app.data.MessageData;
import com.example.creact.imdemo.app.data.QuestionData;
import com.example.creact.imdemo.core.callback.ReceiveMessageCallback;
import com.example.creact.imdemo.core.callback.SendMessageCallback;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/11/28.
 */
public interface ChatView {

    void receive(List<MessageData> dataList);
    void showQuestions(List<QuestionData> questionDatas);

}
