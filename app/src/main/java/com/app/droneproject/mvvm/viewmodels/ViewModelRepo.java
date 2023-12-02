package com.app.droneproject.mvvm.viewmodels;


import android.app.Activity;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.app.droneproject.mvvm.APIClient;
import com.app.droneproject.mvvm.interfaces.NetworkCalls;
import com.app.droneproject.mvvm.mapping_utils.GenericCall;
import com.app.droneproject.mvvm.mapping_utils.GenericResponse;
import com.app.droneproject.mvvm.pojos.request.PostLoginPojo;
import com.app.droneproject.mvvm.pojos.request.PostRegPojo;
import com.app.droneproject.mvvm.pojos.request.SocialLoginPojo;
import com.app.droneproject.mvvm.pojos.response.Capture;
import com.app.droneproject.mvvm.pojos.response.Disease;
import com.app.droneproject.mvvm.pojos.response.RegResponse;
import com.app.droneproject.mvvm.pojos.response.UserProfile;
import com.app.droneproject.mvvm.pojos.response.plant.Plant;
import com.app.droneproject.utils.SharedPrefUtils;
import com.app.droneproject.utils.Utils;

import java.io.File;
import java.util.List;

public class ViewModelRepo extends AndroidViewModel {

    NetworkCalls networkCalls;

    public ViewModelRepo(@NonNull Application application) {
        super(application);
        networkCalls = APIClient.getRetrofit().create(NetworkCalls.class);
    }

    public LiveData<GenericResponse<RegResponse>> postGoogleCode(SocialLoginPojo socialLoginPojo) {
        return new GenericCall<>(networkCalls.postGoogleLogin(socialLoginPojo)).getMutableLiveData();
    }

    public LiveData<GenericResponse<RegResponse>> postLogin(PostLoginPojo postLoginPojo) {
        return new GenericCall<>(networkCalls.postLogin(postLoginPojo)).getMutableLiveData();
    }

    public LiveData<GenericResponse<RegResponse>> postAuth(PostRegPojo postRegPojo) {
        return new GenericCall<>(networkCalls.postAuth(postRegPojo))
                .getMutableLiveData();
    }

    public LiveData<GenericResponse<List<Plant>>> getPlants() {
        return new GenericCall<>(networkCalls.getPlants()).getMutableLiveData();
    }

    public LiveData<GenericResponse<List<Disease>>> getDisease() {
        return new GenericCall<>(networkCalls.getDiseases()).getMutableLiveData();
    }

    public LiveData<GenericResponse<UserProfile>> getProfile(Activity context) {
        String token = SharedPrefUtils.getToken(context);
        return new GenericCall<>(networkCalls.getProfile(token)).getMutableLiveData();
    }

    public LiveData<GenericResponse<List<Capture>>> getCaptures(Activity context) {
        String token = SharedPrefUtils.getToken(context);
        return new GenericCall<>(networkCalls.getCaptures(token)).getMutableLiveData();
    }

    public LiveData<GenericResponse<UserProfile>> putProfile(Activity context, File file,
                                                             String first_name,
                                                             String last_name,
                                                             String phone) {
        String token = SharedPrefUtils.getToken(context);
        return new GenericCall<>(networkCalls.putProfile(token,
                Utils.fileRequest(file, first_name, last_name, phone))).getMutableLiveData();
    }

}