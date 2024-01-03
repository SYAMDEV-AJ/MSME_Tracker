package com.manappuram.msmetracker.network.retrofit;


import androidx.annotation.NonNull;


import com.manappuram.msmetracker.base.BaseResponse;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.UnknownHostException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

/**
 * Class handle network calls
 *
 * @param <T>
 */
public final class RetrofitRequest<T> {

    private final ResponseListener responseListener;
    private Call<T> call;

    public RetrofitRequest(Call<T> call, ResponseListener<T> responseListener) {
        this.call = call;
        this.responseListener = responseListener;
    }

    public void enqueue() {
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
                try {
                    if (response.isSuccessful() ) {
                        // if (response.isSuccessful()) {
//                        updateSessionRequest((BaseResponse) response.body());
                        responseListener.onResponse(response.body(), response.headers());
                    } else if (response.code() == 502 || response.code() == 500 || ((BaseResponse) response.body()).getStatus() == null || ((BaseResponse) response.body()).getResult() == null) {
                        BaseResponse res = new BaseResponse("400", "Something Went Wrong!");
                        responseListener.onError(400, res);
                    } else if (response.code() == 666 || response.code() == 999 || response.code() == 555 || response.code() == 444) {
                        BaseResponse res = new BaseResponse("401", "Invalid Session");
                        responseListener.onError(401, res);
                    } else {
                        responseListener.onError(400, (BaseResponse) response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
                if (call.isCanceled())// don't process if request is cancelled.
                    return;
                if (t instanceof UnknownHostException)
                    responseListener.onFailure(t);//network error
                else {
                    BaseResponse response = new BaseResponse("400", "Something went wrong");
                    responseListener.onError(400, response);
                }
            }
        });
    }

//    private void updateSessionRequest(BaseResponse response) {
//        if (response != null && response.getRequestId() != null)
//            UserSession.getInstance().setRequestId(response.getRequestId());
//    }

    public void cancel() {
        call.cancel();
    }

    public void retry() {
        call = call.clone();
        enqueue();
    }

    public void execute() throws IOException {
        call.execute();
    }

    public boolean isValidResponse(Response<T> response) {
        if (((BaseResponse) response.body()).getStatus() != null &&
                (((BaseResponse) response.body()).getStatus().equals("111"))) {
            return true;
        } else {
            return false;
        }
    }

    private static BaseResponse parseError(Response<?> response) {
        Converter<ResponseBody, BaseResponse> converter = RetrofitClient.retrofit()
                .responseBodyConverter(BaseResponse.class, new Annotation[0]);
        BaseResponse error = new BaseResponse();
        try {
            error = converter.convert(response.errorBody());
        } catch (Exception e) {
            e.printStackTrace();
            error.setResult("Invalid Request");
            return error;
        }
        return error;
    }

}
