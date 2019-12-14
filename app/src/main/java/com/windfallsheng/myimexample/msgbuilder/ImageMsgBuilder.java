package com.windfallsheng.myimexample.msgbuilder;

import android.util.Log;

import com.windfallsheng.myimexample.entity.MsgInfo;
import com.windfallsheng.myimexample.entity.MsgServer;

import java.util.Map;

/**
 * @author lzsheng
 * <p>
 * 实现图片消息类型客户端消息对象的构建过程
 */
public class ImageMsgBuilder extends BaseMsgBuilder {

    private static final String TAG = "ImageMsgBuilder";

    public ImageMsgBuilder(MsgServer msgServer) {
        super(msgServer);
        Log.d(TAG, "method:ImageMsgBuilder#msgServer=" + msgServer +
                ", this=" + this + ", hashCode=" + this.hashCode());
    }

    @Override
    public MsgInfo buildMsgInfo() {
        Log.d(TAG, "method:buildMsgInfo#in#this=" + this + ", hashCode=" + this.hashCode());
        Map<String, String> propMap = msgServer.getPropMap();
        MsgInfo msgInfo = new MsgInfo(propMap.get("msgId"), msgServer.getMsgType(),
                Integer.valueOf(propMap.get("msgDirection")), propMap.get("imageMsgContent"));
        Log.d(TAG, "method:buildMsgInfo#out#return value:msgInfo=" + msgInfo + ", this=" + this + ", hashCode=" + this.hashCode());
        return msgInfo;
    }
}
