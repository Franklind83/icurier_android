package com.dev.todos.Model.GetPaymentMethod;

import com.google.gson.annotations.SerializedName;

public class GetPaymentMethodResponse{

	@SerializedName("msg")
	private String msg;

	@SerializedName("pay_phone_account_status")
	private String payPhoneAccountStatus;

	@SerializedName("bank_transfer_status")
	private String bankTransferStatus;

	@SerializedName("stripe_credit_card_fees")
	private String stripeCreditCardFees;

	@SerializedName("bank_transfer_fees")
	private String bankTransferFees;

	@SerializedName("payphone_credit_card_fees")
	private String payphoneCreditCardFees;

	@SerializedName("stripe_account_status")
	private String stripeAccountStatus;

	@SerializedName("status")
	private String status;

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}

	public void setPayPhoneAccountStatus(String payPhoneAccountStatus){
		this.payPhoneAccountStatus = payPhoneAccountStatus;
	}

	public String getPayPhoneAccountStatus(){
		return payPhoneAccountStatus;
	}

	public void setBankTransferStatus(String bankTransferStatus){
		this.bankTransferStatus = bankTransferStatus;
	}

	public String getBankTransferStatus(){
		return bankTransferStatus;
	}

	public void setStripeCreditCardFees(String stripeCreditCardFees){
		this.stripeCreditCardFees = stripeCreditCardFees;
	}

	public String getStripeCreditCardFees(){
		return stripeCreditCardFees;
	}

	public void setBankTransferFees(String bankTransferFees){
		this.bankTransferFees = bankTransferFees;
	}

	public String getBankTransferFees(){
		return bankTransferFees;
	}

	public void setPayphoneCreditCardFees(String payphoneCreditCardFees){
		this.payphoneCreditCardFees = payphoneCreditCardFees;
	}

	public String getPayphoneCreditCardFees(){
		return payphoneCreditCardFees;
	}

	public void setStripeAccountStatus(String stripeAccountStatus){
		this.stripeAccountStatus = stripeAccountStatus;
	}

	public String getStripeAccountStatus(){
		return stripeAccountStatus;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}