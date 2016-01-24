package com.example.creact.imdemo.app.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.creact.imdemo.R;
import com.example.creact.imdemo.app.data.MessageData;
import com.example.creact.imdemo.app.data.QuestionData;
import com.example.creact.imdemo.app.presenter.ChatPresenter;
import com.example.creact.imdemo.app.presenter.DataHelper;
import com.example.creact.imdemo.app.presenter.state.ChatState;
import com.example.creact.imdemo.app.presenter.state.FirstInState;
import com.example.creact.imdemo.app.presenter.state.NormalState;
import com.example.creact.imdemo.app.presenter.state.OrderFinishedState;
import com.example.creact.imdemo.app.view.viewholder.data.DataBean;
import com.example.creact.imdemo.app.widget.WaveLoadingView;
import com.example.creact.imdemo.app.widget.WaveLoadingView2;
import com.example.creact.imdemo.core.callback.ConnectCallback;
import com.example.creact.imdemo.core.callback.SendMessageCallback;
import com.example.creact.imdemo.core.callback.SendResponse;
import com.example.creact.imdemo.core.communication.Connection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity implements ChatView {
    private static final String TAG = "WaveLoadingView2";
    private ChatPresenter presenter;
    private String sessionId;
    private List<DataBean> dataBeans;
    private DataHelper dataHelper;
    private boolean isFirstIn;
    private boolean isOrderFinished;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new ChatPresenter(this, getState());
        dataBeans = new ArrayList<>();
        dataHelper = new DataHelper(dataBeans);
        //connect server
        connect(new HashMap<String, Object>());

        final WaveLoadingView2 waveLoadingView = (WaveLoadingView2) findViewById(R.id.wave_loading2);
        SeekBar seekBar = (SeekBar) findViewById(R.id.seek_bar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                waveLoadingView.setProgress(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        waveLoadingView.post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "width:" + waveLoadingView.getWidth() + "height:" + waveLoadingView.getHeight());
            }
        });
    }

    private ChatState getState() {
        ChatState state = new NormalState();
        if (isFirstIn)
            state = new FirstInState();
        if (isOrderFinished)
            state = new OrderFinishedState();
        return state;
    }

    private void connect(Map<String, Object> args) {
        presenter.connectServer(args, new ConnectCallback() {
            @Override
            public void onConnectFailed(Connection connection) {
                Toast.makeText(ChatActivity.this, connection.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onConnectSuccess(Connection connection) {
                Toast.makeText(ChatActivity.this, (CharSequence) connection.getObj(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void send(MessageData messageData) {
        presenter.sendMessage(null, messageData, new SendMessageCallback<MessageData>() {
            @Override
            public void onSendSucess(SendResponse<MessageData> response) {
                MessageData withId = response.getObj();
                Toast.makeText(ChatActivity.this, withId.toString(), Toast.LENGTH_SHORT).show();
                int position = dataHelper.findPositionOfData(response.getObj());
                //UPDATE UI
            }

            @Override
            public void onSendFailed(SendResponse<MessageData> response) {
                Toast.makeText(ChatActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                int position = dataHelper.findPositionOfData(response.getObj());
                //UPDATE UI
            }
        });
    }

    @Override
    public void receive(List<MessageData> datas) {
        //update UI
    }

    @Override
    public void showQuestions(List<QuestionData> questionDatas) {
        //update ui
    }
}
