package com.dev.todos.Model.Payment;

import com.google.gson.annotations.SerializedName;

public class PaymentRequest{

	@SerializedName("amount")
	private String amount;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("source")
	private String source;

	@SerializedName("order_id")
	private String orderId;

	public void setAmount(String amount){
		this.amount = amount;
	}

	public String getAmount(){
		return amount;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setSource(String source){
		this.source = source;
	}

	public String getSource(){
		return source;
	}

	public void setOrderId(String orderId){
		this.orderId = orderId;
	}

	public String getOrderId(){
		return orderId;
	}
}