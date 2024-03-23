package com.manappuram.msmetracker.network;

import com.manappuram.msmetracker.dashboard.modelclass.ActivitylistResponse;
import com.manappuram.msmetracker.dashboard.modelclass.ImageViewResponse;
import com.manappuram.msmetracker.dashboard.modelclass.MapDistanceResponse;
import com.manappuram.msmetracker.dashboard.modelclass.StartServiceResponse;
import com.manappuram.msmetracker.deviceupdation.response.DeviceDeletionResponse;
import com.manappuram.msmetracker.deviceupdation.response.DeviceRegistrationResponse;
import com.manappuram.msmetracker.deviceupdation.response.DeviceUpdationResponse;
import com.manappuram.msmetracker.deviceupdation.response.DeviceVerificationResponse;
import com.manappuram.msmetracker.login.model.ActivityCheckResponse;
import com.manappuram.msmetracker.login.model.LoginResponse;
import com.manappuram.msmetracker.reports.modelclass.BranchDetailsReponse;
import com.manappuram.msmetracker.reports.modelclass.BranchListReponse;
import com.manappuram.msmetracker.reports.modelclass.DepartmentWiseListReponse;
import com.manappuram.msmetracker.reports.modelclass.ReportActivityListReponse;
import com.manappuram.msmetracker.reports.modelclass.ReportTotalCountResponse;
import com.manappuram.msmetracker.reports.modelclass.StateListReponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIInterface {

    @POST("MSME_EmpDetails_newdevicecheck")
    @FormUrlEncoded
    Call<LoginResponse> Androidlogin(@Field("empCode") String empCode, @Field("pwd") String pwd, @Field("tocken") String tocken, @Field("deviceid") String deviceid);

    @POST("Get_activity_list")
    @FormUrlEncoded
    Call<ActivitylistResponse> Get_activity_list(@Field("p_data") String p_data);

    @POST("MSME_start_activity")
    @FormUrlEncoded
    Call<StartServiceResponse> MSME_start_activity(@Field("p_data") String p_data, @Field("image") String image);

    @POST("MSME_end_activity")
    @FormUrlEncoded
    Call<StartServiceResponse> MSME_end_activity(@Field("p_data") String p_data, @Field("image") String image);

    @POST("photo_view")
    @FormUrlEncoded
    Call<ImageViewResponse> photo_view(@Field("p_data") String p_data, @Field("flag") String image);

    @POST("MSME_live_activity")
    @FormUrlEncoded
    Call<ActivityCheckResponse> MSME_live_activity(@Field("p_data") String p_data);

    //report
    @POST("Get_activity_listdrop")
    @FormUrlEncoded
    Call<ReportActivityListReponse> Get_activity_listdrop(@Field("p_data") String p_data);

    @POST("gettotalcount_totaltraveldistance")
    @FormUrlEncoded
    Call<ReportTotalCountResponse> gettotalcount_totaltraveldistance(@Field("p_data") String p_data);

    @POST("getactivitytotalcount_totaltraveldistance")
    @FormUrlEncoded
    Call<ReportTotalCountResponse> getactivitytotalcount_totaltraveldistance(@Field("p_data") String p_data);


    @POST("Get_All_state")
    @FormUrlEncoded
    Call<StateListReponse> Get_All_state(@Field("p_data") String p_data);

    @POST("Get_All_branch")
    @FormUrlEncoded
    Call<BranchListReponse> Get_All_branch(@Field("p_data") String p_data);

    @POST("getdepartmentwise")
    @FormUrlEncoded
    Call<DepartmentWiseListReponse> getdepartmentwise(@Field("p_data") String p_data);

    @POST("getdepartmentwiseall")
    @FormUrlEncoded
    Call<DepartmentWiseListReponse> getdepartmentwiseall(@Field("p_data") String p_data);

    @POST("getmovementwise")
    @FormUrlEncoded
    Call<BranchDetailsReponse> getmovementwise(@Field("p_data") String p_data);

    @POST("getmovementwiseall")
    @FormUrlEncoded
    Call<BranchDetailsReponse> getmovementwiseall(@Field("p_data") String p_data);

    @POST("Msme_Deviceid_Insertion")
    @FormUrlEncoded
    Call<DeviceRegistrationResponse> Msme_Deviceid_Insertion(@Field("p_data") String p_data);

    @POST("deviceid_verify")
    @FormUrlEncoded
    Call<DeviceVerificationResponse> deviceid_verify(@Field("p_data") String p_data);

    @POST("Msme_Deviceid_Updation")
    @FormUrlEncoded
    Call<DeviceUpdationResponse> Msme_Deviceid_Updation(@Field("p_data") String p_data);

    @POST("Msme_Deviceid_delete")
    @FormUrlEncoded
    Call<DeviceDeletionResponse> Msme_Deviceid_delete(@Field("p_data") String p_data);

    @GET("json")
    Call<MapDistanceResponse> mapCall(@Query("units") String units, @Query("origins") String origins, @Query("destinations") String destinations, @Query("key") String key);


}

