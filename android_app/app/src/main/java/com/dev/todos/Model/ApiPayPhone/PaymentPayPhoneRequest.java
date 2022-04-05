package com.dev.todos.Model.ApiPayPhone;

import com.google.gson.annotations.SerializedName;

public class PaymentPayPhoneRequest{

	@SerializedName("amount")
	private String amount;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("id")
	private String id;

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

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setOrderId(String orderId){
		this.orderId = orderId;
	}

	public String getOrderId(){
		return orderId;
	}
}