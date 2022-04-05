package com.dev.todos.Model.GetOrderList;

import com.google.gson.annotations.SerializedName;

public class OrderOfferListItem{

	@SerializedName("reward")
	private String reward;

	@SerializedName("offer_order_id")
	private String offerOrderId;

	@SerializedName("user_image")
	private String userImage;

	@SerializedName("shipping_cost")
	private String shippingCost;

	@SerializedName("user_name")
	private String userName;

	@SerializedName("terms_and_condition")
	private String termsAndCondition;

	@SerializedName("delivery_to")
	private String deliveryTo;

	@SerializedName("hire_status")
	private String hireStatus;

	@SerializedName("rating_as_t")
	private String ratingAsT;

	@SerializedName("taxes_fees")
	private String taxesFees;

	@SerializedName("delivery_date")
	private String deliveryDate;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("rating_as_b")
	private String ratingAsB;

	@SerializedName("delivery_from")
	private String deliveryFrom;

	@SerializedName("created_date")
	private String createdDate;

	@SerializedName("order_id")
	private String orderId;

	@SerializedName("user_rating")
	private String userRating;

	@SerializedName("status")
	private String status;

	public void setReward(String reward){
		this.reward = reward;
	}

	public String getReward(){
		return reward;
	}

	public void setOfferOrderId(String offerOrderId){
		this.offerOrderId = offerOrderId;
	}

	public String getOfferOrderId(){
		return offerOrderId;
	}

	public void setUserImage(String userImage){
		this.userImage = userImage;
	}

	public String getUserImage(){
		return userImage;
	}

	public void setShippingCost(String shippingCost){
		this.shippingCost = shippingCost;
	}

	public String getShippingCost(){
		return shippingCost;
	}

	public void setUserName(String userName){
		this.userName = userName;
	}

	public String getUserName(){
		return userName;
	}

	public void setTermsAndCondition(String termsAndCondition){
		this.termsAndCondition = termsAndCondition;
	}

	public String getTermsAndCondition(){
		return termsAndCondition;
	}

	public void setDeliveryTo(String deliveryTo){
		this.deliveryTo = deliveryTo;
	}

	public String getDeliveryTo(){
		return deliveryTo;
	}

	public void setHireStatus(String hireStatus){
		this.hireStatus = hireStatus;
	}

	public String getHireStatus(){
		return hireStatus;
	}

	public void setRatingAsT(String ratingAsT){
		this.ratingAsT = ratingAsT;
	}

	public String getRatingAsT(){
		return ratingAsT;
	}

	public void setTaxesFees(String taxesFees){
		this.taxesFees = taxesFees;
	}

	public String getTaxesFees(){
		return taxesFees;
	}

	public void setDeliveryDate(String deliveryDate){
		this.deliveryDate = deliveryDate;
	}

	public String getDeliveryDate(){
		return deliveryDate;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setRatingAsB(String ratingAsB){
		this.ratingAsB = ratingAsB;
	}

	public String getRatingAsB(){
		return ratingAsB;
	}

	public void setDeliveryFrom(String deliveryFrom){
		this.deliveryFrom = deliveryFrom;
	}

	public String getDeliveryFrom(){
		return deliveryFrom;
	}

	public void setCreatedDate(String createdDate){
		this.createdDate = createdDate;
	}

	public String getCreatedDate(){
		return createdDate;
	}

	public void setOrderId(String orderId){
		this.orderId = orderId;
	}

	public String getOrderId(){
		return orderId;
	}

	public void setUserRating(String userRating){
		this.userRating = userRating;
	}

	public String getUserRating(){
		return userRating;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}