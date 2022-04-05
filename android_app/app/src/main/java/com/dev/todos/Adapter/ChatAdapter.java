package com.dev.todos.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.todos.Model.Message.ChatDataItem;
import com.dev.todos.R;
import com.dev.todos.Sharedpreferences;
import com.dev.todos.Util.AppConstant;
import com.dev.todos.Util.Keys;
import com.dev.todos.Util.UseMe;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    public static Context context;
    List<ChatDataItem> chatListArrayList;
    private int lastPosition = -1;
   // ClsSharePreferance sharePreferance;
    String imageUser2;

    public ChatAdapter(Context context,List<ChatDataItem> chatListArrayList, String imageUser2) {
        this.context = context;
        this.chatListArrayList = chatListArrayList;
       // sharePreferance = new ClsSharePreferance(context);
        this.imageUser2 = imageUser2;

    }


    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {



        String userId = Sharedpreferences.readFromPreferences(context, AppConstant.USERID,"");

        Log.d("TAG", "onBindView: "+userId+"-----"+chatListArrayList.get(position).getUserId());
        if (chatListArrayList.get(position).getUserId().equals(userId)) {
            holder.llTo.setVisibility(View.VISIBLE);
            holder.llFrom.setVisibility(View.GONE);
            holder.txtToMsg.setText(chatListArrayList.get(position).getMessage());
            holder.txtToDate.setText(chatListArrayList.get(position).getTime());
            UseMe.setImage(context, Sharedpreferences.readFromPreferences(context,AppConstant.PROFILEIMAGE,""), holder.imageRight);

        } else {
            holder.llTo.setVisibility(View.GONE);
            holder.llFrom.setVisibility(View.VISIBLE);
            holder.txtFrom.setText(chatListArrayList.get(position).getMessage());
            holder.txtFromDate.setText(chatListArrayList.get(position).getTime());
            UseMe.setImage(context, imageUser2, holder.imageLeft);

        }

    }



    @Override
    public int getItemCount() {
        return chatListArrayList.size();
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder {
        LinearLayout llFrom, llTo;
        TextView txtToMsg, txtFrom, txtToDate, txtFromDate;
        ImageView imageLeft, imageRight;

        public ChatViewHolder(View itemView) {
            super(itemView);

            llFrom = itemView.findViewById(R.id.llFrom);
            llTo = itemView.findViewById(R.id.llTo);
            txtToMsg = itemView.findViewById(R.id.chat_right_msg_text_view);
            txtFrom = itemView.findViewById(R.id.chat_left_msg_text_view);
            txtToDate = itemView.findViewById(R.id.right_txt_date);
            txtFromDate = itemView.findViewById(R.id.left_txt_date);
            imageLeft = itemView.findViewById(R.id.imgLeftUser);
            imageRight = itemView.findViewById(R.id.imgRightUser);

        }
    }




}
