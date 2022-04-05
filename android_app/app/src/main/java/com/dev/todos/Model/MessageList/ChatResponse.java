package com.dev.todos.Model.MessageList;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ChatResponse{

	@SerializedName("msg")
	private String msg;

	@SerializedName("chat_list")
	private List<ChatListItem> chatList;

	@SerializedName("status")
	private String status;

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}

	public void setChatList(List<ChatListItem> chatList){
		this.chatList = chatList;
	}

	public List<ChatListItem> getChatList(){
		return chatList;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}