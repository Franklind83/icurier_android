package com.dev.todos.Model.Signup;

import com.google.gson.annotations.SerializedName;

public class SignupResponse{

	@SerializedName("msg")
	private String msg;

	@SerializedName("user_details")
	private UserDetails userDetails;

	@SerializedName("status")
	private String status;

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}

	public void setUserDetails(UserDetails userDetails){
		this.userDetails = userDetails;
	}

	public UserDetails getUserDetails(){
		return userDetails;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}