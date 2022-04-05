package com.dev.todos.Util;

import java.util.ArrayList;

public class PurchaseMade {
    public static String orderId;
    public static String purchaseMadeDate;
    public static ArrayList<String> base64ImageArray;

    public static String getOrderId() {
        return orderId;
    }

    public static void setOrderId(String orderId) {
        PurchaseMade.orderId = orderId;
    }

    public static String getPurchaseMadeDate() {
        return purchaseMadeDate;
    }

    public static void setPurchaseMadeDate(String purchaseMadeDate) {
        PurchaseMade.purchaseMadeDate = purchaseMadeDate;
    }

    public static ArrayList<String> getBase64ImageArray() {
        return base64ImageArray;
    }

    public static void setBase64ImageArray(ArrayList<String> base64ImageArray) {
        PurchaseMade.base64ImageArray = base64ImageArray;
    }
}
