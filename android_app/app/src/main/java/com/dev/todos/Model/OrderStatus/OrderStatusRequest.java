package com.dev.todos.Model.OrderStatus;

import com.google.gson.annotations.SerializedName;

public class OrderStatusRequest{

	@SerializedName("order_id")
	private String orderId;

	public void setOrderId(String orderId){
		this.orderId = orderId;
	}

	public String getOrderId(){
		return orderId;
	}
}