package com.manappuram.msmetracker.repository;

import com.manappuram.msmetracker.base.BaseRepository;
import com.manappuram.msmetracker.base.BaseResponse;
import com.manappuram.msmetracker.base.Event;
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


}
