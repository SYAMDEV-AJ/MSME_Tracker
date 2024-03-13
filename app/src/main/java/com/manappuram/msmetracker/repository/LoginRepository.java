package com.manappuram.msmetracker.repository;

import com.manappuram.msmetracker.base.BaseRepository;
import com.manappuram.msmetracker.base.BaseResponse;
import com.manappuram.msmetracker.base.Event;
import com.manappuram.msmetracker.dashboard.modelclass.ActivitylistResponse;
import com.manappuram.msmetracker.dashboard.modelclass.ImageViewResponse;
import com.manappuram.msmetracker.dashboard.modelclass.StartServiceResponse;
import com.manappuram.msmetracker.deviceupdation.response.DeviceDeletionResponse;
import com.manappuram.msmetracker.deviceupdation.response.DeviceRegistrationResponse;
import com.manappuram.msmetracker.deviceupdation.response.DeviceUpdationResponse;
import com.manappuram.msmetracker.deviceupdation.response.DeviceVerificationResponse;
import com.manappuram.msmetracker.login.model.ActivityCheckResponse;
import com.manappuram.msmetracker.login.model.LoginResponse;
import com.manappuram.msmetracker.network.retrofit.ResponseListener;
import com.manappuram.msmetracker.network.retrofit.RetrofitClient;
import com.manappuram.msmetracker.network.retrofit.RetrofitRequest;
import com.manappuram.msmetracker.reports.modelclass.BranchDetailsReponse;
import com.manappuram.msmetracker.reports.modelclass.BranchListReponse;
import com.manappuram.msmetracker.reports.modelclass.DepartmentWiseListReponse;
import com.manappuram.msmetracker.reports.modelclass.ReportActivityListReponse;
import com.manappuram.msmetracker.reports.modelclass.ReportTotalCountResponse;
import com.manappuram.msmetracker.reports.modelclass.StateListReponse;

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

    public void MSME_live_activity(String p_data, SuccessResponse successResponse) {
        Call<ActivityCheckResponse> call = RetrofitClient.getAPIInterface().MSME_live_activity(p_data);
        new RetrofitRequest<>(call, new ResponseListener<ActivityCheckResponse>() {
            @Override
            public void onResponse(ActivityCheckResponse response, Headers headers) {
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

    public void Get_activity_listdrop(String p_data, SuccessResponse successResponse) {
        Call<ReportActivityListReponse> call = RetrofitClient.getAPIInterface().Get_activity_listdrop(p_data);
        new RetrofitRequest<>(call, new ResponseListener<ReportActivityListReponse>() {
            @Override
            public void onResponse(ReportActivityListReponse response, Headers headers) {
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

    public void gettotalcount_totaltraveldistance(String p_data, SuccessResponse successResponse) {
        Call<ReportTotalCountResponse> call = RetrofitClient.getAPIInterface().gettotalcount_totaltraveldistance(p_data);
        new RetrofitRequest<>(call, new ResponseListener<ReportTotalCountResponse>() {
            @Override
            public void onResponse(ReportTotalCountResponse response, Headers headers) {
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

    public void getactivitytotalcount_totaltraveldistance(String p_data, SuccessResponse successResponse) {
        Call<ReportTotalCountResponse> call = RetrofitClient.getAPIInterface().getactivitytotalcount_totaltraveldistance(p_data);
        new RetrofitRequest<>(call, new ResponseListener<ReportTotalCountResponse>() {
            @Override
            public void onResponse(ReportTotalCountResponse response, Headers headers) {
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


    public void Get_All_state(String p_data, SuccessResponse successResponse) {
        Call<StateListReponse> call = RetrofitClient.getAPIInterface().Get_All_state(p_data);
        new RetrofitRequest<>(call, new ResponseListener<StateListReponse>() {
            @Override
            public void onResponse(StateListReponse response, Headers headers) {
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

    public void Get_All_branch(String p_data, SuccessResponse successResponse) {
        Call<BranchListReponse> call = RetrofitClient.getAPIInterface().Get_All_branch(p_data);
        new RetrofitRequest<>(call, new ResponseListener<BranchListReponse>() {
            @Override
            public void onResponse(BranchListReponse response, Headers headers) {
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

    public void getdepartmentwise(String p_data, SuccessResponse successResponse) {
        Call<DepartmentWiseListReponse> call = RetrofitClient.getAPIInterface().getdepartmentwise(p_data);
        new RetrofitRequest<>(call, new ResponseListener<DepartmentWiseListReponse>() {
            @Override
            public void onResponse(DepartmentWiseListReponse response, Headers headers) {
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

    public void getdepartmentwiseall(String p_data, SuccessResponse successResponse) {
        Call<DepartmentWiseListReponse> call = RetrofitClient.getAPIInterface().getdepartmentwiseall(p_data);
        new RetrofitRequest<>(call, new ResponseListener<DepartmentWiseListReponse>() {
            @Override
            public void onResponse(DepartmentWiseListReponse response, Headers headers) {
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


    public void getmovementwise(String p_data, SuccessResponse successResponse) {
        Call<BranchDetailsReponse> call = RetrofitClient.getAPIInterface().getmovementwise(p_data);
        new RetrofitRequest<>(call, new ResponseListener<BranchDetailsReponse>() {
            @Override
            public void onResponse(BranchDetailsReponse response, Headers headers) {
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

    public void getmovementwiseall(String p_data, SuccessResponse successResponse) {
        Call<BranchDetailsReponse> call = RetrofitClient.getAPIInterface().getmovementwiseall(p_data);
        new RetrofitRequest<>(call, new ResponseListener<BranchDetailsReponse>() {
            @Override
            public void onResponse(BranchDetailsReponse response, Headers headers) {
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

    public void Msme_Deviceid_Insertion(String p_data, SuccessResponse successResponse) {
        Call<DeviceRegistrationResponse> call = RetrofitClient.getAPIInterface().Msme_Deviceid_Insertion(p_data);
        new RetrofitRequest<>(call, new ResponseListener<DeviceRegistrationResponse>() {
            @Override
            public void onResponse(DeviceRegistrationResponse response, Headers headers) {
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

    public void deviceid_verify(String p_data, SuccessResponse successResponse) {
        Call<DeviceVerificationResponse> call = RetrofitClient.getAPIInterface().deviceid_verify(p_data);
        new RetrofitRequest<>(call, new ResponseListener<DeviceVerificationResponse>() {
            @Override
            public void onResponse(DeviceVerificationResponse response, Headers headers) {
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

    public void Msme_Deviceid_Updation(String p_data, SuccessResponse successResponse) {
        Call<DeviceUpdationResponse> call = RetrofitClient.getAPIInterface().Msme_Deviceid_Updation(p_data);
        new RetrofitRequest<>(call, new ResponseListener<DeviceUpdationResponse>() {
            @Override
            public void onResponse(DeviceUpdationResponse response, Headers headers) {
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

    public void Msme_Deviceid_delete(String p_data, SuccessResponse successResponse) {
        Call<DeviceDeletionResponse> call = RetrofitClient.getAPIInterface().Msme_Deviceid_delete(p_data);
        new RetrofitRequest<>(call, new ResponseListener<DeviceDeletionResponse>() {
            @Override
            public void onResponse(DeviceDeletionResponse response, Headers headers) {
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
