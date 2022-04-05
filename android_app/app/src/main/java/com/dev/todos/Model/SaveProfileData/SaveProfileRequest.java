package com.dev.todos.Model.SaveProfileData;

import com.google.gson.annotations.SerializedName;

public class SaveProfileRequest{

	@SerializedName("country")
	private String country;

	@SerializedName("image")
	private String image;

	@SerializedName("iban")
	private String iban;


	@SerializedName("password")
	private String Password;

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	@SerializedName("bank_code")
	private String bankCode;

	public String getBranchName() {
		return branchName;
	}

	public String getInstitution() {
		return institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
	}

	public String getTransitNumber() {
		return transitNumber;
	}

	public void setTransitNumber(String transitNumber) {
		this.transitNumber = transitNumber;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	public String getBsb() {
		return bsb;
	}

	public void setBsb(String bsb) {
		this.bsb = bsb;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getIfsc() {
		return ifsc;
	}

	public void setIfsc(String ifsc) {
		this.ifsc = ifsc;
	}

	public String getClearingCode() {
		return clearingCode;
	}

	public void setClearingCode(String clearingCode) {
		this.clearingCode = clearingCode;
	}

	@SerializedName("clearing_code")
	private String clearingCode;

	@SerializedName("institution")
	private String institution;

	@SerializedName("transit_number")
	private String transitNumber;

	@SerializedName("passport_number")
	private String passportNumber;

	@SerializedName("bsb")
	private String bsb;

	@SerializedName("owner_name")
	private String ownerName;

	@SerializedName("ifsc")
	private String ifsc;

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	@SerializedName("branch_name")
	private String 	branchName;


	public String getClabe() {
		return clabe;
	}

	public void setClabe(String clabe) {
		this.clabe = clabe;
	}

	@SerializedName("clabe")
	private String clabe;




	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	@SerializedName("branch_code")
	private String branchCode;




	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public String getSortCode() {
		return sortCode;
	}

	public void setSortCode(String sortCode) {
		this.sortCode = sortCode;
	}

	@SerializedName("sort_code")
	private String sortCode;

	@SerializedName("routing_number")
	private String routingNumber;

	public String getRoutingNumber() {
		return routingNumber;
	}

	public void setRoutingNumber(String routingNumber) {
		this.routingNumber = routingNumber;
	}

	@SerializedName("bank_account_number")
	private String bankAccountNumber;

	@SerializedName("dial_code")
	private String dialCode;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("bank_id")
	private String bankId;

	@SerializedName("name")
	private String name;

	@SerializedName("mobile")
	private String mobile;

	@SerializedName("bank_name")
	private String bankName;

	@SerializedName("bank_country")
	private String bankCountry;

	@SerializedName("bank_account_type")
	private String bankAccountType;

	@SerializedName("email")
	private String email;

	public void setCountry(String country){
		this.country = country;
	}

	public String getCountry(){
		return country;
	}

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setBankAccountNumber(String bankAccountNumber){
		this.bankAccountNumber = bankAccountNumber;
	}

	public String getBankAccountNumber(){
		return bankAccountNumber;
	}

	public void setDialCode(String dialCode){
		this.dialCode = dialCode;
	}

	public String getDialCode(){
		return dialCode;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setBankId(String bankId){
		this.bankId = bankId;
	}

	public String getBankId(){
		return bankId;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setMobile(String mobile){
		this.mobile = mobile;
	}

	public String getMobile(){
		return mobile;
	}

	public void setBankName(String bankName){
		this.bankName = bankName;
	}

	public String getBankName(){
		return bankName;
	}

	public void setBankCountry(String bankCountry){
		this.bankCountry = bankCountry;
	}

	public String getBankCountry(){
		return bankCountry;
	}

	public void setBankAccountType(String bankAccountType){
		this.bankAccountType = bankAccountType;
	}

	public String getBankAccountType(){
		return bankAccountType;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}
}