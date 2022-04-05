package com.dev.todos.Model.StatusInformation;

import com.google.gson.annotations.SerializedName;

public class StatusResponse{

	@SerializedName("msg")
	private String msg;

	@SerializedName("order_status")
	private String orderStatus;

	@SerializedName("rating_status")
	private String ratingStatus;

	@SerializedName("traveller_id")
	private String travellerId;

	@SerializedName("traveller_data")
	private TravellerData travellerData;

	@SerializedName("buyer_id")
	private String buyerId;

	@SerializedName("byer_data")
	private ByerData byerData;

	@SerializedName("status")
	private String status;

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}

	public void setOrderStatus(String orderStatus){
		this.orderStatus = orderStatus;
	}

	public String getOrderStatus(){
		return orderStatus;
	}

	public void setRatingStatus(String ratingStatus){
		this.ratingStatus = ratingStatus;
	}

	public String getRatingStatus(){
		return ratingStatus;
	}

	public void setTravellerId(String travellerId){
		this.travellerId = travellerId;
	}

	public String getTravellerId(){
		return travellerId;
	}

	public void setTravellerData(TravellerData travellerData){
		this.travellerData = travellerData;
	}

	public TravellerData getTravellerData(){
		return travellerData;
	}

	public void setBuyerId(String buyerId){
		this.buyerId = buyerId;
	}

	public String getBuyerId(){
		return buyerId;
	}

	public void setByerData(ByerData byerData){
		this.byerData = byerData;
	}

	public ByerData getByerData(){
		return byerData;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}