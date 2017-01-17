package com.netty.serializationIO.entity;

import java.io.Serializable;

/**
 * Created by Bravowhale on 2017/1/17.
 */
public class SubscribeResp implements Serializable{
    private static final long serialVersionUID = -4694112223886257116L;
    private int subReqID;
    private int respCode;
    private String desc;

    public int getSubReqID() {
        return subReqID;
    }

    public void setSubReqID(int subReqID) {
        this.subReqID = subReqID;
    }

    public int getRespCode() {
        return respCode;
    }

    public void setRespCode(int respCode) {
        this.respCode = respCode;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "SubscribeResp [subReqId="+ subReqID+", respCode="
                +respCode+",desc="+desc+"]";
    }
}
