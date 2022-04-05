package com.dev.todos.Model.AddSupport;

import com.google.gson.annotations.SerializedName;

public class AddSupportRequest {

    @SerializedName("subject")
    private String subject;

    @SerializedName("user_id")
    private String userId;

    @SerializedName("description")
    private String description;

    @SerializedName("reason")
    private String reason;

    @SerializedName("date")
    private String date;

    @SerializedName("time")
    private String time;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
