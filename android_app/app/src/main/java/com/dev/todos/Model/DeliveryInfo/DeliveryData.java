package com.dev.todos.Model.DeliveryInfo;

import com.google.gson.annotations.SerializedName;

public class DeliveryData{

	@SerializedName("delivery_date")
	private String deliveryDate;

	@SerializedName("delivered_image")
	private String deliveredImage;

	@SerializedName("delivered_to")
	private String deliveredTo;

	@SerializedName("delivered_person_doc_no")
	private String deliveredPersonDocNo;

	@SerializedName("delivery_id")
	private String deliveryId;

	@SerializedName("order_id")
	private String orderId;

	public void setDeliveryDate(String deliveryDate){
		this.deliveryDate = deliveryDate;
	}

	public String getDeliveryDate(){
		return deliveryDate;
	}

	public void setDeliveredImage(String deliveredImage){
		this.deliveredImage = deliveredImage;
	}

	public String getDeliveredImage(){
		return deliveredImage;
	}

	public void setDeliveredTo(String deliveredTo){
		this.deliveredTo = deliveredTo;
	}

	public String getDeliveredTo(){
		return deliveredTo;
	}

	public void setDeliveredPersonDocNo(String deliveredPersonDocNo){
		this.deliveredPersonDocNo = deliveredPersonDocNo;
	}

	public String getDeliveredPersonDocNo(){
		return deliveredPersonDocNo;
	}

	public void setDeliveryId(String deliveryId){
		this.deliveryId = deliveryId;
	}

	public String getDeliveryId(){
		return deliveryId;
	}

	public void setOrderId(String orderId){
		this.orderId = orderId;
	}

	public String getOrderId(){
		return orderId;
	}
}