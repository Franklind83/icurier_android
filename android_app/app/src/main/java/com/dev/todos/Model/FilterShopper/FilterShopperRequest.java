package com.dev.todos.Model.FilterShopper;

import com.google.gson.annotations.SerializedName;

public class FilterShopperRequest{

	@SerializedName("location_from_country")
	private String locationFromCountry;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("location_to_country")
	private String locationToCountry;

	@SerializedName("location_from")
	private String locationFrom;

	@SerializedName("location_from_city")
	private String locationFromCity;

	@SerializedName("location_from_state")
	private String locationFromState;

	@SerializedName("until_date")
	private String untilDate;

	@SerializedName("location_to_city")
	private String locationToCity;

	@SerializedName("location_to")
	private String locationTo;

	@SerializedName("since_date")
	private String sinceDate;

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


	public void setLocationFromCountry(String locationFromCountry){
		this.locationFromCountry = locationFromCountry;
	}

	public String getLocationFromCountry(){
		return locationFromCountry;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setLocationToCountry(String locationToCountry){
		this.locationToCountry = locationToCountry;
	}

	public String getLocationToCountry(){
		return locationToCountry;
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

	public void setLocationFromState(String locationFromState){
		this.locationFromState = locationFromState;
	}

	public String getLocationFromState(){
		return locationFromState;
	}

	public void setUntilDate(String untilDate){
		this.untilDate = untilDate;
	}

	public String getUntilDate(){
		return untilDate;
	}

	public void setLocationToCity(String locationToCity){
		this.locationToCity = locationToCity;
	}

	public String getLocationToCity(){
		return locationToCity;
	}

	public void setLocationTo(String locationTo){
		this.locationTo = locationTo;
	}

	public String getLocationTo(){
		return locationTo;
	}

	public void setSinceDate(String sinceDate){
		this.sinceDate = sinceDate;
	}

	public String getSinceDate(){
		return sinceDate;
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