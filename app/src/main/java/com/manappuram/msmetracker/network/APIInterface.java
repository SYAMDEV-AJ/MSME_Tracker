package com.manappuram.msmetracker.network;

import com.manappuram.msmetracker.login.model.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIInterface {

    @POST("MSME_EmpDetails_newdevicecheck")
    @FormUrlEncoded
    Call<LoginResponse> Androidlogin(@Field("empCode") String empCode,
                                     @Field("pwd") String pwd,
                                     @Field("deviceid") String deviceid,
                                     @Field("tocken") String tocken);


}

