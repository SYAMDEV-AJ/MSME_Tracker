package com.manappuram.msmetracker.viewmodel;

import android.media.Image;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.manappuram.msmetracker.base.BaseRepository;
import com.manappuram.msmetracker.dashboard.modelclass.ActivitylistResponse;
import com.manappuram.msmetracker.dashboard.modelclass.ImageViewResponse;
import com.manappuram.msmetracker.dashboard.modelclass.StartServiceResponse;
import com.manappuram.msmetracker.login.model.ActivityCheckResponse;
import com.manappuram.msmetracker.login.model.LoginResponse;
import com.manappuram.msmetracker.repository.LoginRepository;

public class LoginViewmodel extends ViewModel {

    LoginRepository loginRepository = new LoginRepository();

    public LoginRepository getLoginRepository() {
        return loginRepository;
    }

    public MutableLiveData<LoginResponse> loginResponseMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<ActivitylistResponse> activitylistResponseMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<StartServiceResponse> startServiceResponseMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<StartServiceResponse> endServiceResponseMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<ImageViewResponse> imageViewResponseMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<ActivityCheckResponse> activityCheckResponseMutableLiveData = new MutableLiveData<>();


    public void userLogin(String empCode, String pwd, String tocken, String deviceid) {
        getLoginRepository().userLogin(empCode, pwd, tocken, deviceid, (BaseRepository.SuccessResponse<LoginResponse>) loginResponse -> {
            loginResponseMutableLiveData.setValue(loginResponse);
        });
    }

    public void Get_activity_list(String p_data) {
        getLoginRepository().Get_activity_list(p_data, (BaseRepository.SuccessResponse<ActivitylistResponse>) activitylistResponse -> {
            activitylistResponseMutableLiveData.setValue(activitylistResponse);
        });
    }

    public void MSME_start_activity(String p_data, String image) {
        getLoginRepository().MSME_start_activity(p_data, image, (BaseRepository.SuccessResponse<StartServiceResponse>) startServiceResponse -> {
            startServiceResponseMutableLiveData.setValue(startServiceResponse);
        });
    }

    public void MSME_end_activity(String p_data, String image) {
        getLoginRepository().MSME_end_activity(p_data, image, (BaseRepository.SuccessResponse<StartServiceResponse>) startServiceResponse -> {
            endServiceResponseMutableLiveData.setValue(startServiceResponse);
        });
    }

    public void photo_view(String p_data, String image) {
        getLoginRepository().photo_view(p_data, image, (BaseRepository.SuccessResponse<ImageViewResponse>) imageViewResponse -> {
            imageViewResponseMutableLiveData.setValue(imageViewResponse);
        });
    }

    public void MSME_live_activity(String p_data) {
        getLoginRepository().MSME_live_activity(p_data, (BaseRepository.SuccessResponse<ActivityCheckResponse>) activityCheckResponse -> {
            activityCheckResponseMutableLiveData.setValue(activityCheckResponse);
        });
    }


    public MutableLiveData<LoginResponse> getLoginResponseMutableLiveData() {
        return loginResponseMutableLiveData;
    }

    public MutableLiveData<ActivitylistResponse> getActivitylistResponseMutableLiveData() {
        return activitylistResponseMutableLiveData;
    }

    public MutableLiveData<StartServiceResponse> getStartServiceResponseMutableLiveData() {
        return startServiceResponseMutableLiveData;
    }

    public MutableLiveData<StartServiceResponse> getEndServiceResponseMutableLiveData() {
        return endServiceResponseMutableLiveData;
    }

    public MutableLiveData<ImageViewResponse> getImageViewResponseMutableLiveData() {
        return imageViewResponseMutableLiveData;
    }

    public MutableLiveData<ActivityCheckResponse> getActivityCheckResponseMutableLiveData() {
        return activityCheckResponseMutableLiveData;
    }


}
