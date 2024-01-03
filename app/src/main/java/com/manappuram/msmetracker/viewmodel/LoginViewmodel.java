package com.manappuram.msmetracker.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.manappuram.msmetracker.base.BaseRepository;
import com.manappuram.msmetracker.login.model.LoginResponse;
import com.manappuram.msmetracker.repository.LoginRepository;

public class LoginViewmodel extends ViewModel {

    LoginRepository loginRepository = new LoginRepository();

    public LoginRepository getLoginRepository() {
        return loginRepository;
    }

    public MutableLiveData<LoginResponse> loginResponseMutableLiveData = new MutableLiveData<>();


    public void userLogin(String empCode, String pwd, String tocken, String deviceid) {
        getLoginRepository().userLogin(empCode, pwd, tocken, deviceid, (BaseRepository.SuccessResponse<LoginResponse>) loginResponse -> {
            loginResponseMutableLiveData.setValue(loginResponse);
        });
    }


    public MutableLiveData<LoginResponse> getLoginResponseMutableLiveData() {
        return loginResponseMutableLiveData;
    }


}
