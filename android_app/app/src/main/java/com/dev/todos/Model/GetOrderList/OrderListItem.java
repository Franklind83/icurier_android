package com.dev.todos.Model.GetOrderList;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class OrderListItem{

	@SerializedName("order_offer_list")
	private List<OrderOfferListItem> orderOfferList;

	@SerializedName("order_status")
	private String orderStatus;

	@SerializedName("bring_product")
	private String bringProduct;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("user_image")
	private String userImage;

	@SerializedName("user_name")
	private String userName;

	@SerializedName("order_offer_status")
	private String orderOfferStatus;

	@SerializedName("hire_status")
	private String hireStatus;

	@SerializedName("total_delivery_reward")
	private String totalDeliveryReward;

	@SerializedName("product_list")
	private List<ProductListItem> productList;

	@SerializedName("order_id")
	private String orderId;

	@SerializedName("total_order_price")
	private String totalOrderPrice;

	public void setOrderOfferList(List<OrderOfferListItem> orderOfferList){
		this.orderOfferList = orderOfferList;
	}

	public List<OrderOfferListItem> getOrderOfferList(){
		return orderOfferList;
	}

	public void setOrderStatus(String orderStatus){
		this.orderStatus = orderStatus;
	}

	public String getOrderStatus(){
		return orderStatus;
	}

	public void setBringProduct(String bringProduct){
		this.bringProduct = bringProduct;
	}

	public String getBringProduct(){
		return bringProduct;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setUserImage(String userImage){
		this.userImage = userImage;
	}

	public String getUserImage(){
		return userImage;
	}

	public void setUserName(String userName){
		this.userName = userName;
	}

	public String getUserName(){
		return userName;
	}

	public void setOrderOfferStatus(String orderOfferStatus){
		this.orderOfferStatus = orderOfferStatus;
	}

	public String getOrderOfferStatus(){
		return orderOfferStatus;
	}

	public void setHireStatus(String hireStatus){
		this.hireStatus = hireStatus;
	}

	public String getHireStatus(){
		return hireStatus;
	}

	public void setTotalDeliveryReward(String totalDeliveryReward){
		this.totalDeliveryReward = totalDeliveryReward;
	}

	public String getTotalDeliveryReward(){
		return totalDeliveryReward;
	}

	public List<ProductListItem> getProductList() {
		return productList;
	}

	public void setProductList(List<ProductListItem> productList) {
		this.productList = productList;
	}

	public void setOrderId(String orderId){
		this.orderId = orderId;
	}

	public String getOrderId(){
		return orderId;
	}

	public void setTotalOrderPrice(String totalOrderPrice){
		this.totalOrderPrice = totalOrderPrice;
	}

	public String getTotalOrderPrice(){
		return totalOrderPrice;
	}
}