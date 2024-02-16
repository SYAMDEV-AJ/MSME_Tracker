package com.manappuram.msmetracker.network;

import com.manappuram.msmetracker.dashboard.modelclass.ActivitylistResponse;
import com.manappuram.msmetracker.dashboard.modelclass.ImageViewResponse;
import com.manappuram.msmetracker.dashboard.modelclass.StartServiceResponse;
import com.manappuram.msmetracker.login.model.ActivityCheckResponse;
import com.manappuram.msmetracker.login.model.LoginResponse;
import com.manappuram.msmetracker.reports.modelclass.ReportActivityListReponse;
import com.manappuram.msmetracker.reports.modelclass.ReportTotalCountResponse;
import com.manappuram.msmetracker.reports.modelclass.StateListReponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

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

    @POST("MSME_live_activity")
    @FormUrlEncoded
    Call<ActivityCheckResponse> MSME_live_activity(@Field("p_data") String p_data);

    //report
    @POST("MSME_live_activity")
    @FormUrlEncoded
    Call<ReportActivityListReponse> Get_activity_listdrop(@Field("p_data") String p_data);

    @POST("gettotalcount_totaltraveldistance")
    @FormUrlEncoded
    Call<ReportTotalCountResponse> gettotalcount_totaltraveldistance(@Field("p_data") String p_data);

    @POST("Get_All_state")
    @FormUrlEncoded
    Call<StateListReponse> Get_All_state(@Field("p_data") String p_data);

}

