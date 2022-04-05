package com.dev.todos.Model.WalletCommission;

import com.google.gson.annotations.SerializedName;

public class OrderListItem{

	@SerializedName("order_id")
	private String orderId;

	@SerializedName("payed_status")
	private String payed_status;

	public String getPayed_status() {
		return payed_status;
	}

	public void setPayed_status(String payed_status) {
		this.payed_status = payed_status;
	}

	@SerializedName("commission_earned")
	private String commissionEarned;

	public void setOrderId(String orderId){
		this.orderId = orderId;
	}

	public String getOrderId(){
		return orderId;
	}

	public void setCommissionEarned(String commissionEarned){
		this.commissionEarned = commissionEarned;
	}

	public String getCommissionEarned(){
		return commissionEarned;
	}
}