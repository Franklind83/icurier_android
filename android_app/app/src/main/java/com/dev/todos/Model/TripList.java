package com.dev.todos.Model;

import com.google.gson.annotations.SerializedName;

public class TripList {
    public String getOrder_count() {
        return order_count;
    }

    public void setOrder_count(String order_count) {
        this.order_count = order_count;
    }

    public String getTotal_earning() {
        return total_earning;
    }

    public void setTotal_earning(String total_earning) {
        this.total_earning = total_earning;
    }

    public String getTrip_id() {
        return trip_id;
    }

    public void setTrip_id(String trip_id) {
        this.trip_id = trip_id;
    }

    public String getLocation_to() {
        return location_to;
    }

    public void setLocation_to(String location_to) {
        this.location_to = location_to;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getLocation_from() {
        return location_from;
    }

    public void setLocation_from(String location_from) {
        this.location_from = location_from;
    }

    public String getTravel_date() {
        return travel_date;
    }

    public void setTravel_date(String travel_date) {
        this.travel_date = travel_date;
    }

    String order_count;
    String total_earning;
    String trip_id;
    String location_to;
    String user_id;
    String location_from;
    String travel_date;
    String order_delivery_count;
    String accepted_status;
    String location_from_lat;
    String location_from_lng;
    String location_to_lat;
    String location_to_lng;

    public String getAccepted_status() {
        return accepted_status;
    }

    public void setAccepted_status(String accepted_status) {
        this.accepted_status = accepted_status;
    }

    public String getOrder_delivery_count() {
        return order_delivery_count;
    }

    public void setOrder_delivery_count(String order_delivery_count) {
        this.order_delivery_count = order_delivery_count;
    }
    public String getLocation_latfrom() {
        return location_from_lat;
    }

    public void setLocation_latfrom(String location_from_city) {
        this.location_from_lat = location_from_city;
    }
    public String getLocation_longfrom() {
        return location_from_lng;
    }

    public void setLocation_longfrom(String location_from_city) {
        this.location_from_lng = location_from_city;
    }

    public String getLocation_latto() {
        return location_to_lat;
    }

    public void setLocation_latto(String location_from_city)
    {
        this.location_to_lat = location_from_city;
    }
    public String getLocation_longto() {
        return location_to_lng;
    }

    public void setLocation_longto(String location_from_city) {
        this.location_to_lng = location_from_city;
    }




}

