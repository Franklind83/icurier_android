package com.dev.todos.Model.WalletCommission;

import com.google.gson.annotations.SerializedName;

public class WalletRequest{

	@SerializedName("user_id")
	private String userId;

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}
}