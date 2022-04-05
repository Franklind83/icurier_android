package com.dev.todos.Model.SaveAmountPayPhone;

import com.google.gson.annotations.SerializedName;

public class PayPhoneResponse{

	@SerializedName("msg")
	private String msg;

	@SerializedName("payment_checkout_url")
	private String paymentCheckoutUrl;

	@SerializedName("status")
	private String status;

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}

	public void setPaymentCheckoutUrl(String paymentCheckoutUrl){
		this.paymentCheckoutUrl = paymentCheckoutUrl;
	}

	public String getPaymentCheckoutUrl(){
		return paymentCheckoutUrl;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}