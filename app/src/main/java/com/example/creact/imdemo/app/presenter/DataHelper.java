package com.example.creact.imdemo.app.presenter;

import com.example.creact.imdemo.app.data.MessageData;
import com.example.creact.imdemo.app.view.viewholder.data.DataBean;

import java.util.List;

/**
 * Created by Administrator on 2015/11/29.
 */
public class DataHelper {

    private List<DataBean> dataBeans;

    public DataHelper(List<DataBean> dataBeans) {
        this.dataBeans = dataBeans;
    }

    public int findPositionOfBean(DataBean dataBean){
        return dataBeans.indexOf(dataBean);
    }
    public int findPositionOfData(MessageData messageData){

        for(int i=0,size=dataBeans.size();i<size;i++){

            if(messageData==dataBeans.get(i).getData()){
                return i;
            }
        }
        return -1;
    }
}
