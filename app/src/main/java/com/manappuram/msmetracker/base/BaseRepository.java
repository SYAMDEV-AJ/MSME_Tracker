package com.manappuram.msmetracker.base;

import androidx.lifecycle.MutableLiveData;

public class BaseRepository {
    public MutableLiveData<Event<BaseResponse>> errorsMutable;
    public MutableLiveData<Event> errorData;
    public MutableLiveData<Event<String>> failMessageMutable;


    @FunctionalInterface
    public interface SuccessResponse<T> {
        void onResponse(T t);
    }


    public BaseRepository() {
        errorsMutable = new MutableLiveData<>();
        failMessageMutable = new MutableLiveData<>();
        errorData = new MutableLiveData<>();
    }

    public MutableLiveData<Event<BaseResponse>> getErrorsMutable() {
        return errorsMutable;
    }

    public MutableLiveData<Event<String>> getFailMessageMutable() {
        return failMessageMutable;
    }
}
