package com.dev.todos.Model.WalletCommission;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class WalletResponse{

	@SerializedName("total_commission_earned")
	private String totalCommissionEarned;

	@SerializedName("msg")
	private String msg;

	@SerializedName("order_list")
	private List<OrderListItem> orderList;

	@SerializedName("status")
	private String status;

	public void setTotalCommissionEarned(String totalCommissionEarned){
		this.totalCommissionEarned = totalCommissionEarned;
	}

	public String getTotalCommissionEarned(){
		return totalCommissionEarned;
	}

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