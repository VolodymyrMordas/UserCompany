package com.usercompany.network;

import com.usercompany.network.request.Authorization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RequestModel<T> {

    private Authorization authorization;
    private HashMap<String, List<T>> data;

    public RequestModel() {
    }

    public RequestModel(String authKey) {
        this.authorization = new Authorization(authKey);
    }

    public Authorization getAuthorization() {
        return authorization;
    }

    public void setAuthorization(Authorization authorization) {
        this.authorization = authorization;
    }

    public HashMap<String, List<T>> getData() {
        return data;
    }

    public void setData(HashMap<String, List<T>> data) {
        this.data = data;
    }

    public void addData(T t){
        if(this.data == null){
            this.data = new HashMap<>();
        }

        if(!this.data.containsKey(t.getClass().getSimpleName().toLowerCase())){
            this.data.put(t.getClass().getSimpleName().toLowerCase(), new ArrayList<T>());
        }

        this.data.get(t.getClass().getSimpleName().toLowerCase()).add(t);
    }
}
