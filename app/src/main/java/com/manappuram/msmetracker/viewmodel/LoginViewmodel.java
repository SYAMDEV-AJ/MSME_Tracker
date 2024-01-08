package com.manappuram.msmetracker.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.manappuram.msmetracker.base.BaseRepository;
import com.manappuram.msmetracker.dashboard.modelclass.ActivitylistResponse;
import com.manappuram.msmetracker.dashboard.modelclass.StartServiceResponse;
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


    public MutableLiveData<LoginResponse> getLoginResponseMutableLiveData() {
        return loginResponseMutableLiveData;
    }

    public MutableLiveData<ActivitylistResponse> getActivitylistResponseMutableLiveData() {
        return activitylistResponseMutableLiveData;
    }

    public MutableLiveData<StartServiceResponse> getStartServiceResponseMutableLiveData() {
        return startServiceResponseMutableLiveData;
    }


}
