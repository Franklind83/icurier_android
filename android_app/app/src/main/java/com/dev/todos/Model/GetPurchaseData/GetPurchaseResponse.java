package com.dev.todos.Model.GetPurchaseData;

import com.google.gson.annotations.SerializedName;

public class GetPurchaseResponse{

	@SerializedName("msg")
	private String msg;

	@SerializedName("purchase_data")
	private PurchaseData purchaseData;

	@SerializedName("status")
	private String status;

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}

	public void setPurchaseData(PurchaseData purchaseData){
		this.purchaseData = purchaseData;
	}

	public PurchaseData getPurchaseData(){
		return purchaseData;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}