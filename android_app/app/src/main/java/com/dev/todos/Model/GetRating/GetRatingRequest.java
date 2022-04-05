package com.dev.todos.Model.GetRating;

import com.google.gson.annotations.SerializedName;

public class GetRatingRequest{

	@SerializedName("rating_for")
	private String ratingFor;

	@SerializedName("user_id")
	private String userId;

	public void setRatingFor(String ratingFor){
		this.ratingFor = ratingFor;
	}

	public String getRatingFor(){
		return ratingFor;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}
}