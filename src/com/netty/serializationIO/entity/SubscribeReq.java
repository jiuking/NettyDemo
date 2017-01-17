package com.netty.serializationIO.entity;

import java.io.Serializable;

/**
 * Created by Bravowhale on 2017/1/17.
 */
public class SubscribeReq implements Serializable{

    private static final long serialVersionUID = -3357130849646994480L;
    private int subReqID;
    private String userName;
    private String productName;
    private String productNumber;
    private String phoneNumber;
    private String address;

    public SubscribeReq() {
    }

    public int getSubReqID() {

        return subReqID;
    }

    public void setSubReqID(int subReqID) {
        this.subReqID = subReqID;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "SubscribeReq [subReqID="+subReqID+",userName="+
                userName+",productName="+productName+",phoneNumber="
                +phoneNumber+",address="+address+"]";
    }
}
