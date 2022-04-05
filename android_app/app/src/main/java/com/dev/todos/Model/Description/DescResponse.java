package com.dev.todos.Model.Description;

import com.google.gson.annotations.SerializedName;

public class DescResponse{

	@SerializedName("msg")
	private String msg;

	@SerializedName("contact_page_email")
	private String contactPageEmail;

	@SerializedName("privacy_policy_page_description")
	private String privacyPolicyPageDescription;

	@SerializedName("contact_page_instagram_link")
	private String contactPageInstagramLink;

	@SerializedName("terms_and_condition_page_description")
	private String termsAndConditionPageDescription;

	@SerializedName("contact_page_description")
	private String contactPageDescription;

	@SerializedName("faq_page_description")
	private String faqPageDescription;

	@SerializedName("contact_page_facebook_link")
	private String contactPageFacebookLink;

	@SerializedName("contact_page_telephone_2")
	private String contactPageTelephone2;

	@SerializedName("contact_page_telephone_1")
	private String contactPageTelephone1;

	@SerializedName("contact_page_website_link")
	private String contactPageWebsiteLink;

	@SerializedName("status")
	private String status;

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}

	public void setContactPageEmail(String contactPageEmail){
		this.contactPageEmail = contactPageEmail;
	}

	public String getContactPageEmail(){
		return contactPageEmail;
	}

	public void setPrivacyPolicyPageDescription(String privacyPolicyPageDescription){
		this.privacyPolicyPageDescription = privacyPolicyPageDescription;
	}

	public String getPrivacyPolicyPageDescription(){
		return privacyPolicyPageDescription;
	}

	public void setContactPageInstagramLink(String contactPageInstagramLink){
		this.contactPageInstagramLink = contactPageInstagramLink;
	}

	public String getContactPageInstagramLink(){
		return contactPageInstagramLink;
	}

	public void setTermsAndConditionPageDescription(String termsAndConditionPageDescription){
		this.termsAndConditionPageDescription = termsAndConditionPageDescription;
	}

	public String getTermsAndConditionPageDescription(){
		return termsAndConditionPageDescription;
	}

	public void setContactPageDescription(String contactPageDescription){
		this.contactPageDescription = contactPageDescription;
	}

	public String getContactPageDescription(){
		return contactPageDescription;
	}

	public void setFaqPageDescription(String faqPageDescription){
		this.faqPageDescription = faqPageDescription;
	}

	public String getFaqPageDescription(){
		return faqPageDescription;
	}

	public void setContactPageFacebookLink(String contactPageFacebookLink){
		this.contactPageFacebookLink = contactPageFacebookLink;
	}

	public String getContactPageFacebookLink(){
		return contactPageFacebookLink;
	}

	public void setContactPageTelephone2(String contactPageTelephone2){
		this.contactPageTelephone2 = contactPageTelephone2;
	}

	public String getContactPageTelephone2(){
		return contactPageTelephone2;
	}

	public void setContactPageTelephone1(String contactPageTelephone1){
		this.contactPageTelephone1 = contactPageTelephone1;
	}

	public String getContactPageTelephone1(){
		return contactPageTelephone1;
	}

	public void setContactPageWebsiteLink(String contactPageWebsiteLink){
		this.contactPageWebsiteLink = contactPageWebsiteLink;
	}

	public String getContactPageWebsiteLink(){
		return contactPageWebsiteLink;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}