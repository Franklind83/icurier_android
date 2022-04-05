package com.dev.todos.Model.GetTravellerList;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class TravellerListResponse{

	@SerializedName("msg")
	private String msg;

	@SerializedName("trip_list")
	private List<TripListItem> tripList;

	@SerializedName("status")
	private String status;

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}

	public void setTripList(List<TripListItem> tripList){
		this.tripList = tripList;
	}

	public List<TripListItem> getTripList(){
		return tripList;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}