package com.dev.todos.Model.OrderStatus;

import com.google.gson.annotations.SerializedName;

public class OrderStatusResponse{

	@SerializedName("msg")
	private String msg;

	@SerializedName("order_info_status")
	private String orderInfoStatus;

	@SerializedName("status")
	private String status;

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}

	public void setOrderInfoStatus(String orderInfoStatus){
		this.orderInfoStatus = orderInfoStatus;
	}

	public String getOrderInfoStatus(){
		return orderInfoStatus;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}