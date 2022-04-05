package com.dev.todos.Model.SaveProfileData;

import com.dev.todos.Model.Signup.UserDetails;
import com.google.gson.annotations.SerializedName;

public class SaveProfileResponse{

	@SerializedName("msg")
	private String msg;

	@SerializedName("status")
	private String status;

	@SerializedName("user_details")
	private UserDetails userDetails;

	public UserDetails getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}