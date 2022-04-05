package com.dev.todos.Model.DeliveryInfo;

import com.google.gson.annotations.SerializedName;

public class DeliveryInfoRequest{

	@SerializedName("order_id")
	private String orderId;

	public void setOrderId(String orderId){
		this.orderId = orderId;
	}

	public String getOrderId(){
		return orderId;
	}
}