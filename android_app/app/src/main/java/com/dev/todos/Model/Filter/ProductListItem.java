package com.dev.todos.Model.Filter;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ProductListItem{

	@SerializedName("delivery_deadline")
	private String deliveryDeadline;

	@SerializedName("created_time")
	private String createdTime;

	@SerializedName("buy_item_link")
	private String buyItemLink;

	@SerializedName("item_price")
	private String itemPrice;

	@SerializedName("article_name")
	private String articleName;

	@SerializedName("location_to")
	private String locationTo;

	@SerializedName("article_comment")
	private String articleComment;

	@SerializedName("product_id")
	private String productId;

	@SerializedName("location_from")
	private String locationFrom;

	@SerializedName("created_date")
	private String createdDate;

	@SerializedName("product_image_list")
	private List<String> productImageList;

	@SerializedName("delivery_reward")
	private String deliveryReward;

	@SerializedName("item_quantity")
	private String itemQuantity;

	public void setDeliveryDeadline(String deliveryDeadline){
		this.deliveryDeadline = deliveryDeadline;
	}

	public String getDeliveryDeadline(){
		return deliveryDeadline;
	}

	public void setCreatedTime(String createdTime){
		this.createdTime = createdTime;
	}

	public String getCreatedTime(){
		return createdTime;
	}

	public void setBuyItemLink(String buyItemLink){
		this.buyItemLink = buyItemLink;
	}

	public String getBuyItemLink(){
		return buyItemLink;
	}

	public void setItemPrice(String itemPrice){
		this.itemPrice = itemPrice;
	}

	public String getItemPrice(){
		return itemPrice;
	}

	public void setArticleName(String articleName){
		this.articleName = articleName;
	}

	public String getArticleName(){
		return articleName;
	}

	public void setLocationTo(String locationTo){
		this.locationTo = locationTo;
	}

	public String getLocationTo(){
		return locationTo;
	}

	public void setArticleComment(String articleComment){
		this.articleComment = articleComment;
	}

	public String getArticleComment(){
		return articleComment;
	}

	public void setProductId(String productId){
		this.productId = productId;
	}

	public String getProductId(){
		return productId;
	}

	public void setLocationFrom(String locationFrom){
		this.locationFrom = locationFrom;
	}

	public String getLocationFrom(){
		return locationFrom;
	}

	public void setCreatedDate(String createdDate){
		this.createdDate = createdDate;
	}

	public String getCreatedDate(){
		return createdDate;
	}

	public void setProductImageList(List<String> productImageList){
		this.productImageList = productImageList;
	}

	public List<String> getProductImageList(){
		return productImageList;
	}

	public void setDeliveryReward(String deliveryReward){
		this.deliveryReward = deliveryReward;
	}

	public String getDeliveryReward(){
		return deliveryReward;
	}

	public void setItemQuantity(String itemQuantity){
		this.itemQuantity = itemQuantity;
	}

	public String getItemQuantity(){
		return itemQuantity;
	}
}