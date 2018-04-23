package com.yw.mvp.presenter;

import android.util.Log;

import com.yw.mvp.base.BaseActivity;
import com.yw.mvp.base.BasePresenter;
import com.yw.mvp.retrofit.RetrofitFactory;
import com.yw.mvp.utils.MapToJson;
import com.yw.mvp.iview.IMainAtView;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;

import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 作者：create by YW
 * 日期：2018.01.03 15:43
 * 描述：P -> 在BasePresenter绑定View
 */
public class MainAtPresenter extends BasePresenter<IMainAtView> {

    public MainAtPresenter(BaseActivity context) {
        super(context);
    }

    @Override
    public void onResume() {
        
    }

    public void userInfo(String phone) {
        Map<String, String> map = new HashMap();
        map.put("v", "5");
//        RetrofitFactory.newInstance().getRetrofitService()
//                .userInfo(phone, map)
//                .subscribeOn(Schedulers.io()) //订阅在io
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(userInfo1 -> {
//                    Log.e("YW", userInfo1.toString());
//                    getView().getText().setText(userInfo1.toString());
//                }, this::loginError);

//        String b = MapToJson.toJson(map);
//        RequestBody body = RequestBody.create(MediaType.parse("application/json"), b);
//        RetrofitFactory.newInstance()
//                .getRetrofitService()
//                .userInfo3(phone, body)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(userInfo -> {
//                    Log.e("YW", userInfo.toString());
//                    getView().getText().setText(userInfo.toString());
//                }, this::loginError);


//        String b = MapToJson.toJson(map);
//        RequestBody body = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), b);
//        RetrofitFactory.newInstance()
//                .getRetrofitService()
//                .userField(map)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(userInfo -> {
//                    Log.e("YW", userInfo.toString());
//                    getView().getText().setText(userInfo.toString());
//                }, this::loginError);


        RequestBody userInfoBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), MapToJson.toJson(map));
        RetrofitFactory.newInstance()
                .getRetrofitService()
                .userInfoBody(userInfoBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userInfo -> {
                    Log.e("YW", userInfo.toString());
                    getView().getText().setText(userInfo.toString());
                }, this::loginError);

    }

    private void loginError(Throwable throwable) {
        Log.e("YW", throwable.getLocalizedMessage());
    }
}
