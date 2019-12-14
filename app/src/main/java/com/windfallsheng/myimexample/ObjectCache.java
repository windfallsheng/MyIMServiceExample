package com.windfallsheng.myimexample;

import android.util.Log;

import com.windfallsheng.myimexample.entity.MsgServer;
import com.windfallsheng.myimexample.msgbuilder.BaseMsgBuilder;
import com.windfallsheng.myimexample.msgbuilder.MsgObjectHelper;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ObjectCache {

    private static final String TAG = "ObjectCache";

    private static volatile ObjectCache instance = null;

    private final ConcurrentMap<String, Object> OBJECT_CACHE =
            new ConcurrentHashMap<String, Object>();

    private ObjectCache() {

    }

    public static ObjectCache getInstance() {
        if (instance == null) {
            synchronized (ObjectCache.class) {
                if (instance == null) {
                    instance = new ObjectCache();
                }
            }
        }
        return instance;
    }

    public void cache(Class clazz, Object obj) {
        OBJECT_CACHE.put(clazz.getName(), obj);
    }

    public MsgObjectHelper getMsgHelper(Class clazz) {
        MsgObjectHelper msgObjectHelper = (MsgObjectHelper) OBJECT_CACHE.get(clazz.getName());
        Log.d(TAG, "method:getMsgHelper#msgObjectHelperCache=" + msgObjectHelper);
        if (msgObjectHelper != null) {
            msgObjectHelper.recycle();
        } else {
            msgObjectHelper = new MsgObjectHelper();
            cache(clazz, msgObjectHelper);
            Log.d(TAG, "method:getMsgHelper#msgObjectHelper=" + msgObjectHelper);
        }
        return msgObjectHelper;
    }

    public BaseMsgBuilder getMsgBuilder(Class clazz, MsgServer msgServer) {
        BaseMsgBuilder baseMsgBuilder = (BaseMsgBuilder) OBJECT_CACHE.get(clazz.getName());
        Log.d(TAG, "method:getMsgBuilder#baseMsgBuilderCache=" + baseMsgBuilder);
        if (baseMsgBuilder != null) {
            baseMsgBuilder.recycle();
            baseMsgBuilder.setMsgServer(msgServer);
        } else {
            Constructor constructor = null;
            try {
                constructor = clazz.getConstructor(MsgServer.class);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                return null;
            }
            try {
                baseMsgBuilder = (BaseMsgBuilder) constructor.newInstance(msgServer);
                cache(clazz, baseMsgBuilder);
                Log.d(TAG, "method:getMsgBuilder#baseMsgBuilder=" + baseMsgBuilder);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return baseMsgBuilder;
    }
}
