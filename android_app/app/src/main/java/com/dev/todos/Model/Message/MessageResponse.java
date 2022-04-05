package com.dev.todos.Model.Message;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MessageResponse{

	@SerializedName("msg")
	private String msg;

	@SerializedName("chat_data")
	private List<ChatDataItem> chatData;

	@SerializedName("status")
	private String status;

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}

	public void setChatData(List<ChatDataItem> chatData){
		this.chatData = chatData;
	}

	public List<ChatDataItem> getChatData(){
		return chatData;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}