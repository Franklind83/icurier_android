package com.dev.todos.Model.OrderInfomation;

import com.google.gson.annotations.SerializedName;

public class TravellerData{

	@SerializedName("country")
	private String country;

	@SerializedName("profile_image")
	private String profileImage;

	@SerializedName("dial_code")
	private String dialCode;

	@SerializedName("city")
	private String city;

	@SerializedName("dob")
	private String dob;

	@SerializedName("traveller_id")
	private String travellerId;

	@SerializedName("name")
	private String name;

	@SerializedName("mobile")
	private String mobile;

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

	public void setCity(String city){
		this.city = city;
	}

	public String getCity(){
		return city;
	}

	public void setDob(String dob){
		this.dob = dob;
	}

	public String getDob(){
		return dob;
	}

	public void setTravellerId(String travellerId){
		this.travellerId = travellerId;
	}

	public String getTravellerId(){
		return travellerId;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setMobile(String mobile){
		this.mobile = mobile;
	}

	public String getMobile(){
		return mobile;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}
}