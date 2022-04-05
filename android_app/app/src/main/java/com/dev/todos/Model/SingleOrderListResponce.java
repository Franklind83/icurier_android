package com.dev.todos.Model;

import com.dev.todos.Model.SingleorderList.ProductListItem;

import java.util.ArrayList;
import java.util.List;

public class SingleOrderListResponce {
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    String status;
    String msg;


    public OrderDetalis getOrder_details() {
        return order_details;
    }

    public void setOrder_details(OrderDetalis order_details) {
        this.order_details = order_details;
    }


    private OrderDetalis order_details;



    public class OrderDetalis{
        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUser_image() {
            return user_image;
        }

        public void setUser_image(String user_image) {
            this.user_image = user_image;
        }

        public List<ProductListItem> getProduct_list() {
            return product_list;
        }

        public void setProduct_list(ArrayList<ProductListItem> product_list) {
            this.product_list = product_list;
        }

        String user_id;
        String user_image;

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        String user_name;
        String total_order_price;
        String total_delivery_reward;

        public String getTotal_delivery_reward() {
            return total_delivery_reward;
        }

        public void setTotal_delivery_reward(String total_delivery_reward) {
            this.total_delivery_reward = total_delivery_reward;
        }

        public String getTotal_order_price() {
            return total_order_price;
        }

        public void setTotal_order_price(String total_order_price) {
            this.total_order_price = total_order_price;
        }

        ArrayList<ProductListItem> product_list;

        public ArrayList<OrderOfferList> getOrder_offer_list() {
            return order_offer_list;
        }

        public void setOrder_offer_list(ArrayList<OrderOfferList> order_offer_list) {
            this.order_offer_list = order_offer_list;
        }

        ArrayList<OrderOfferList> order_offer_list;

    }

    public class OrderOfferList{
        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUser_rating() {
            return user_rating;
        }

        public void setUser_rating(String user_rating) {
            this.user_rating = user_rating;
        }

        public String getTaxes_fees() {
            return taxes_fees;
        }

        public void setTaxes_fees(String taxes_fees) {
            this.taxes_fees = taxes_fees;
        }

        public String getDelivery_date() {
            return delivery_date;
        }

        public void setDelivery_date(String delivery_date) {
            this.delivery_date = delivery_date;
        }

        public String getDelivery_to() {
            return delivery_to;
        }

        public void setDelivery_to(String delivery_to) {
            this.delivery_to = delivery_to;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getDelivery_from() {
            return delivery_from;
        }

        public void setDelivery_from(String delivery_from) {
            this.delivery_from = delivery_from;
        }

        public String getOffer_order_id() {
            return offer_order_id;
        }

        public void setOffer_order_id(String offer_order_id) {
            this.offer_order_id = offer_order_id;
        }

        public String getUser_image() {
            return user_image;
        }

        public void setUser_image(String user_image) {
            this.user_image = user_image;
        }

        public String getReward() {
            return reward;
        }

        public void setReward(String reward) {
            this.reward = reward;
        }

        public String getCreated_date() {
            return created_date;
        }

        public void setCreated_date(String created_date) {
            this.created_date = created_date;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getShipping_cost() {
            return shipping_cost;
        }

        public void setShipping_cost(String shipping_cost) {
            this.shipping_cost = shipping_cost;
        }

        String user_id;
        String user_rating;
        String taxes_fees;
        String delivery_date;
        String delivery_to;
        String status;
        String order_id;
        String delivery_from;
        String offer_order_id;
        String user_image;
        String reward;
        String created_date;
        String user_name;
        String shipping_cost;


    }
}
