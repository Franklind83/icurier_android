package com.dev.todos.Model.GetOrderList;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class OrderListResponse{

	@SerializedName("msg")
	private String msg;

	@SerializedName("order_status_count")
	private OrderStatusCount orderStatusCount;

	public String getPendingOrderCount() {
		return pendingOrderCount;
	}

	public void setPendingOrderCount(String pendingOrderCount) {
		this.pendingOrderCount = pendingOrderCount;
	}

	public String getDeliveredOrderCount() {
		return deliveredOrderCount;
	}

	public void setDeliveredOrderCount(String deliveredOrderCount) {
		this.deliveredOrderCount = deliveredOrderCount;
	}

	public String getInactiveOrderCount() {
		return inactiveOrderCount;
	}

	public void setInactiveOrderCount(String inactiveOrderCount) {
		this.inactiveOrderCount = inactiveOrderCount;
	}

	@SerializedName("accepted_order_count")
	private String acceptedOrderCount;

	@SerializedName("inactive_order_count")
	private String inactiveOrderCount;

	@SerializedName("delivered_order_count")
	private String deliveredOrderCount;

	@SerializedName("pending_order_count")
	private String pendingOrderCount;

	public String getAcceptedOrderCount() {
		return acceptedOrderCount;
	}

	public void setAcceptedOrderCount(String acceptedOrderCount) {
		this.acceptedOrderCount = acceptedOrderCount;
	}

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

	public void setOrderStatusCount(OrderStatusCount orderStatusCount){
		this.orderStatusCount = orderStatusCount;
	}

	public OrderStatusCount getOrderStatusCount(){
		return orderStatusCount;
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