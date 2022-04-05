package com.dev.todos.Model.DeletedUser;

import com.google.gson.annotations.SerializedName;

public class DeletedUserResponse{

	@SerializedName("msg")
	private String msg;

	@SerializedName("is_user_deleted")
	private String isUserDeleted;

	@SerializedName("status")
	private String status;

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}

	public void setIsUserDeleted(String isUserDeleted){
		this.isUserDeleted = isUserDeleted;
	}

	public String getIsUserDeleted(){
		return isUserDeleted;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}