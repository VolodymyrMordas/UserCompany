package com.usercompany.network.request;

import com.usercompany.network.RequestModel;

import java.io.Serializable;

public class FacebookLogin extends RequestModel implements Serializable {

    public FacebookLogin(){

    }
    public FacebookLogin(String authKey) {
        super(authKey);
    }
}
