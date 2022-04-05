package com.dev.todos.Model.PaymentBanktransfer;

import com.google.gson.annotations.SerializedName;

public class BankTransferRequest{

	@SerializedName("origin_bank")
	private String originBank;

	@SerializedName("amount")
	private String amount;

	@SerializedName("transaction_image")
	private String transactionImage;

	@SerializedName("offer_order_id")
	private String offerOrderId;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("order_id")
	private String orderId;

	@SerializedName("transaction_no")
	private String transactionNo;

	public void setOriginBank(String originBank){
		this.originBank = originBank;
	}

	public String getOriginBank(){
		return originBank;
	}

	public void setAmount(String amount){
		this.amount = amount;
	}

	public String getAmount(){
		return amount;
	}

	public void setTransactionImage(String transactionImage){
		this.transactionImage = transactionImage;
	}

	public String getTransactionImage(){
		return transactionImage;
	}

	public void setOfferOrderId(String offerOrderId){
		this.offerOrderId = offerOrderId;
	}

	public String getOfferOrderId(){
		return offerOrderId;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setOrderId(String orderId){
		this.orderId = orderId;
	}

	public String getOrderId(){
		return orderId;
	}

	public void setTransactionNo(String transactionNo){
		this.transactionNo = transactionNo;
	}

	public String getTransactionNo(){
		return transactionNo;
	}
}