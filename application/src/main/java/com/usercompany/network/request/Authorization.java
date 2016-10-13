package com.usercompany.network.request;

import java.io.Serializable;

public class Authorization implements Serializable {

    public String auth_key;

    public Authorization(String authKey) {
        this.auth_key = authKey;
    }
}