package com.dev.todos.Model;

import java.util.ArrayList;

public class ProductDetail {
    public String getAricaleName() {
        return aricaleName;
    }

    public void setAricaleName(String aricaleName) {
        this.aricaleName = aricaleName;
    }

    public String getCommentsOfTheArticle() {
        return commentsOfTheArticle;
    }

    public void setCommentsOfTheArticle(String commentsOfTheArticle) {
        this.commentsOfTheArticle = commentsOfTheArticle;
    }

    public String getBuyItem() {
        return buyItem;
    }

    public void setBuyItem(String buyItem) {
        this.buyItem = buyItem;
    }

    public String getPriceOfItem() {
        return priceOfItem;
    }

    public void setPriceOfItem(String priceOfItem) {
        this.priceOfItem = priceOfItem;
    }

    public String getQunt() {
        return qunt;
    }

    public void setQunt(String qunt) {
        this.qunt = qunt;
    }


    private String aricaleName;
    private String commentsOfTheArticle;
    private String buyItem;
    private String priceOfItem;
    private String qunt;

    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getDeliveryLocation() {
        return deliveryLocation;
    }

    public void setDeliveryLocation(String deliveryLocation) {
        this.deliveryLocation = deliveryLocation;
    }

    public String getDeadLineDate() {
        return deadLineDate;
    }

    public void setDeadLineDate(String deadLineDate) {
        this.deadLineDate = deadLineDate;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String location_from_city;
    private String location_from_state;
    private String location_from_country;
    private String location_to_city;
    private String location_to_state;
    private String location_to_country;
    private String location_latfrom;
    private String location_longfrom;
    private String location_latto;
    private String location_longto;

    public String getLocation_from_city() {
        return location_from_city;
    }

    public void setLocation_from_city(String location_from_city) {
        this.location_from_city = location_from_city;
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

    public String getLocation_from_state() {
        return location_from_state;
    }

    public void setLocation_from_state(String location_from_state) {
        this.location_from_state = location_from_state;
    }

    public String getLocation_from_country() {
        return location_from_country;
    }

    public void setLocation_from_country(String location_from_country) {
        this.location_from_country = location_from_country;
    }

    public String getLocation_to_city() {
        return location_to_city;
    }

    public void setLocation_to_city(String location_to_city) {
        this.location_to_city = location_to_city;
    }

    public String getLocation_to_state() {
        return location_to_state;
    }

    public void setLocation_to_state(String location_to_state) {
        this.location_to_state = location_to_state;
    }

    public String getLocation_to_country() {
        return location_to_country;
    }

    public void setLocation_to_country(String location_to_country) {
        this.location_to_country = location_to_country;
    }

    private String fromLocation;
    private String deliveryLocation;
    private String deadLineDate;
    private String reward;
    private String description;

    public String getTraveller_id() {
        return traveller_id;
    }

    public void setTraveller_id(String traveller_id) {
        this.traveller_id = traveller_id;
    }

    private String traveller_id;

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    private String currentDate;
    private String currentTime;

    public String getProductLink() {
        return productLink;
    }

    public void setProductLink(String productLink) {
        this.productLink = productLink;
    }

    private String productLink;

    public ArrayList<String> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<String> photos) {
        this.photos = photos;
    }

    private ArrayList<String> photos;

    public String getProductImage() {
        return productImagePath;
    }

    public void setProductImage(String productImage) {
        this.productImagePath = productImage;
    }

    String productImagePath;

    public String getProductUri() {
        return productUri;
    }

    public void setProductUri(String productUri) {
        this.productUri = productUri;
    }

    String productUri;



}
