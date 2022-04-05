package com.dev.todos.Model;




import java.util.ArrayList;

public class Responce {

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    private String msg;
    private String status;


    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    private String error;

    public ArrayList<TripList> getTripLists() {
        return trip_list;
    }

    public void setTripLists(ArrayList<TripList> tripLists) {
        this.trip_list = tripLists;
    }

    ArrayList<TripList> trip_list;
}
