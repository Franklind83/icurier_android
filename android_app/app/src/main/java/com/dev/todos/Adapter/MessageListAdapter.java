package com.dev.todos.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.todos.Fragment.ChangeFragment;
import com.dev.todos.Fragment.Message.MessageFragment;
import com.dev.todos.Fragment.Message.MessageListFragment;
import com.dev.todos.Fragment.Message.OrderSummaryMessageFragment;
import com.dev.todos.Fragment.OrderSummaryFragment;
import com.dev.todos.Model.MessageList.ChatListItem;
import com.dev.todos.R;
import com.dev.todos.Util.Keys;
import com.dev.todos.Util.StaticClass;
import com.dev.todos.Util.UseMe;
import com.dev.todos.databinding.LayoutUserItemBinding;

import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.MyViewHolder> {
    Context context;
    FragmentManager fragmentManager;
    List<ChatListItem> chatLists;

    public MessageListAdapter(Context context, FragmentManager fragmentManager, List<ChatListItem> chatLists) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.chatLists = chatLists;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutUserItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_user_item, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.binding.txtMsg.setText(chatLists.get(position).getMessage());
        holder.binding.txtName.setText(chatLists.get(position).getUserName());
        holder.binding.txtTime.setText(chatLists.get(position).getTime());

        UseMe.setImage(context, chatLists.get(position).getUserImage(), holder.binding.imgProfile);


        holder.binding.txtProductDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString(Keys.user2_id, chatLists.get(position).getUser2Id());
                bundle.putString(Keys.user1_id, chatLists.get(position).getUser1Id());
                bundle.putString(Keys.order_id, chatLists.get(position).getOrderId());
                bundle.putString(Keys.name, chatLists.get(position).getUserName());
                bundle.putString(Keys.user1_id, chatLists.get(position).getUser1Id());
                bundle.putString(Keys.profileImage, chatLists.get(position).getUserImage());
                bundle.putString("CustomerProfile", chatLists.get(position).getUserImage());

                StaticClass.fromChat = true;
                StaticClass.fromUser = false;


                new ChangeFragment(context).changeFragmentWithBackStack(fragmentManager,
                        bundle, R.id.frameLayoout, new OrderSummaryMessageFragment());


            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(Keys.user2_id, chatLists.get(position).getUser2Id());
                bundle.putString(Keys.user1_id, chatLists.get(position).getUser1Id());
                bundle.putString(Keys.order_id, chatLists.get(position).getOrderId());
                bundle.putString(Keys.name, chatLists.get(position).getUserName());
                bundle.putString(Keys.user1_id, chatLists.get(position).getUser1Id());
                bundle.putString(Keys.profileImage, chatLists.get(position).getUserImage());
                bundle.putString("CustomerProfile", chatLists.get(position).getUserImage());

                StaticClass.fromChat = true;
                StaticClass.fromUser = false;

                new ChangeFragment(context).changeFragmentWithBackStack(fragmentManager,
                        bundle, R.id.frameLayoout, new MessageFragment());
            }
        });

    }

    @Override
    public int getItemCount() {
        return chatLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LayoutUserItemBinding binding;


        public MyViewHolder(LayoutUserItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
