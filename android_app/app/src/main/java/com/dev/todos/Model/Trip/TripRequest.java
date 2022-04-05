package com.dev.todos.Model.Trip;

import com.google.gson.annotations.SerializedName;

public class TripRequest {


    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    @SerializedName("trip_id")
    private String tripId;

    @SerializedName("location_from")
    private String locationFrom;

    @SerializedName("location_to")
    private String locationTo;

    @SerializedName("location_from_city")
    private String locationFromCity;

    @SerializedName("location_from_state")
    private String locationFromState;

    @SerializedName("location_from_country")
    private String locationFromCountry;

    @SerializedName("location_to_city")
    private String locationToCity;

    @SerializedName("location_to_state")
    private String locationToState;

    @SerializedName("location_to_country")
    private String locationToCountry;

    @SerializedName("travel_date")
    private String traveDate;

    @SerializedName("location_from_lat")
    private String location_latfrom;

    @SerializedName("location_from_lng")
    private String location_longfrom;

    @SerializedName("location_to_lat")
    private String location_latto;

    @SerializedName("location_to_lng")
    private String location_longto;

    public String getLocationFrom() {
        return locationFrom;
    }

    public void setLocationFrom(String locationFrom) {
        this.locationFrom = locationFrom;
    }

    public String getLocationTo() {
        return locationTo;
    }

    public void setLocationTo(String locationTo) {
        this.locationTo = locationTo;
    }

    public String getLocationFromCity() {
        return locationFromCity;
    }

    public void setLocationFromCity(String locationFromCity) {
        this.locationFromCity = locationFromCity;
    }

    public String getLocationFromState() {
        return locationFromState;
    }

    public void setLocationFromState(String locationFromState) {
        this.locationFromState = locationFromState;
    }

    public String getLocationFromCountry() {
        return locationFromCountry;
    }

    public void setLocationFromCountry(String locationFromCountry) {
        this.locationFromCountry = locationFromCountry;
    }

    public String getLocationToCity() {
        return locationToCity;
    }

    public void setLocationToCity(String locationToCity) {
        this.locationToCity = locationToCity;
    }

    public String getLocationToState() {
        return locationToState;
    }

    public void setLocationToState(String locationToState) {
        this.locationToState = locationToState;
    }

    public String getLocationToCountry() {
        return locationToCountry;
    }

    public void setLocationToCountry(String locationToCountry) {
        this.locationToCountry = locationToCountry;
    }

    public String getTraveDate() {
        return traveDate;
    }

    public void setTraveDate(String traveDate) {
        this.traveDate = traveDate;
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
