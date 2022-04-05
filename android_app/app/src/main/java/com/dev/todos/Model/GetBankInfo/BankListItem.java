package com.dev.todos.Model.GetBankInfo;

import com.google.gson.annotations.SerializedName;

public class BankListItem{

	@SerializedName("account_type")
	private String accountType;

	@SerializedName("id_no")
	private String idNo;

	@SerializedName("owner_name")
	private String ownerName;

	@SerializedName("bank_name")
	private String bankName;

	@SerializedName("account_no")
	private String accountNo;

	@SerializedName("email")
	private String email;

	public void setAccountType(String accountType){
		this.accountType = accountType;
	}

	public String getAccountType(){
		return accountType;
	}

	public void setIdNo(String idNo){
		this.idNo = idNo;
	}

	public String getIdNo(){
		return idNo;
	}

	public void setOwnerName(String ownerName){
		this.ownerName = ownerName;
	}

	public String getOwnerName(){
		return ownerName;
	}

	public void setBankName(String bankName){
		this.bankName = bankName;
	}

	public String getBankName(){
		return bankName;
	}

	public void setAccountNo(String accountNo){
		this.accountNo = accountNo;
	}

	public String getAccountNo(){
		return accountNo;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}
}