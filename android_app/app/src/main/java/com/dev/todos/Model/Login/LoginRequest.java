package com.dev.todos.Model.Login;

import com.google.gson.annotations.SerializedName;

public class LoginRequest{

	@SerializedName("password")
	private String password;

	@SerializedName("token_from")
	private String tokenFrom;

	@SerializedName("user_token")
	private String userToken;

	public void setPassword(String password) {
		this.password = password;
	}

	public void setTokenFrom(String tokenFrom) {
		this.tokenFrom = tokenFrom;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@SerializedName("email")
	private String email;

	public String getPassword(){
		return password;
	}

	public String getTokenFrom(){
		return tokenFrom;
	}

	public String getUserToken(){
		return userToken;
	}

	public String getEmail(){
		return email;
	}
}