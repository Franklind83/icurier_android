package com.dev.todos.Model.GetSupport;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SupportResponse{

	@SerializedName("msg")
	private String msg;

	@SerializedName("support_list")
	private List<SupportListItem> supportList;

	@SerializedName("status")
	private String status;

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}

	public void setSupportList(List<SupportListItem> supportList){
		this.supportList = supportList;
	}

	public List<SupportListItem> getSupportList(){
		return supportList;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}