package com.example.tva_projekt.retrofit;

import com.example.tva_projekt.dataObjects.AppUser;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitService {
    //tukaj se kličejo requesti na določen URL
    @POST("user/register")
    Call<AppUser> registerUser(@Body AppUser appUser);
}
