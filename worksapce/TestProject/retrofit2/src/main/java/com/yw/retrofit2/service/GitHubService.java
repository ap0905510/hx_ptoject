package com.yw.retrofit2.service;

import com.yw.retrofit2.bean.Res;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;


public interface GitHubService {
	
	@FormUrlEncoded
	@POST("joke_text")
	Call<Res> getJoke(@Header("apikey") String key, @Field("page") String page);
	
}
