package com.dev.todos.Model.TodosCommission;

import com.google.gson.annotations.SerializedName;

public class ComRequest{

	@SerializedName("amount")
	private String amount;

	public void setAmount(String amount){
		this.amount = amount;
	}

	public String getAmount(){
		return amount;
	}
}