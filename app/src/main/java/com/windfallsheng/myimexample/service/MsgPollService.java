package com.windfallsheng.myimexample.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.windfallsheng.myimexample.entity.MsgServer;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MsgPollService extends Service {

    private final String TAG = "MsgPollService";

    private ScheduledExecutorService mMessagePollingService;

    /**
     * 轮询的间隔时间
     */
    private long mPeriod = 500;//
    /**
     * 记录轮询的次数
     */
    private int mPollingCount;//


    public static Intent newIntent(Context context, String action) {
        return new Intent(context.getApplicationContext(), MsgPollService.class).setAction(action);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            if ("action.MSG_POLL".equals(intent.getAction())) {
                buildPollingService(mPeriod);
            }
        }
        // return super.onStartCommand(intent, flags, startId);
        // onStartCommand方法的默认值START_STICKY_COMPATIBILITY或者START_STICKY不能保证intent不为null.
        return super.onStartCommand(intent, Service.START_REDELIVER_INTENT, startId);
    }

    /**
     * 根据不同和参数配置，创建新的轮询服务，同时根据之前的对象存在于否来判断是否关闭之前的服务
     *
     * @param period 间隔时间
     */
    private void buildPollingService(long period) {
        if (mMessagePollingService != null) {
            mMessagePollingService.shutdownNow();
        }
        mMessagePollingService = Executors.newSingleThreadScheduledExecutor();
        mMessagePollingService.scheduleAtFixedRate(new Runnable() {

            @Override
            public void run() {
                mPollingCount++;
                Map<String, String> propMap = new HashMap<>();
                int msgType = (int) (Math.random() * 3 + 1);
                String msgId = String.valueOf(UUID.randomUUID());
                propMap.put("msgId", msgId);
                int msgDirection = (int) (Math.random() * 2 + 1);
                propMap.put("msgDirection", msgDirection + "");

                String msgContent;
                if (msgType == 1) {
                    msgContent = "[第" + mPollingCount + "条信息]\n" + msgId + "\n[文本]这是我要传达的信息";
                    propMap.put("textMsgContent", msgContent);
                } else if (msgType == 2) {
                    msgContent = "[第" + mPollingCount + "条信息]\n" + msgId + "\n[图片]";
                    propMap.put("imageMsgContent", msgContent);
                } else if (msgType == 3) {
                    msgContent = "[第" + mPollingCount + "条信息]\n" + msgId + "\n[视频]";
                    propMap.put("videoMsgContent", msgContent);
                } else {
                    msgContent = "[第" + mPollingCount + "条信息]\n" + msgId + "\n[default]";
                    propMap.put("defaultMsgContent", msgContent);
                }
                MsgServer msgServer = new MsgServer(msgType, propMap);
                Log.d(TAG, "run#this= " + this + ", msgServer=" + msgServer + ", hashCode=" + msgServer.hashCode());
                Intent intent = new Intent("action.MSG_RECEIVE");
                intent.putExtra("MsgServer", (Serializable) msgServer);
                LocalBroadcastManager.getInstance(MsgPollService.this).sendBroadcast(intent);
            }
        }, 0, period, TimeUnit.MILLISECONDS);
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
