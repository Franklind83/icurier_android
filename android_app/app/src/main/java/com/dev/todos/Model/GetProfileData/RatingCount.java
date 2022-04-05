package com.dev.todos.Model.GetProfileData;

import com.google.gson.annotations.SerializedName;

public class RatingCount{

	@SerializedName("rating_count_5")
	private String ratingCount5;

	@SerializedName("rating_count_4")
	private String ratingCount4;

	@SerializedName("rating_count_3")
	private String ratingCount3;

	@SerializedName("rating_count_2")
	private String ratingCount2;

	@SerializedName("rating_count_1")
	private String ratingCount1;

	public void setRatingCount5(String ratingCount5){
		this.ratingCount5 = ratingCount5;
	}

	public String getRatingCount5(){
		return ratingCount5;
	}

	public void setRatingCount4(String ratingCount4){
		this.ratingCount4 = ratingCount4;
	}

	public String getRatingCount4(){
		return ratingCount4;
	}

	public void setRatingCount3(String ratingCount3){
		this.ratingCount3 = ratingCount3;
	}

	public String getRatingCount3(){
		return ratingCount3;
	}

	public void setRatingCount2(String ratingCount2){
		this.ratingCount2 = ratingCount2;
	}

	public String getRatingCount2(){
		return ratingCount2;
	}

	public void setRatingCount1(String ratingCount1){
		this.ratingCount1 = ratingCount1;
	}

	public String getRatingCount1(){
		return ratingCount1;
	}
}