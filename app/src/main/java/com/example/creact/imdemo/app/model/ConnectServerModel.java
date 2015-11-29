package com.example.creact.imdemo.app.model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.creact.imdemo.core.callback.ConnectCallback;
import com.example.creact.imdemo.core.communication.Connection;

import java.util.Map;
import java.util.Random;

/**
 * Created by Administrator on 2015/11/28.
 */
public class ConnectServerModel {
    private static final String TAG = ConnectServerModel.class.getSimpleName();
    public Connection connect(Map<String,Object> args){
        Log.i(TAG,"connect server...");
        String sessionId = connectServer(args);
        if(new Random().nextBoolean()){
            Log.i(TAG,"connect success");
            return  new Connection(true,sessionId);
        }
        else{
            Connection connection = retryConnect(args);
            if(connection.isOk()){
                Log.i(TAG,"connect success");
            }
            else{
                Log.i(TAG,"connect failed");
                connection.setMessage("connect failed");
            }
            return connection;
        }
    }

    @NonNull
    private String connectServer(Map<String,Object> args) {
        String sessionId;
        int j = 0;
        for(int i=0;i<50000;i++){
            j++;
        }
        sessionId =  new Random().nextBoolean()?"12000":"";
        return sessionId;
    }

    private Connection retryConnect(Map<String,Object> args) {
        String sessionId = connectServer(args);
        return new Connection(!"".equals(sessionId),sessionId);
    }

}
