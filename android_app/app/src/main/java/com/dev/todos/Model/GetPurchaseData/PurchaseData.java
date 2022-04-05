package com.dev.todos.Model.GetPurchaseData;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class PurchaseData{

	@SerializedName("purchase_id")
	private String purchaseId;

	@SerializedName("purchase_date")
	private String purchaseDate;

	@SerializedName("order_id")
	private int orderId;

	@SerializedName("purchase_imgaes")
	private List<String> purchaseImgaes;

	public void setPurchaseId(String purchaseId){
		this.purchaseId = purchaseId;
	}

	public String getPurchaseId(){
		return purchaseId;
	}

	public void setPurchaseDate(String purchaseDate){
		this.purchaseDate = purchaseDate;
	}

	public String getPurchaseDate(){
		return purchaseDate;
	}

	public void setOrderId(int orderId){
		this.orderId = orderId;
	}

	public int getOrderId(){
		return orderId;
	}

	public void setPurchaseImgaes(List<String> purchaseImgaes){
		this.purchaseImgaes = purchaseImgaes;
	}

	public List<String> getPurchaseImgaes(){
		return purchaseImgaes;
	}


}