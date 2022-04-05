package com.dev.todos.Model.GetRating;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class GetRatingResponse{

	@SerializedName("msg")
	private String msg;

	@SerializedName("rating_list")
	private List<RatingListItem> ratingList;

	@SerializedName("status")
	private String status;

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}

	public void setRatingList(List<RatingListItem> ratingList){
		this.ratingList = ratingList;
	}

	public List<RatingListItem> getRatingList(){
		return ratingList;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}