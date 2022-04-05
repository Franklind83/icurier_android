package com.dev.todos.Model.GetRating;

import com.google.gson.annotations.SerializedName;

public class RatingListItem{

	@SerializedName("rating_for")
	private String ratingFor;

	@SerializedName("user_review")
	private String userReview;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("user_name_from")
	private String userNameFrom;

	@SerializedName("user_id_from")
	private String userIdFrom;

	@SerializedName("user_image_from")
	private String userImageFrom;

	@SerializedName("user_rating")
	private String userRating;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@SerializedName("date")
	private String date;



	public void setRatingFor(String ratingFor){
		this.ratingFor = ratingFor;
	}

	public String getRatingFor(){
		return ratingFor;
	}

	public void setUserReview(String userReview){
		this.userReview = userReview;
	}

	public String getUserReview(){
		return userReview;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setUserNameFrom(String userNameFrom){
		this.userNameFrom = userNameFrom;
	}

	public String getUserNameFrom(){
		return userNameFrom;
	}

	public void setUserIdFrom(String userIdFrom){
		this.userIdFrom = userIdFrom;
	}

	public String getUserIdFrom(){
		return userIdFrom;
	}

	public void setUserImageFrom(String userImageFrom){
		this.userImageFrom = userImageFrom;
	}

	public String getUserImageFrom(){
		return userImageFrom;
	}

	public void setUserRating(String userRating){
		this.userRating = userRating;
	}

	public String getUserRating(){
		return userRating;
	}
}