package com.projects.nyinyihtunlwin.routeplanner.data.vo;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class RouteVO extends RealmObject {

    @Required
    @PrimaryKey
    private String id;

    private String name;

    private String type;

    private double lat;

    private double lng;

    private String currentAmount;

    private int code;

    private boolean isDone;

    private long reqAmount;

    private String reqType;

    public RouteVO() {
    }

    public RouteVO(String id, String name, String type, double lat, double lng, String currentAmount, int code, boolean isDone
            , long reqAmount, String reqType) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.lat = lat;
        this.lng = lng;
        this.currentAmount = currentAmount;
        this.code = code;
        this.isDone = isDone;
        this.reqAmount = reqAmount;
        this.reqType = reqType;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(String currentAmount) {
        this.currentAmount = currentAmount;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public long getReqAmount() {
        return reqAmount;
    }

    public void setReqAmount(long reqAmount) {
        this.reqAmount = reqAmount;
    }

    public String getReqType() {
        return reqType;
    }

    public void setReqType(String reqType) {
        this.reqType = reqType;
    }
}
