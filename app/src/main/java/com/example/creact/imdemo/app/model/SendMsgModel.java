package com.example.creact.imdemo.app.model;

import com.example.creact.imdemo.app.data.MessageData;
import com.example.creact.imdemo.core.callback.SendMessageCallback;
import com.example.creact.imdemo.core.callback.SendResponse;

import java.util.Map;
import java.util.Random;

/**
 * Created by Administrator on 2015/11/28.
 */
public class SendMsgModel {

    public void send(Map<String,Object> args,MessageData messageData,SendMessageCallback<MessageData> callback){
        Random random = new Random();
        for(int i=0;i<10000;i++)
        if(random.nextBoolean()){
            messageData.setId(10000);
            SendResponse<MessageData> response = new SendResponse<>();
            response.setObj(messageData);
            response.setSuccess(true);
            callback.onSendSucess(response);
        }
        else{
            SendResponse<MessageData> response = new SendResponse<>();
            response.setObj(messageData);
            response.setSuccess(false);
            response.setMessage("send failed");
            callback.onSendFailed(response);
        }
    }
}
