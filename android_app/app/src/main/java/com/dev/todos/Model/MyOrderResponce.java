package com.dev.todos.Model;

import java.util.ArrayList;

public class MyOrderResponce {

    public String getMsg() {
        return msg;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    String error;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public OfferStatusCount getOffer_status_count() {
        return offer_status_count;
    }

    public void setOffer_status_count(OfferStatusCount offer_status_count) {
        this.offer_status_count = offer_status_count;
    }

    public ArrayList<OfferStatusCount.TravelOderList> getTravel_order_list() {
        return trip_order_list;
    }

    public void setTravel_order_list(ArrayList<OfferStatusCount.TravelOderList> travel_order_list) {
        this.trip_order_list = travel_order_list;
    }


    String msg;
    String status;
    OfferStatusCount offer_status_count;
    ArrayList<OfferStatusCount.TravelOderList>trip_order_list;


    public String getAll_order_count() {
        return all_order_count;
    }

    public void setAll_order_count(String all_order_count) {
        this.all_order_count = all_order_count;
    }

    public String getDelivered_order_count() {
        return delivered_order_count;
    }

    public void setDelivered_order_count(String delivered_order_count) {
        this.delivered_order_count = delivered_order_count;
    }

    public String getAccepted_order_count() {
        return accepted_order_count;
    }

    public void setAccepted_order_count(String accepted_order_count) {
        this.accepted_order_count = accepted_order_count;
    }

    public String getPending_order_count() {
        return pending_order_count;
    }

    public void setPending_order_count(String pending_order_count) {
        this.pending_order_count = pending_order_count;
    }

    String all_order_count;
    String delivered_order_count;
    String accepted_order_count;
    String pending_order_count;






    public class OfferStatusCount{
        public String getAccepted_offer_count() {
            return accepted_offer_count;
        }

        public void setAccepted_offer_count(String accepted_offer_count) {
            this.accepted_offer_count = accepted_offer_count;
        }

        public String getPending_offer_count() {
            return pending_offer_count;
        }

        public void setPending_offer_count(String pending_offer_count) {
            this.pending_offer_count = pending_offer_count;
        }

        public String getDelivered_offer_count() {
            return delivered_offer_count;
        }

        public void setDelivered_offer_count(String delivered_offer_count) {
            this.delivered_offer_count = delivered_offer_count;
        }

        public String getInactive_offer_count() {
            return inactive_offer_count;
        }

        public void setInactive_offer_count(String inactive_offer_count) {
            this.inactive_offer_count = inactive_offer_count;
        }

        String accepted_offer_count;
        String pending_offer_count;
        String delivered_offer_count;
        String inactive_offer_count;


        public class TravelOderList{

            public ArrayList<ProductList> getProduct_list() {
                return product_list;
            }

            public void setProduct_list(ArrayList<ProductList> product_list) {
                this.product_list = product_list;
            }

            ArrayList<ProductList>product_list;

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            String user_id;
            String offer_order_id;

            public String getOffer_order_id() {
                return offer_order_id;
            }

            public void setOffer_order_id(String offer_order_id) {
                this.offer_order_id = offer_order_id;
            }

            public String getLocation_to() {
                return location_to;
            }

            public void setLocation_to(String location_to) {
                this.location_to = location_to;
            }

            public String getArticle_comment() {
                return article_comment;
            }

            public void setArticle_comment(String article_comment) {
                this.article_comment = article_comment;
            }

            public String getItem_quantity() {
                return item_quantity;
            }

            public void setItem_quantity(String item_quantity) {
                this.item_quantity = item_quantity;
            }

            public String getCreated_date() {
                return created_date;
            }

            public void setCreated_date(String created_date) {
                this.created_date = created_date;
            }

            public String getProduct_id() {
                return product_id;
            }

            public void setProduct_id(String product_id) {
                this.product_id = product_id;
            }

            public String getArticle_name() {
                return article_name;
            }

            public void setArticle_name(String article_name) {
                this.article_name = article_name;
            }

            public String getDelivery_reward() {
                return delivery_reward;
            }

            public void setDelivery_reward(String delivery_reward) {
                this.delivery_reward = delivery_reward;
            }

            public String getItem_price() {
                return item_price;
            }

            public void setItem_price(String item_price) {
                this.item_price = item_price;
            }

            public String getBuy_item_link() {
                return buy_item_link;
            }

            public void setBuy_item_link(String buy_item_link) {
                this.buy_item_link = buy_item_link;
            }

            public String getDelivery_deadline() {
                return delivery_deadline;
            }

            public void setDelivery_deadline(String delivery_deadline) {
                this.delivery_deadline = delivery_deadline;
            }

            public String getCreated_time() {
                return created_time;
            }

            public void setCreated_time(String created_time) {
                this.created_time = created_time;
            }

            public String getLocation_from() {
                return location_from;
            }

            public void setLocation_from(String location_from) {
                this.location_from = location_from;
            }

            String location_to;
            String article_comment;
            String item_quantity;
            String created_date;
            String product_id;
            String article_name;
            String delivery_reward;
            String item_price;
            String buy_item_link;
            String delivery_deadline;
            String created_time;
            String location_from;

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public String getUser_image() {
                return user_image;
            }

            public void setUser_image(String user_image) {
                this.user_image = user_image;
            }

            public String getOrder_offer_status() {
                return order_offer_status;
            }

            public void setOrder_offer_status(String order_offer_status) {
                this.order_offer_status = order_offer_status;
            }

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

            public String getOrder_id() {
                return order_id;
            }

            public void setOrder_id(String order_id) {
                this.order_id = order_id;
            }

            String user_name;
            String user_image;
            String order_offer_status;
            String total_delivery_reward;
            String total_order_price;
            String order_id;





        }

    }
    public class ProductList{
        public String getCreated_time() {
            return created_time;
        }

        public void setCreated_time(String created_time) {
            this.created_time = created_time;
        }

        public String getArticle_comment() {
            return article_comment;
        }

        public void setArticle_comment(String article_comment) {
            this.article_comment = article_comment;
        }

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public String getArticle_name() {
            return article_name;
        }

        public void setArticle_name(String article_name) {
            this.article_name = article_name;
        }

        public ArrayList<String> getProduct_image_list() {
            return product_image_list;
        }

        public void setProduct_image_list(ArrayList<String> product_image_list) {
            this.product_image_list = product_image_list;
        }

        public String getBuy_item_link() {
            return buy_item_link;
        }

        public void setBuy_item_link(String buy_item_link) {
            this.buy_item_link = buy_item_link;
        }

        public String getItem_price() {
            return item_price;
        }

        public void setItem_price(String item_price) {
            this.item_price = item_price;
        }

        public String getLocation_to() {
            return location_to;
        }

        public void setLocation_to(String location_to) {
            this.location_to = location_to;
        }

        public String getCreated_date() {
            return created_date;
        }

        public void setCreated_date(String created_date) {
            this.created_date = created_date;
        }

        public String getDelivery_deadline() {
            return delivery_deadline;
        }

        public void setDelivery_deadline(String delivery_deadline) {
            this.delivery_deadline = delivery_deadline;
        }

        public String getLocation_from() {
            return location_from;
        }

        public void setLocation_from(String location_from) {
            this.location_from = location_from;
        }

        public String getItem_quantity() {
            return item_quantity;
        }

        public void setItem_quantity(String item_quantity) {
            this.item_quantity = item_quantity;
        }

        public String getDelivery_reward() {
            return delivery_reward;
        }

        public void setDelivery_reward(String delivery_reward) {
            this.delivery_reward = delivery_reward;
        }

        String created_time;
        String article_comment;
        String product_id;
        String article_name;
        ArrayList<String >product_image_list;
        String buy_item_link;
        String item_price;
        String location_to;
        String created_date;
        String delivery_deadline;
        String location_from;
        String item_quantity;
        String delivery_reward;
    }
}
