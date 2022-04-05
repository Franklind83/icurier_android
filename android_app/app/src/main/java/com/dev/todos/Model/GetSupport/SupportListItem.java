package com.dev.todos.Model.GetSupport;

import com.google.gson.annotations.SerializedName;

public class SupportListItem{

	@SerializedName("date")
	private String date;

	@SerializedName("reason")
	private String reason;

	@SerializedName("support_id")
	private String supportId;

	@SerializedName("subject")
	private String subject;

	@SerializedName("description")
	private String description;

	@SerializedName("time")
	private String time;

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
	}

	public void setReason(String reason){
		this.reason = reason;
	}

	public String getReason(){
		return reason;
	}

	public void setSupportId(String supportId){
		this.supportId = supportId;
	}

	public String getSupportId(){
		return supportId;
	}

	public void setSubject(String subject){
		this.subject = subject;
	}

	public String getSubject(){
		return subject;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setTime(String time){
		this.time = time;
	}

	public String getTime(){
		return time;
	}
}