package com.dev.todos.Model.Payment;

import com.google.gson.annotations.SerializedName;

public class PaymentResponse{

	@SerializedName("msg")
	private String msg;

	@SerializedName("transaction_status")
	private String transactionStatus;

	@SerializedName("status")
	private String status;

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}

	public void setTransactionStatus(String transactionStatus){
		this.transactionStatus = transactionStatus;
	}

	public String getTransactionStatus(){
		return transactionStatus;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}