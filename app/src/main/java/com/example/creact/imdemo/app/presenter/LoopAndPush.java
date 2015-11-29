package com.example.creact.imdemo.app.presenter;

import com.example.creact.imdemo.app.data.MessageData;
import com.example.creact.imdemo.app.data.ReceiveData;
import com.example.creact.imdemo.app.model.ConnectServerModel;
import com.example.creact.imdemo.app.model.LoopQueryModel;
import com.example.creact.imdemo.app.model.SendMsgModel;
import com.example.creact.imdemo.core.callback.ConnectCallback;
import com.example.creact.imdemo.core.callback.ReceiveMessageCallback;
import com.example.creact.imdemo.core.callback.SendMessageCallback;
import com.example.creact.imdemo.core.communication.Communication;
import com.example.creact.imdemo.core.communication.Connection;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/11/28.
 */
public class LoopAndPush implements Communication<MessageData> {

    private ConnectServerModel connectServerModel;
    private LoopQueryModel loopQueryModel;
    private SendMsgModel sendMsgModel;

    public LoopAndPush() {
        connectServerModel = new ConnectServerModel();
        loopQueryModel = new LoopQueryModel();
        sendMsgModel = new SendMsgModel();
    }

    /**
     * sync
     * @param args
     * @param callback
     */
    @Override
    public void connect(Map<String, Object> args, ConnectCallback callback) {
        Connection connection = connectServerModel.connect(args);
        if(connection.isOk()){
           callback.onConnectSuccess(connection);
        }
        else{
            callback.onConnectFailed(connection);
        }

    }

    /**
     * async
     * @param args
     * @param message
     * @param callback
     */
    @Override
    public void send(Map<String,Object> args,MessageData message, SendMessageCallback<MessageData> callback) {
        sendMsgModel.send(args,message,callback);
    }

    /**
     * sync
     * @param args
     * @param callback
     */
    @Override
    public void receive(Map<String,Object> args,ReceiveMessageCallback<List<MessageData>> callback) {
        ReceiveData receiveData = loopQueryModel.loopQuery(args);
        callback.receive(receiveData.getDataList());
    }
}
