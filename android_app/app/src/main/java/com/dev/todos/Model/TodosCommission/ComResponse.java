package com.dev.todos.Model.TodosCommission;

import com.google.gson.annotations.SerializedName;

public class ComResponse{

	@SerializedName("icurier_commission")
	private String icurierCommission;

	@SerializedName("msg")
	private String msg;

	@SerializedName("amount")
	private String amount;

	@SerializedName("status")
	private String status;

	public void setIcurierCommission(String icurierCommission){
		this.icurierCommission = icurierCommission;
	}

	public String getIcurierCommission(){
		return icurierCommission;
	}

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}

	public void setAmount(String amount){
		this.amount = amount;
	}

	public String getAmount(){
		return amount;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}