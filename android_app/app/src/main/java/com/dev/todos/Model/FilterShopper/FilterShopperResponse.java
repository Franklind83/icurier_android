package com.dev.todos.Model.FilterShopper;

import com.dev.todos.Model.GetTravellerList.TripListItem;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FilterShopperResponse{

	@SerializedName("msg")
	private String msg;

	@SerializedName("status")
	private String status;

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@SerializedName("trip_list")
	private List<TripListItem> tripList;


	public void setTripList(List<TripListItem> tripList){
		this.tripList = tripList;
	}

	public List<TripListItem> getTripList(){
		return tripList;
	}

}