package com.windfallsheng.myimexample;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.windfallsheng.myimexample.entity.MsgInfo;

import java.util.List;

public class MsgListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = MsgListAdapter.class.getSimpleName();
    private Context mContext;
    private List<MsgInfo> msgInfoList;

    private final int MSG_TYPE_TEXT_SEND = 1;
    private final int MSG_TYPE_TEXT_RECEIVE = 2;

    public MsgListAdapter(Context context, List<MsgInfo> msgInfoList) {
        this.mContext = context;
        this.msgInfoList = msgInfoList;
    }

    @Override
    public int getItemViewType(int position) {
        MsgInfo msgInfo = msgInfoList.get(position);
        int msgDirection = msgInfo.getMsgDirection();
        return msgDirection;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case MSG_TYPE_TEXT_SEND:
                return new HolderMsgTextSend(
                        LayoutInflater.from(mContext).inflate(R.layout.rv_item_chat_msg_send_text, parent, false));
            case MSG_TYPE_TEXT_RECEIVE:
                return new HolderMsgTextReceive(
                        LayoutInflater.from(mContext).inflate(R.layout.rv_item_chat_msg_receive_text, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        int viewType = getItemViewType(position);
        final MsgInfo msgInfo = msgInfoList.get(position);
        switch (viewType) {
            case MSG_TYPE_TEXT_SEND:
                final HolderMsgTextSend holderSend = (HolderMsgTextSend) holder;
                holderSend.content.setText(msgInfo.getMsgContent());
                break;
            case MSG_TYPE_TEXT_RECEIVE:
                final HolderMsgTextReceive holderReceive = (HolderMsgTextReceive) holder;
                holderReceive.content.setText(msgInfo.getMsgContent());
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (msgInfoList != null) {
            return msgInfoList.size();
        }
        return 0;
    }


    class HolderMsgTextSend extends RecyclerView.ViewHolder {

        TextView content;

        public HolderMsgTextSend(View itemView) {
            super(itemView);
            content = (TextView) itemView.findViewById(R.id.textview_message_content);
        }

    }

    class HolderMsgTextReceive extends RecyclerView.ViewHolder {

        TextView content;

        public HolderMsgTextReceive(View itemView) {
            super(itemView);
            content = (TextView) itemView.findViewById(R.id.textview_message_content);
        }

    }

}
