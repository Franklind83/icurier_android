package com.dev.todos.Model.GetTravellerList;

import com.google.gson.annotations.SerializedName;

public class TravellerListRequest{

	@SerializedName("current_date")
	private String currentDate;

	@SerializedName("user_id")
	private String userId;

	public void setCurrentDate(String currentDate){
		this.currentDate = currentDate;
	}

	public String getCurrentDate(){
		return currentDate;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}
}