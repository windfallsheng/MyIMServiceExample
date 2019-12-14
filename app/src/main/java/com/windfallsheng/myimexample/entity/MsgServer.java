package com.windfallsheng.myimexample.entity;

import java.io.Serializable;
import java.util.Map;

public class MsgServer implements Serializable {

    private int msgType;
    private Map propMap;

    public MsgServer(int msgType, Map propMap) {
        this.msgType = msgType;
        this.propMap = propMap;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public Map getPropMap() {
        return propMap;
    }

    public void setPropMap(Map propMap) {
        this.propMap = propMap;
    }

    @Override
    public String toString() {
        return "MsgServer{" +
                "msgType=" + msgType +
                ", propMap=" + propMap +
                '}';
    }
}
