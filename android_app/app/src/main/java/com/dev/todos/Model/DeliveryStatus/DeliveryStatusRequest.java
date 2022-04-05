package com.dev.todos.Model.DeliveryStatus;

import com.google.gson.annotations.SerializedName;

public class DeliveryStatusRequest{

	@SerializedName("order_id")
	private String orderId;

	public void setOrderId(String orderId){
		this.orderId = orderId;
	}

	public String getOrderId(){
		return orderId;
	}
}