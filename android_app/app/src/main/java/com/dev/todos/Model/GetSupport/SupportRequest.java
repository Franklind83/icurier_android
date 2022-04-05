package com.dev.todos.Model.GetSupport;

import com.google.gson.annotations.SerializedName;

public class SupportRequest{

	@SerializedName("user_id")
	private String userId;

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}
}