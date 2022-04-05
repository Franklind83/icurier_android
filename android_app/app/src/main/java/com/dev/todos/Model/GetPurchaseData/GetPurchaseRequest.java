package com.dev.todos.Model.GetPurchaseData;

import com.google.gson.annotations.SerializedName;

public class GetPurchaseRequest{

	@SerializedName("order_id")
	private String orderId;

	public void setOrderId(String orderId){
		this.orderId = orderId;
	}

	public String getOrderId(){
		return orderId;
	}
}