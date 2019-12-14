package com.windfallsheng.myimexample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.windfallsheng.myimexample.entity.MsgInfo;
import com.windfallsheng.myimexample.entity.MsgServer;
import com.windfallsheng.myimexample.msgbuilder.MsgObjectHelper;
import com.windfallsheng.myimexample.service.MsgPollService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";
    private TextView mTextView;
    private RecyclerView mRecyclerView;
    private MsgListAdapter mMsgListAdapter;
    private List<MsgInfo> mMsgInfoList;
    private ScheduledExecutorService mExecutorService;
    private boolean isRunning;
    private int index = 0;

    private BroadcastReceiver msgReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent == null) {
                return;
            }
            String action = intent.getAction();
            if ("action.MSG_RECEIVE".equals(action)) {
                Bundle data = intent.getExtras();
                MsgServer msgServer = (MsgServer) data.get("MsgServer");
                Log.d(TAG, "msgReceiver#msgServer=" + msgServer + ", hashCode=" + msgServer.hashCode());
                MsgInfo msgInfo = MsgObjectHelper.obtain().buildMsgInfo(msgServer);
                if (msgInfo == null) {
                    Log.d(TAG, "msgReceiver#return#msgInfo == null");
                    return;
                }
                final int size = mMsgInfoList.size();
                mMsgInfoList.add(size, msgInfo);
                mMsgListAdapter.notifyDataSetChanged();
                mRecyclerView.scrollToPosition(mMsgInfoList.size() - 1);
            }

        }
    };

//    private Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case 1:
//                    MsgInfo msgInfo = (MsgInfo) msg.obj;
//                    Log.d(TAG, "handleMessage#msgInfo=" + msgInfo + ", hashCode=" + msgInfo.hashCode());
//                    final int size = mMsgInfoList.size();
//                    mMsgInfoList.add(size, msgInfo);
//                    mMsgListAdapter.notifyDataSetChanged();
//                    mRecyclerView.scrollToPosition(mMsgInfoList.size() - 1);
//                    break;
//                default:
//                    break;
//            }
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.text_view);
        mRecyclerView = findViewById(R.id.recycler_view);
        mMsgInfoList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mMsgListAdapter = new MsgListAdapter(this, mMsgInfoList);
        mRecyclerView.setAdapter(mMsgListAdapter);

        IntentFilter filter = new IntentFilter();
        filter.addAction("action.MSG_RECEIVE");
        LocalBroadcastManager.getInstance(MainActivity.this).registerReceiver(msgReceiver, filter);
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRunning = !isRunning;
                if (isRunning) {
                    mTextView.setText("Stop");
                    startService(MsgPollService.newIntent(MainActivity.this, "action.MSG_POLL"));
                } else {
                    mTextView.setText("Start");
                    stopService(MsgPollService.newIntent(MainActivity.this, "action.MSG_POLL"));
                }
            }
        });

//        if (mExecutorService == null) {
//            mExecutorService = Executors.newSingleThreadScheduledExecutor();
//        }
//        mExecutorService.scheduleAtFixedRate(new Runnable() {
//            @Override
//            public void run() {
//                index++;
//                String msgId = String.valueOf(UUID.randomUUID());
//                int msgType = (int) (Math.random() * 3 + 1);
//                int msgDirection = (int) (Math.random() * 2 + 1);
//                String msgContent = null;
//                if (msgType == 1) {
//                    msgContent = "[第" + index + "条信息]\n" + msgId + "\n[文本]这是我要传达的信息";
//                } else if (msgType == 2) {
//                    msgContent = "[第" + index + "条信息]\n" + msgId + "\n[图片]";
//                } else if (msgType == 3) {
//                    msgContent = "[第" + index + "条信息]\n" + msgId + "\n[视频]";
//                } else {
//                    msgContent = "[第" + index + "条信息]\n" + msgId + "\n[default]";
//                }
//                MsgInfo msgInfo = new MsgInfo(msgId, msgType, msgDirection, msgContent);
//                Log.d(TAG, "run#msgInfo=" + msgInfo + ", hashCode=" + msgInfo.hashCode());
//
//                Message msg = Message.obtain();
//                msg.what = 1;
//                msg.obj = msgInfo;
//                mHandler.sendMessage(msg);
//
//            }
//        }, 0, 3000, TimeUnit.MILLISECONDS);
    }
}
