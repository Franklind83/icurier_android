package com.dev.todos.Model.GetBankInfo;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class GetBankInfoResponse{

	@SerializedName("msg")
	private String msg;

	@SerializedName("bank_list")
	private List<BankListItem> bankList;

	@SerializedName("status")
	private String status;

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}

	public void setBankList(List<BankListItem> bankList){
		this.bankList = bankList;
	}

	public List<BankListItem> getBankList(){
		return bankList;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}