package com.manappuram.msmetracker.network;

import com.manappuram.msmetracker.dashboard.modelclass.ActivitylistResponse;
import com.manappuram.msmetracker.dashboard.modelclass.ImageViewResponse;
import com.manappuram.msmetracker.dashboard.modelclass.StartServiceResponse;
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
                                     @Field("tocken") String tocken,
                                     @Field("deviceid") String deviceid);

    @POST("Get_activity_list")
    @FormUrlEncoded
    Call<ActivitylistResponse> Get_activity_list(@Field("p_data") String p_data);

    @POST("MSME_start_activity")
    @FormUrlEncoded
    Call<StartServiceResponse> MSME_start_activity(@Field("p_data") String p_data,
                                                   @Field("image") String image);

    @POST("MSME_end_activity")
    @FormUrlEncoded
    Call<StartServiceResponse> MSME_end_activity(@Field("p_data") String p_data,
                                                 @Field("image") String image);

    @POST("photo_view")
    @FormUrlEncoded
    Call<ImageViewResponse> photo_view(@Field("p_data") String p_data,
                                       @Field("flag") String image);


}

