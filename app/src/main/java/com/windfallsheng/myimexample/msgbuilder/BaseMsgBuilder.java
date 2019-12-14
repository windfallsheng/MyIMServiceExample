package com.windfallsheng.myimexample.msgbuilder;

import android.util.Log;

import com.windfallsheng.myimexample.entity.MsgInfo;
import com.windfallsheng.myimexample.entity.MsgServer;

/**
 * @author lzsheng
 * <p>
 * 各类型消息对象构建者的基类，并且实现一些通用的功能
 */
public abstract class BaseMsgBuilder{

    private static final String TAG = "BaseMsgBuilder";

    /**
     * 不同类型的消息对象
     */
    MsgServer msgServer;

    /**
     * 当前实例的属性是否可重置，且实例可被重用，fase为不可用，true为可用；
     */
    public boolean flag;

    public BaseMsgBuilder(MsgServer msgServer) {
        Log.d(TAG, "method:BaseMsgBuilder#msgServer=" + msgServer +
                ", this=" + this + ", hashCode=" + this.hashCode());
        this.msgServer = msgServer;
    }


    abstract MsgInfo buildMsgInfo();

    public void setMsgServer(MsgServer msgServer) {
        this.msgServer = msgServer;
    }

    public BaseMsgBuilder recycle() {
        Log.d(TAG, "method:recycle#in#this=" + this + ", hashCode=" + this.hashCode());
        this.msgServer = null;
        this.flag = false;
        Log.d(TAG, "method:recycle#out#this=" + this + ", hashCode=" + this.hashCode());
        return this;
    }

    @Override
    public String toString() {
        return "BaseMsgBuilder{" +
                "msgServer=" + msgServer +
                ", flag=" + flag +
                '}';
    }
}
