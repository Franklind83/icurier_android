package com.dev.todos.Model.GetProfileData;

import com.google.gson.annotations.SerializedName;

public class UserDetails{

	@SerializedName("country")
	private String country;

	@SerializedName("profile_image")
	private String profileImage;

	@SerializedName("dial_code")
	private String dialCode;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("rating_as_b")
	private String ratingAsB;

	@SerializedName("mobile")
	private String mobile;

	@SerializedName("rating")
	private String rating;

	@SerializedName("name")
	private String name;

	@SerializedName("rating_as_t")
	private String ratingAsT;

	@SerializedName("email")
	private String email;

	public void setCountry(String country){
		this.country = country;
	}

	public String getCountry(){
		return country;
	}

	public void setProfileImage(String profileImage){
		this.profileImage = profileImage;
	}

	public String getProfileImage(){
		return profileImage;
	}

	public void setDialCode(String dialCode){
		this.dialCode = dialCode;
	}

	public String getDialCode(){
		return dialCode;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setRatingAsB(String ratingAsB){
		this.ratingAsB = ratingAsB;
	}

	public String getRatingAsB(){
		return ratingAsB;
	}

	public void setMobile(String mobile){
		this.mobile = mobile;
	}

	public String getMobile(){
		return mobile;
	}

	public void setRating(String rating){
		this.rating = rating;
	}

	public String getRating(){
		return rating;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setRatingAsT(String ratingAsT){
		this.ratingAsT = ratingAsT;
	}

	public String getRatingAsT(){
		return ratingAsT;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}
}