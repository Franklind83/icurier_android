package com.dev.todos.Model.GetProfileData;

import com.google.gson.annotations.SerializedName;

public class UserAccountDetails{

	@SerializedName("name_account_holder")
	private String nameAccountHolder;

	@SerializedName("account_number")
	private String accountNumber;

	@SerializedName("bank")
	private String bank;

	@SerializedName("mobile")
	private String mobile;

	@SerializedName("identity_card")
	private String identityCard;

	@SerializedName("email")
	private String email;

	@SerializedName("is_paypal")
	private String isPaypal;

	@SerializedName("status")
	private String status;

	public void setNameAccountHolder(String nameAccountHolder){
		this.nameAccountHolder = nameAccountHolder;
	}

	public String getNameAccountHolder(){
		return nameAccountHolder;
	}

	public void setAccountNumber(String accountNumber){
		this.accountNumber = accountNumber;
	}

	public String getAccountNumber(){
		return accountNumber;
	}

	public void setBank(String bank){
		this.bank = bank;
	}

	public String getBank(){
		return bank;
	}

	public void setMobile(String mobile){
		this.mobile = mobile;
	}

	public String getMobile(){
		return mobile;
	}

	public void setIdentityCard(String identityCard){
		this.identityCard = identityCard;
	}

	public String getIdentityCard(){
		return identityCard;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setIsPaypal(String isPaypal){
		this.isPaypal = isPaypal;
	}

	public String getIsPaypal(){
		return isPaypal;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}