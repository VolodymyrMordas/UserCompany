package com.usercompany.network;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class ResponseModel<T> implements Serializable {
    private String status;
    private String htext;
    private String text;
    private String message;
    private Integer code;
    private HashMap<String,List<T>> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHtext() {
        return htext;
    }

    public void setHtext(String htext) {
        this.htext = htext;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public HashMap<String, List<T>> getData() {
        return data;
    }

    public void setData(HashMap<String, List<T>> data) {
        this.data = data;
    }
}