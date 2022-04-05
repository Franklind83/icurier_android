package com.dev.todos.Model.OrderInfomation;

import com.google.gson.annotations.SerializedName;

public class OrderInfoRequest{

	@SerializedName("order_id")
	private String orderId;

	public void setOrderId(String orderId){
		this.orderId = orderId;
	}

	public String getOrderId(){
		return orderId;
	}
}