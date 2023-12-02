package com.app.droneproject.mvvm.interfaces;

import com.app.droneproject.mvvm.pojos.request.PostLoginPojo;
import com.app.droneproject.mvvm.pojos.request.PostRegPojo;
import com.app.droneproject.mvvm.pojos.request.SocialLoginPojo;
import com.app.droneproject.mvvm.pojos.response.Capture;
import com.app.droneproject.mvvm.pojos.response.Disease;
import com.app.droneproject.mvvm.pojos.response.RegResponse;
import com.app.droneproject.mvvm.pojos.response.UserProfile;
import com.app.droneproject.mvvm.pojos.response.plant.Plant;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface NetworkCalls {

    @POST(Urls.URL_GOOGLE_LOGIN)
    Call<RegResponse> postGoogleLogin(@Body SocialLoginPojo postRegPojo);

    @POST(Urls.URL_LOGIN)
    Call<RegResponse> postLogin(@Body PostLoginPojo postRegPojo);

    @POST(Urls.URL_REG)
    Call<RegResponse> postAuth(@Body PostRegPojo postRegPojo);

    @GET(Urls.URL_GET_PLANTS)
    Call<List<Plant>> getPlants();

    @GET(Urls.URL_GET_DISEASES)
    Call<List<Disease>> getDiseases();

    @GET(Urls.URL_GET_USER)
    Call<UserProfile> getProfile(@Header("Authorization") String token);

    @PUT(Urls.URL_GET_USER)
    Call<UserProfile> putProfile(
            @Header("Authorization") String token,
            @Body RequestBody file);

    @GET(Urls.URL_GET_CAPTURES)
    Call<List<Capture>> getCaptures(@Header("Authorization") String token);

}
