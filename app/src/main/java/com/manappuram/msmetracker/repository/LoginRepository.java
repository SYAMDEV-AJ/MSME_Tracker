package com.manappuram.msmetracker.repository;

import com.manappuram.msmetracker.base.BaseRepository;
import com.manappuram.msmetracker.base.BaseResponse;
import com.manappuram.msmetracker.base.Event;
import com.manappuram.msmetracker.dashboard.modelclass.ActivitylistResponse;
import com.manappuram.msmetracker.dashboard.modelclass.ImageViewResponse;
import com.manappuram.msmetracker.dashboard.modelclass.StartServiceResponse;
import com.manappuram.msmetracker.login.model.LoginResponse;
import com.manappuram.msmetracker.network.retrofit.ResponseListener;
import com.manappuram.msmetracker.network.retrofit.RetrofitClient;
import com.manappuram.msmetracker.network.retrofit.RetrofitRequest;

import okhttp3.Headers;
import retrofit2.Call;

public class LoginRepository extends BaseRepository {

    public void userLogin(String empCode, String pwd, String tocken, String deviceid, SuccessResponse successResponse) {

        Call<LoginResponse> call = RetrofitClient.getAPIInterface().Androidlogin(empCode, pwd, tocken, deviceid);
        new RetrofitRequest<>(call, new ResponseListener<LoginResponse>() {
            @Override
            public void onResponse(LoginResponse response, Headers headers) {
                if (null != successResponse) {
                    successResponse.onResponse(response);
                }
            }

            @Override
            public void onError(int status, BaseResponse errors) {
                errorsMutable.postValue(new Event<>(errors));

            }


            @Override
            public void onFailure(Throwable throwable) {
                failMessageMutable.postValue(new Event<>(throwable.getMessage()));
            }

        }).enqueue();
    }

    public void Get_activity_list(String p_data, SuccessResponse successResponse) {

        Call<ActivitylistResponse> call = RetrofitClient.getAPIInterface().Get_activity_list(p_data);
        new RetrofitRequest<>(call, new ResponseListener<ActivitylistResponse>() {
            @Override
            public void onResponse(ActivitylistResponse response, Headers headers) {
                if (null != successResponse) {
                    successResponse.onResponse(response);
                }
            }

            @Override
            public void onError(int status, BaseResponse errors) {
                errorsMutable.postValue(new Event<>(errors));

            }


            @Override
            public void onFailure(Throwable throwable) {
                failMessageMutable.postValue(new Event<>(throwable.getMessage()));
            }

        }).enqueue();
    }

    public void MSME_start_activity(String p_data, String image, SuccessResponse successResponse) {

        Call<StartServiceResponse> call = RetrofitClient.getAPIInterface().MSME_start_activity(p_data, image);
        new RetrofitRequest<>(call, new ResponseListener<StartServiceResponse>() {
            @Override
            public void onResponse(StartServiceResponse response, Headers headers) {
                if (null != successResponse) {
                    successResponse.onResponse(response);
                }
            }

            @Override
            public void onError(int status, BaseResponse errors) {
                errorsMutable.postValue(new Event<>(errors));

            }


            @Override
            public void onFailure(Throwable throwable) {
                failMessageMutable.postValue(new Event<>(throwable.getMessage()));
            }

        }).enqueue();
    }

    public void MSME_end_activity(String p_data, String image, SuccessResponse successResponse) {

        Call<StartServiceResponse> call = RetrofitClient.getAPIInterface().MSME_end_activity(p_data, image);
        new RetrofitRequest<>(call, new ResponseListener<StartServiceResponse>() {
            @Override
            public void onResponse(StartServiceResponse response, Headers headers) {
                if (null != successResponse) {
                    successResponse.onResponse(response);
                }
            }

            @Override
            public void onError(int status, BaseResponse errors) {
                errorsMutable.postValue(new Event<>(errors));

            }


            @Override
            public void onFailure(Throwable throwable) {
                failMessageMutable.postValue(new Event<>(throwable.getMessage()));
            }

        }).enqueue();
    }

    public void photo_view(String p_data, String image, SuccessResponse successResponse) {

        Call<ImageViewResponse> call = RetrofitClient.getAPIInterface().photo_view(p_data, image);
        new RetrofitRequest<>(call, new ResponseListener<ImageViewResponse>() {
            @Override
            public void onResponse(ImageViewResponse response, Headers headers) {
                if (null != successResponse) {
                    successResponse.onResponse(response);
                }
            }

            @Override
            public void onError(int status, BaseResponse errors) {
                errorsMutable.postValue(new Event<>(errors));

            }


            @Override
            public void onFailure(Throwable throwable) {
                failMessageMutable.postValue(new Event<>(throwable.getMessage()));
            }

        }).enqueue();
    }


}
