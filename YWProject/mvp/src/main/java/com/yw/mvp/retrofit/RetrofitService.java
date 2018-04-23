package com.yw.mvp.retrofit;

import android.support.annotation.StringRes;

import com.yw.mvp.model.LoginResponse;
import com.yw.mvp.model.UserInfo;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.OPTIONS;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * 作者：create by YW
 * 日期：2018.01.04 14:55
 * 描述：相对于单独使用Retrofit，该处返回的是Observable对象
 */
public interface RetrofitService {

    //登录
    @FormUrlEncoded
    @POST("user/login")
    Observable<LoginResponse> login(@Body RequestBody body);

    @POST("jhuxin-openapi/qy/userinfo/{phone}")
    Observable<UserInfo> userInfo(@Path("phone") String phone, @QueryMap Map<String, String> map);

    @GET("jhuxin-openapi/qy/userinfo/{phone}")
    Observable<UserInfo> userInfo2(@Path("phone") String phone, @QueryMap Map<String, String> map);

    @FormUrlEncoded
    @POST("jhuxin-openapi/qy/userinfo/{phone}")
    Observable<UserInfo> userBody(@Path("phone") String phone, @Body RequestBody body);

    @FormUrlEncoded
    @POST("/{phone}")
    public Observable<UserInfo> userField(@Path("phone") String phone, @Field("") String json);

    @FormUrlEncoded
    @POST("/13250013344")
    public Observable<UserInfo> userField(@FieldMap Map<String, String> map);

    @POST("jhuxin-openapi/qy/userinfo/13250013344")
    Observable<UserInfo> userInfoBody(@Body RequestBody map);
}
