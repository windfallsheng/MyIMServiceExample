package com.windfallsheng.myimexample.entity;

import java.io.Serializable;

public class MsgInfo implements Serializable {

    private String msgId;
    private int msgType;
    private int msgDirection;
    private String msgContent;

    public MsgInfo(String msgId, int msgType, int msgDirection, String msgContent) {
        this.msgId = msgId;
        this.msgType = msgType;
        this.msgDirection = msgDirection;
        this.msgContent = msgContent;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public int getMsgDirection() {
        return msgDirection;
    }

    public void setMsgDirection(int msgDirection) {
        this.msgDirection = msgDirection;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    @Override
    public String toString() {
        return "MsgInfo{" +
                "msgId=" + msgId +
                ", msgType=" + msgType +
                ", msgDirection=" + msgDirection +
                ", msgContent='" + msgContent + '\'' +
                '}';
    }
}
