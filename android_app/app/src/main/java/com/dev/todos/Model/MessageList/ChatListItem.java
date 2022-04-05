package com.dev.todos.Model.MessageList;

import com.google.gson.annotations.SerializedName;

public class ChatListItem{

	@SerializedName("date")
	private String date;

	@SerializedName("user_image")
	private String userImage;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("user_name")
	private String userName;

	@SerializedName("user1_id")
	private String user1Id;

	@SerializedName("time")
	private String time;

	@SerializedName("message")
	private String message;

	@SerializedName("order_id")
	private String orderId;

	@SerializedName("user2_id")
	private String user2Id;

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
	}

	public void setUserImage(String userImage){
		this.userImage = userImage;
	}

	public String getUserImage(){
		return userImage;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setUserName(String userName){
		this.userName = userName;
	}

	public String getUserName(){
		return userName;
	}

	public void setUser1Id(String user1Id){
		this.user1Id = user1Id;
	}

	public String getUser1Id(){
		return user1Id;
	}

	public void setTime(String time){
		this.time = time;
	}

	public String getTime(){
		return time;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setOrderId(String orderId){
		this.orderId = orderId;
	}

	public String getOrderId(){
		return orderId;
	}

	public void setUser2Id(String user2Id){
		this.user2Id = user2Id;
	}

	public String getUser2Id(){
		return user2Id;
	}
}