package com.dev.todos.Model.Logout;

import com.google.gson.annotations.SerializedName;

public class LogoutResponse{

	@SerializedName("msg")
	private String msg;

	@SerializedName("status")
	private String status;

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