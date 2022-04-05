package com.dev.todos.Model.SingleorderList;

import com.google.gson.annotations.SerializedName;

public class SingleOrderResponse{

	@SerializedName("msg")
	private String msg;

	@SerializedName("order_details")
	private OrderDetails orderDetails;

	@SerializedName("status")
	private String status;

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}

	public void setOrderDetails(OrderDetails orderDetails){
		this.orderDetails = orderDetails;
	}

	public OrderDetails getOrderDetails(){
		return orderDetails;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}