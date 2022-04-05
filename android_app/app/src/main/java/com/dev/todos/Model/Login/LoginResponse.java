package com.dev.todos.Model.Login;

import com.google.gson.annotations.SerializedName;

public class LoginResponse{

	@SerializedName("msg")
	private String msg;

	@SerializedName("user_details")
	private UserDetails userDetails;

	@SerializedName("status")
	private String status;

	public String getMsg(){
		return msg;
	}

	public UserDetails getUserDetails(){
		return userDetails;
	}

	public String getStatus(){
		return status;
	}
}