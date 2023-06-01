package com.example.tva_projekt.retrofit;

import com.example.tva_projekt.dataObjects.ActivityFormObject;
import com.example.tva_projekt.dataObjects.AppUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitService {
    //tukaj se kličejo requesti na določen URL
    @POST("user/register")
    Call<AppUser> registerUser(@Body AppUser appUser);
    @POST("activity/insertActivityFrom")
    Call<ActivityFormObject> insertActivityForm(@Body ActivityFormObject activityFormObject);

    //@GET("predefinedActivity/getActivity/")
    //Call<List<GetPredefinedActivitiesResult>> getPredefinedActivities(@Path("type") String type);
    @GET("predefinedActivity/getActivity/{type}")
    Call<List<GetPredefinedActivitiesResult>> getPredefinedActivities(@Path("type") String type);

    @POST("user/login")
    Call<LoginDataObject> getUserId(@Body LoginDataObject loginDataObject);
}
