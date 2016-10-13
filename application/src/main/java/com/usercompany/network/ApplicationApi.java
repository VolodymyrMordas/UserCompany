package com.usercompany.network;

import retrofit.http.Body;
import retrofit.http.PUT;

public interface ApplicationApi {
    @PUT("/user")
    ResponseModel userSignup(@Body RequestModel request);

//    @POST("/user")
//    ResponseModel userUpdate(@Body RequestModel request);
//
//    @DELETE("/user/delete")
//    ResponseModel userDelete(@Body RequestModel request);
//
//    @GET("/user")
//    ResponseModel user(@Body RequestModel request);
}
