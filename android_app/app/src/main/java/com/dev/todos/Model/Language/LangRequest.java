package com.dev.todos.Model.Language;

import com.google.gson.annotations.SerializedName;

public class LangRequest{

	@SerializedName("user_id")
	private String userId;

	@SerializedName("language")
	private String language;

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setLanguage(String language){
		this.language = language;
	}

	public String getLanguage(){
		return language;
	}
}