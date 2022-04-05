package com.dev.todos.Model.SaveAmountPayPhone;

import com.google.gson.annotations.SerializedName;

public class PayPhoneRequest{

	@SerializedName("user_id")
	private String userId;

	@SerializedName("amount_to_pay")
	private String amountToPay;

	@SerializedName("order_id")
	private String orderId;

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setAmountToPay(String amountToPay){
		this.amountToPay = amountToPay;
	}

	public String getAmountToPay(){
		return amountToPay;
	}

	public void setOrderId(String orderId){
		this.orderId = orderId;
	}

	public String getOrderId(){
		return orderId;
	}
}