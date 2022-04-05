package com.dev.todos.Model.Filter;

import java.util.List;

import com.dev.todos.Model.GetOrderList.OrderListItem;
import com.google.gson.annotations.SerializedName;

public class FilterResponse{

	@SerializedName("msg")
	private String msg;

	@SerializedName("order_list")
	private List<OrderListItem> orderList;

	@SerializedName("status")
	private String status;

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}

	public void setOrderList(List<OrderListItem> orderList){
		this.orderList = orderList;
	}

	public List<OrderListItem> getOrderList(){
		return orderList;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}