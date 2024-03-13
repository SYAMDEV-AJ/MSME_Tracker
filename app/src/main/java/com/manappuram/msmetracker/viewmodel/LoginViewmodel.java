package com.manappuram.msmetracker.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.manappuram.msmetracker.base.BaseRepository;
import com.manappuram.msmetracker.dashboard.modelclass.ActivitylistResponse;
import com.manappuram.msmetracker.dashboard.modelclass.ImageViewResponse;
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
import com.manappuram.msmetracker.repository.LoginRepository;

import java.util.concurrent.locks.StampedLock;

public class LoginViewmodel extends ViewModel {

    public LoginRepository loginRepository = new LoginRepository();

    public LoginRepository getLoginRepository() {
        return loginRepository;
    }

    public MutableLiveData<LoginResponse> loginResponseMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<ActivitylistResponse> activitylistResponseMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<StartServiceResponse> startServiceResponseMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<StartServiceResponse> endServiceResponseMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<ImageViewResponse> imageViewResponseMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<ActivityCheckResponse> activityCheckResponseMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<ReportActivityListReponse> reportActivityListReponseMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<ReportTotalCountResponse> reportTotalCountMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<StateListReponse> stateListReponseMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<BranchListReponse> branchListReponseMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<DepartmentWiseListReponse> departmentWiseListReponseMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<BranchDetailsReponse> branchDetailsReponseMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<DeviceRegistrationResponse> deviceRegistrationResponseMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<DeviceVerificationResponse> deviceVerificationResponseMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<DeviceUpdationResponse> deviceUpdationResponseMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<DeviceDeletionResponse> deviceDeletionResponseMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> errorMutableLiveData = new MutableLiveData<>();


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

    public void Get_activity_listdrop(String p_data) {
        getLoginRepository().Get_activity_listdrop(p_data, (BaseRepository.SuccessResponse<ReportActivityListReponse>) reportActivityListReponse -> {
            reportActivityListReponseMutableLiveData.setValue(reportActivityListReponse);
        });
    }

    public void gettotalcount_totaltraveldistance(String p_data) {
        getLoginRepository().gettotalcount_totaltraveldistance(p_data, (BaseRepository.SuccessResponse<ReportTotalCountResponse>) reportTotalCount -> {
            reportTotalCountMutableLiveData.setValue(reportTotalCount);
        });
    }

    public void getactivitytotalcount_totaltraveldistance(String p_data) {
        getLoginRepository().getactivitytotalcount_totaltraveldistance(p_data, (BaseRepository.SuccessResponse<ReportTotalCountResponse>) reportTotalCount -> {
            reportTotalCountMutableLiveData.setValue(reportTotalCount);
        });
    }


    public void Get_All_state(String p_data) {
        getLoginRepository().Get_All_state(p_data, (BaseRepository.SuccessResponse<StateListReponse>) stateListReponse -> {
            stateListReponseMutableLiveData.setValue(stateListReponse);
        });
    }

    public void Get_All_branch(String p_data) {
        getLoginRepository().Get_All_branch(p_data, (BaseRepository.SuccessResponse<BranchListReponse>) branchListReponse -> {
            branchListReponseMutableLiveData.setValue(branchListReponse);
        });
    }

    public void getdepartmentwise(String p_data) {
        getLoginRepository().getdepartmentwise(p_data, (BaseRepository.SuccessResponse<DepartmentWiseListReponse>) departmentWiseListReponse -> {
            departmentWiseListReponseMutableLiveData.setValue(departmentWiseListReponse);
        });
    }

    public void getdepartmentwiseall(String p_data) {
        getLoginRepository().getdepartmentwiseall(p_data, (BaseRepository.SuccessResponse<DepartmentWiseListReponse>) departmentWiseListReponse -> {
            departmentWiseListReponseMutableLiveData.setValue(departmentWiseListReponse);
        });
    }


    public void getmovementwise(String p_data) {
        getLoginRepository().getmovementwise(p_data, (BaseRepository.SuccessResponse<BranchDetailsReponse>) branchDetailsReponse -> {
            branchDetailsReponseMutableLiveData.setValue(branchDetailsReponse);
        });
    }

    public void getmovementwiseall(String p_data) {
        getLoginRepository().getmovementwiseall(p_data, (BaseRepository.SuccessResponse<BranchDetailsReponse>) branchDetailsReponse -> {
            branchDetailsReponseMutableLiveData.setValue(branchDetailsReponse);
        });
    }

    public void Msme_Deviceid_Insertion(String p_data) {
        getLoginRepository().Msme_Deviceid_Insertion(p_data, (BaseRepository.SuccessResponse<DeviceRegistrationResponse>) deviceRegistrationResponse -> {
            deviceRegistrationResponseMutableLiveData.setValue(deviceRegistrationResponse);
        });
    }

    public void deviceid_verify(String p_data) {
        getLoginRepository().deviceid_verify(p_data, (BaseRepository.SuccessResponse<DeviceVerificationResponse>) deviceVerificationResponse -> {
            deviceVerificationResponseMutableLiveData.setValue(deviceVerificationResponse);
        });
    }

    public void Msme_Deviceid_Updation(String p_data) {
        getLoginRepository().Msme_Deviceid_Updation(p_data, (BaseRepository.SuccessResponse<DeviceUpdationResponse>) deviceUpdationResponse -> {
            deviceUpdationResponseMutableLiveData.setValue(deviceUpdationResponse);
        });
    }

    public void Msme_Deviceid_delete(String p_data) {
        getLoginRepository().Msme_Deviceid_delete(p_data, (BaseRepository.SuccessResponse<DeviceDeletionResponse>) deviceDeletionResponse -> {
            deviceDeletionResponseMutableLiveData.setValue(deviceDeletionResponse);
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

    public MutableLiveData<ReportActivityListReponse> getReportActivityListReponseMutableLiveData() {
        return reportActivityListReponseMutableLiveData;
    }

    public MutableLiveData<ReportTotalCountResponse> getReportTotalCountMutableLiveData() {
        return reportTotalCountMutableLiveData;
    }

    public MutableLiveData<StateListReponse> getStateListReponseMutableLiveData() {
        return stateListReponseMutableLiveData;
    }

    public MutableLiveData<BranchListReponse> getBranchListReponseMutableLiveData() {
        return branchListReponseMutableLiveData;
    }

    public MutableLiveData<DepartmentWiseListReponse> getDepartmentWiseListReponseMutableLiveData() {
        return departmentWiseListReponseMutableLiveData;
    }

    public MutableLiveData<BranchDetailsReponse> getBranchDetailsReponseMutableLiveData() {
        return branchDetailsReponseMutableLiveData;
    }

    public MutableLiveData<DeviceRegistrationResponse> getDeviceRegistrationResponseMutableLiveData() {
        return deviceRegistrationResponseMutableLiveData;
    }

    public MutableLiveData<DeviceVerificationResponse> getDeviceVerificationResponseMutableLiveData() {
        return deviceVerificationResponseMutableLiveData;
    }

    public MutableLiveData<DeviceUpdationResponse> getDeviceUpdationResponseMutableLiveData() {
        return deviceUpdationResponseMutableLiveData;
    }

    public MutableLiveData<DeviceDeletionResponse> getDeviceDeletionResponseMutableLiveData() {
        return deviceDeletionResponseMutableLiveData;
    }


}
