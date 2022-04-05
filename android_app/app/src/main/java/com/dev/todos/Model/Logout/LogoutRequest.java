package com.dev.todos.Model.Logout;

import com.google.gson.annotations.SerializedName;

public class LogoutRequest{

	@SerializedName("user_id")
	private String userId;

	@SerializedName("device")
	private String device;

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setDevice(String device){
		this.device = device;
	}

	public String getDevice(){
		return device;
	}
}