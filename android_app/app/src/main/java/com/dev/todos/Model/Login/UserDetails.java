package com.dev.todos.Model.Login;

import com.google.gson.annotations.SerializedName;

public class UserDetails{

	@SerializedName("country")
	private String country;

	@SerializedName("profile_image")
	private String profileImage;

	@SerializedName("dial_code")
	private String dialCode;

	@SerializedName("rating_as_b")
	private String ratingAsB;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("rating")
	private String rating;

	@SerializedName("mobile")
	private String mobile;

	@SerializedName("name")
	private String name;

	@SerializedName("rating_as_t")
	private String ratingAsT;

	@SerializedName("email")
	private String email;

	public String getCountry(){
		return country;
	}

	public String getProfileImage(){
		return profileImage;
	}

	public String getDialCode(){
		return dialCode;
	}

	public String getRatingAsB(){
		return ratingAsB;
	}

	public String getUserId(){
		return userId;
	}

	public String getRating(){
		return rating;
	}

	public String getMobile(){
		return mobile;
	}

	public String getName(){
		return name;
	}

	public String getRatingAsT(){
		return ratingAsT;
	}

	public String getEmail(){
		return email;
	}
}