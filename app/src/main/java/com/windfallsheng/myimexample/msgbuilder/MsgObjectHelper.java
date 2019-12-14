package com.windfallsheng.myimexample.msgbuilder;

import android.util.Log;

import com.windfallsheng.myimexample.ObjectCache;
import com.windfallsheng.myimexample.entity.MsgInfo;
import com.windfallsheng.myimexample.entity.MsgServer;

/**
 * @author lzsheng
 * <p>
 * 实现各类型消息构建者的创建，及调用创建客户端消息对象的总体业务处理
 */
public class MsgObjectHelper {

    private static final String TAG = "MsgObjectHelper";

    /**
     * 当前实例的属性是否可重置，且实例可被重用，fase为不可用，true为可用；
     */
    public boolean flag;

    public MsgObjectHelper() {
        Log.d(TAG, "method:MsgObjectHelper#this=" + this + ", hashCode=" + this.hashCode());
    }

    /**
     * 获取一个{@link MsgObjectHelper}实例
     * <p>
     * 如果内存中的实例可被重用，则会返回内存中的实例对象，否则创建新实例；
     *
     * @return 返回一个可用的实例对象；
     */
    public static MsgObjectHelper obtain() {
        Log.d(TAG, "method:obtain#in");
        MsgObjectHelper msgObjectHelper = ObjectCache.getInstance().getMsgHelper(MsgObjectHelper.class);
//        MsgObjectHelper msgObjectHelper = new MsgObjectHelper();
        Log.d(TAG, "method:obtain#out#return value:msgObjectHelper=" + msgObjectHelper +
                ", hashCode=" + msgObjectHelper.hashCode());
        return msgObjectHelper;
    }

    public MsgObjectHelper recycle() {
        Log.d(TAG, "method:recycle#in#this=" + this + ", hashCode=" + this.hashCode());
        this.flag = false;
        Log.d(TAG, "method:recycle#out#this=" + this + ", hashCode=" + this.hashCode());
        return this;
    }

    /**
     * 根据不同的消息类型，创建不同的消息构建实例
     *
     * @param msgServer
     * @return
     */
    private BaseMsgBuilder initMsgBuilder(MsgServer msgServer) {
        Log.d(TAG, "method:initMsgBuilder#in#arguments:msgServer" + msgServer);
        if (msgServer == null) {
            Log.d(TAG, "method:initMsgBuilder#return#msgServer == null");

            return null;
        }
        int msgType = msgServer.getMsgType();
        BaseMsgBuilder msgBuilder = null;
        Log.d(TAG, "method:initMsgBuilder#msgType=" + msgType);
        switch (msgType) {
            case 1:
                msgBuilder = ObjectCache.getInstance().getMsgBuilder(TextMsgBuilder.class, msgServer);
//                msgBuilder = new TextMsgBuilder(msgServer);
                break;
            case 2:
                msgBuilder = ObjectCache.getInstance().getMsgBuilder(ImageMsgBuilder.class, msgServer);
//                msgBuilder = new ImageMsgBuilder(msgServer);
                break;
            case 3:
                msgBuilder = ObjectCache.getInstance().getMsgBuilder(VideoMsgBuilder.class, msgServer);
//                msgBuilder = new VideoMsgBuilder(msgServer);
                break;
            default:
                break;
        }
        Log.d(TAG, "method:initMsgBuilder#out#return value:msgBuilder=" + msgBuilder);
        return msgBuilder;
    }

    /**
     * 创建消息构建实例，并构建消息对象返回
     *
     * @param msgServer
     * @return
     */
    public MsgInfo buildMsgInfo(MsgServer msgServer) {
        Log.d(TAG, "method:buildMsgInfo#in#arguments:msgServer" + msgServer +
                ", this=" + this + ", hashCode=" + this.hashCode());
        BaseMsgBuilder msgBuilder = initMsgBuilder(msgServer);
        // 将当前相关实例的的flag属性置为不可重用状态
        if (msgBuilder != null) {
            Log.d(TAG, "method:buildMsgInfo#msgBuilder=" + msgBuilder + ", hashCode=" + msgBuilder.hashCode());
            MsgInfo msg = msgBuilder.buildMsgInfo();
            // 将当前相关实例的的flag属性置为可重用状态
            msgBuilder.flag = true;
            flag = true;
            Log.d(TAG, "method:buildMsgInfo#msgBuilder=" + msgBuilder + ", hashCode=" + msgBuilder.hashCode());
            Log.d(TAG, "method:buildMsgInfo#return msgInfo=" + msg);
            return msg;
        }
        Log.d(TAG, "method:buildMsgInfo#out#return null#msgBuilder == null");
        return null;
    }

    @Override
    public String toString() {
        return "MsgObjectHelper{" + ", flag=" + flag + '}';
    }
}
