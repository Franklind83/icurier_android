package com.dev.todos.Model.GetOrderList;

import com.google.gson.annotations.SerializedName;

public class OrderListRequest{

	@SerializedName("post_string")
	private String postString;

	@SerializedName("user_id")
	private String userId;

	public void setPostString(String postString){
		this.postString = postString;
	}

	public String getPostString(){
		return postString;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}
}