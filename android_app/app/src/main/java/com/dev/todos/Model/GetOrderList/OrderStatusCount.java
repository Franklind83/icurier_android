package com.dev.todos.Model.GetOrderList;

import com.google.gson.annotations.SerializedName;

public class OrderStatusCount{

	@SerializedName("accepted_order_count")
	private String acceptedOrderCount;

	@SerializedName("inactive_order_count")
	private String inactiveOrderCount;

	@SerializedName("delivered_order_count")
	private String deliveredOrderCount;

	@SerializedName("pending_order_count")
	private String pendingOrderCount;

	public void setAcceptedOrderCount(String acceptedOrderCount){
		this.acceptedOrderCount = acceptedOrderCount;
	}

	public String getAcceptedOrderCount(){
		return acceptedOrderCount;
	}

	public void setInactiveOrderCount(String inactiveOrderCount){
		this.inactiveOrderCount = inactiveOrderCount;
	}

	public String getInactiveOrderCount(){
		return inactiveOrderCount;
	}

	public void setDeliveredOrderCount(String deliveredOrderCount){
		this.deliveredOrderCount = deliveredOrderCount;
	}

	public String getDeliveredOrderCount(){
		return deliveredOrderCount;
	}

	public void setPendingOrderCount(String pendingOrderCount){
		this.pendingOrderCount = pendingOrderCount;
	}

	public String getPendingOrderCount(){
		return pendingOrderCount;
	}
}