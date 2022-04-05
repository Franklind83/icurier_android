package com.dev.todos.Model.GetProfileData;

import com.google.gson.annotations.SerializedName;

public class GetProfileResponse{

	@SerializedName("msg")
	private String msg;

	@SerializedName("bank_code")
	private String bankCode;

	@SerializedName("bank_account_number")
	private String bankAccountNumber;

	@SerializedName("owner_name")
	private String ownerName;

	@SerializedName("user_details")
	private UserDetails userDetails;

	@SerializedName("bank_country")
	private String bankCountry;

	@SerializedName("routing_number")
	private String routingNumber;

	@SerializedName("sort_code")
	private String sortCode;

	@SerializedName("clearing_code")
	private String clearingCode;

	@SerializedName("rating_count")
	private RatingCount ratingCount;

	@SerializedName("branch_code")
	private String branchCode;

	@SerializedName("bsb")
	private String bsb;

	@SerializedName("institution")
	private String institution;

	@SerializedName("user_account_details")
	private UserAccountDetails userAccountDetails;

	@SerializedName("bank_id")
	private String bankId;

	@SerializedName("branch_name")
	private String branchName;

	@SerializedName("iban")
	private String iban;

	@SerializedName("passport_number")
	private String passportNumber;

	@SerializedName("transit_number")
	private String transitNumber;

	@SerializedName("bank_name")
	private String bankName;

	@SerializedName("bank_account_type")
	private String bankAccountType;

	@SerializedName("ifsc")
	private String ifsc;

	@SerializedName("clabe")
	private String clabe;

	@SerializedName("status")
	private String status;

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}

	public void setBankCode(String bankCode){
		this.bankCode = bankCode;
	}

	public String getBankCode(){
		return bankCode;
	}

	public void setBankAccountNumber(String bankAccountNumber){
		this.bankAccountNumber = bankAccountNumber;
	}

	public String getBankAccountNumber(){
		return bankAccountNumber;
	}

	public void setOwnerName(String ownerName){
		this.ownerName = ownerName;
	}

	public String getOwnerName(){
		return ownerName;
	}

	public void setUserDetails(UserDetails userDetails){
		this.userDetails = userDetails;
	}

	public UserDetails getUserDetails(){
		return userDetails;
	}

	public void setBankCountry(String bankCountry){
		this.bankCountry = bankCountry;
	}

	public String getBankCountry(){
		return bankCountry;
	}

	public void setRoutingNumber(String routingNumber){
		this.routingNumber = routingNumber;
	}

	public String getRoutingNumber(){
		return routingNumber;
	}

	public void setSortCode(String sortCode){
		this.sortCode = sortCode;
	}

	public String getSortCode(){
		return sortCode;
	}

	public void setClearingCode(String clearingCode){
		this.clearingCode = clearingCode;
	}

	public String getClearingCode(){
		return clearingCode;
	}

	public void setRatingCount(RatingCount ratingCount){
		this.ratingCount = ratingCount;
	}

	public RatingCount getRatingCount(){
		return ratingCount;
	}

	public void setBranchCode(String branchCode){
		this.branchCode = branchCode;
	}

	public String getBranchCode(){
		return branchCode;
	}

	public void setBsb(String bsb){
		this.bsb = bsb;
	}

	public String getBsb(){
		return bsb;
	}

	public void setInstitution(String institution){
		this.institution = institution;
	}

	public String getInstitution(){
		return institution;
	}

	public void setUserAccountDetails(UserAccountDetails userAccountDetails){
		this.userAccountDetails = userAccountDetails;
	}

	public UserAccountDetails getUserAccountDetails(){
		return userAccountDetails;
	}

	public void setBankId(String bankId){
		this.bankId = bankId;
	}

	public String getBankId(){
		return bankId;
	}

	public void setBranchName(String branchName){
		this.branchName = branchName;
	}

	public String getBranchName(){
		return branchName;
	}

	public void setIban(String iban){
		this.iban = iban;
	}

	public String getIban(){
		return iban;
	}

	public void setPassportNumber(String passportNumber){
		this.passportNumber = passportNumber;
	}

	public String getPassportNumber(){
		return passportNumber;
	}

	public void setTransitNumber(String transitNumber){
		this.transitNumber = transitNumber;
	}

	public String getTransitNumber(){
		return transitNumber;
	}

	public void setBankName(String bankName){
		this.bankName = bankName;
	}

	public String getBankName(){
		return bankName;
	}

	public void setBankAccountType(String bankAccountType){
		this.bankAccountType = bankAccountType;
	}

	public String getBankAccountType(){
		return bankAccountType;
	}

	public void setIfsc(String ifsc){
		this.ifsc = ifsc;
	}

	public String getIfsc(){
		return ifsc;
	}

	public void setClabe(String clabe){
		this.clabe = clabe;
	}

	public String getClabe(){
		return clabe;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}