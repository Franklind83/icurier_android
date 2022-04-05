package com.dev.todos.Model.Signup;

import com.google.gson.annotations.SerializedName;

public class SignupRequest{

	@SerializedName("country")
	private String country;

	@SerializedName("image")
	private String image;

	@SerializedName("password")
	private String password;

	@SerializedName("dial_code")
	private String dialCode;

	@SerializedName("token_from")
	private String tokenFrom;

	@SerializedName("name")
	private String name;

	@SerializedName("mobile")
	private String mobile;

	@SerializedName("user_token")
	private String userToken;

	@SerializedName("email")
	private String email;

	public void setCountry(String country){
		this.country = country;
	}

	public String getCountry(){
		return country;
	}

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setDialCode(String dialCode){
		this.dialCode = dialCode;
	}

	public String getDialCode(){
		return dialCode;
	}

	public void setTokenFrom(String tokenFrom){
		this.tokenFrom = tokenFrom;
	}

	public String getTokenFrom(){
		return tokenFrom;
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

	public void setUserToken(String userToken){
		this.userToken = userToken;
	}

	public String getUserToken(){
		return userToken;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}
}