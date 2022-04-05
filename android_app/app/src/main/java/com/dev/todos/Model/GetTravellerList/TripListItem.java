package com.dev.todos.Model.GetTravellerList;

import com.google.gson.annotations.SerializedName;

public class TripListItem{

	@SerializedName("trip_id")
	private String tripId;

	@SerializedName("rating_as_b")
	private String ratingAsB;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("user_image")
	private String userImage;

	@SerializedName("user_name")
	private String userName;

	@SerializedName("location_from")
	private String locationFrom;

	@SerializedName("rating_as_t")
	private String ratingAsT;

	@SerializedName("travel_date")
	private String travelDate;

	@SerializedName("location_to")
	private String locationTo;

	@SerializedName("user_rating")
	private String userRating;

	@SerializedName("return_date")
	private String returnDate;

	public void setTripId(String tripId){
		this.tripId = tripId;
	}

	public String getTripId(){
		return tripId;
	}

	public void setRatingAsB(String ratingAsB){
		this.ratingAsB = ratingAsB;
	}

	public String getRatingAsB(){
		return ratingAsB;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setUserImage(String userImage){
		this.userImage = userImage;
	}

	public String getUserImage(){
		return userImage;
	}

	public void setUserName(String userName){
		this.userName = userName;
	}

	public String getUserName(){
		return userName;
	}

	public void setLocationFrom(String locationFrom){
		this.locationFrom = locationFrom;
	}

	public String getLocationFrom(){
		return locationFrom;
	}

	public void setRatingAsT(String ratingAsT){
		this.ratingAsT = ratingAsT;
	}

	public String getRatingAsT(){
		return ratingAsT;
	}

	public void setTravelDate(String travelDate){
		this.travelDate = travelDate;
	}

	public String getTravelDate(){
		return travelDate;
	}

	public void setLocationTo(String locationTo){
		this.locationTo = locationTo;
	}

	public String getLocationTo(){
		return locationTo;
	}

	public void setUserRating(String userRating){
		this.userRating = userRating;
	}

	public String getUserRating(){
		return userRating;
	}

	public void setReturnDate(String returnDate){
		this.returnDate = returnDate;
	}

	public String getReturnDate(){
		return returnDate;
	}
}