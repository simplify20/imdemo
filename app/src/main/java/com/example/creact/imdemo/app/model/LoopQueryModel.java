package com.example.creact.imdemo.app.model;

import com.example.creact.imdemo.app.data.MessageData;
import com.example.creact.imdemo.app.data.ReceiveData;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/11/28.
 */
public class LoopQueryModel {

    public ReceiveData loopQuery(Map<String,Object> args){
        ReceiveData receiveData = new ReceiveData();
        receiveData.setDataList(Arrays.asList(new MessageData[]{new MessageData()}));
        return receiveData;
    }
}
