package com.dev.todos.Model.GetAdminBankAccount;

import com.google.gson.annotations.SerializedName;

public class AdminBankRequest{

	@SerializedName("bank_name")
	private String bankName;

	public void setBankName(String bankName){
		this.bankName = bankName;
	}

	public String getBankName(){
		return bankName;
	}
}