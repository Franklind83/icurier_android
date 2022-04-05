package com.dev.todos.Model.DeliveryInfo;

import com.google.gson.annotations.SerializedName;

public class DeliveryInfoResponse{

	@SerializedName("msg")
	private String msg;

	@SerializedName("traveller_id")
	private String travellerId;

	@SerializedName("buyer_id")
	private String buyerId;

	@SerializedName("delivery_data")
	private DeliveryData deliveryData;

	@SerializedName("status")
	private String status;

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}

	public void setTravellerId(String travellerId){
		this.travellerId = travellerId;
	}

	public String getTravellerId(){
		return travellerId;
	}

	public void setBuyerId(String buyerId){
		this.buyerId = buyerId;
	}

	public String getBuyerId(){
		return buyerId;
	}

	public void setDeliveryData(DeliveryData deliveryData){
		this.deliveryData = deliveryData;
	}

	public DeliveryData getDeliveryData(){
		return deliveryData;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}