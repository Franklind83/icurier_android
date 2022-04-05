package com.dev.todos.Model.SingleorderList;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ProductListItem{

	@SerializedName("delivery_deadline")
	private String deliveryDeadline;

	@SerializedName("created_time")
	private String createdTime;

	@SerializedName("buy_item_link")
	private String buyItemLink;

	@SerializedName("location_to_country")
	private String locationToCountry;

	@SerializedName("item_price")
	private String itemPrice;

	@SerializedName("article_name")
	private String articleName;

	@SerializedName("location_from_state")
	private String locationFromState;

	@SerializedName("location_to")
	private String locationTo;

	@SerializedName("location_from_country")
	private String locationFromCountry;

	@SerializedName("article_comment")
	private String articleComment;

	@SerializedName("product_id")
	private String productId;

	@SerializedName("location_from")
	private String locationFrom;

	@SerializedName("location_from_city")
	private String locationFromCity;

	@SerializedName("created_date")
	private String createdDate;

	@SerializedName("product_image_list")
	private List<String> productImageList;

	@SerializedName("location_to_city")
	private String locationToCity;

	@SerializedName("delivery_reward")
	private String deliveryReward;

	@SerializedName("item_quantity")
	private String itemQuantity;

	@SerializedName("location_to_state")
	private String locationToState;

	@SerializedName("location_from_lat")
	private String location_latfrom;
	@SerializedName("location_from_lng")
	private String location_longfrom;
	@SerializedName("location_to_lat")
	private String location_latto;
	@SerializedName("location_to_lng")
	private String location_longto;


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

	public void setLocationToCountry(String locationToCountry){
		this.locationToCountry = locationToCountry;
	}

	public String getLocationToCountry(){
		return locationToCountry;
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

	public void setLocationFromState(String locationFromState){
		this.locationFromState = locationFromState;
	}

	public String getLocationFromState(){
		return locationFromState;
	}

	public void setLocationTo(String locationTo){
		this.locationTo = locationTo;
	}

	public String getLocationTo(){
		return locationTo;
	}

	public void setLocationFromCountry(String locationFromCountry){
		this.locationFromCountry = locationFromCountry;
	}

	public String getLocationFromCountry(){
		return locationFromCountry;
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

	public void setLocationFromCity(String locationFromCity){
		this.locationFromCity = locationFromCity;
	}

	public String getLocationFromCity(){
		return locationFromCity;
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

	public void setLocationToCity(String locationToCity){
		this.locationToCity = locationToCity;
	}

	public String getLocationToCity(){
		return locationToCity;
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

	public void setLocationToState(String locationToState){
		this.locationToState = locationToState;
	}

	public String getLocationToState(){
		return locationToState;
	}


	public String getLocation_latfrom() {
		return location_latfrom;
	}

	public void setLocation_latfrom(String location_from_city) {
		this.location_latfrom = location_from_city;
	}
	public String getLocation_longfrom() {
		return location_longfrom;
	}

	public void setLocation_longfrom(String location_from_city) {
		this.location_longfrom = location_from_city;
	}

	public String getLocation_latto() {
		return location_latto;
	}

	public void setLocation_latto(String location_from_city)
	{
		this.location_latto = location_from_city;
	}
	public String getLocation_longto() {
		return location_longto;
	}

	public void setLocation_longto(String location_from_city) {
		this.location_longto = location_from_city;
	}


}