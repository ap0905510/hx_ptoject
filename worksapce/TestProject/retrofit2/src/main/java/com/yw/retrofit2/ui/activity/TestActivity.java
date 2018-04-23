package com.yw.retrofit2.ui.activity;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.app.Activity;

import com.yw.retrofit2.bean.Res;
import com.yw.retrofit2.service.GitHubService;

public class TestActivity extends Activity {
	
	
//	private void getRequest(){
//		OkHttpClient mOkHttpClient = new OkHttpClient();
//		Request request = new Request.Builder().url("").build();
//		Call call = mOkHttpClient.newCall(request);
//		call.enqueue(new Callback() {
//			
//			@Override
//			public void onResponse(Response res) throws IOException {
//				String string = res.body().string();
//			}
//			
//			@Override
//			public void onFailure(Request arg0, IOException arg1) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
//	}
//	
//	private void postRequest(){
//		FormEncodingBuilder builder = new FormEncodingBuilder();
//		builder.add("page", "1");
//		Request request = new Request.Builder().url("http://apis.baidu.com/showapi_open_bus/showapi_joke/joke_text")
//				.addHeader("apikey", "ffac023cd51e5d0430d6ceaecf623c2e")
//				.post(builder.build()).build();
//
//		OkHttpClient mOkHttpClient = new OkHttpClient();
//		mOkHttpClient.newCall(request).enqueue(new Callback() {
//			
//			@Override
//			public void onResponse(Response arg0) throws IOException {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void onFailure(Request arg0, IOException arg1) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
//	}
	
	private void getRetrofit(){
		Retrofit retrofit = new Retrofit.Builder()
			.baseUrl("http://apis.baidu.com/showapi_open_bus/showapi_joke/")
			.addConverterFactory(GsonConverterFactory.create())
			.build();
//		GitHubService service = retrofit.create(GitHubService.class);
//		retrofit.Call<Res> repos = service.getJoke("ffac023cd51e5d0430d6ceaecf623c2e", "1");
//		repos.enqueue(new retrofit.Callback<Res>() {
//			
//			@Override
//			public void onResponse(retrofit.Response<Res> arg0, Retrofit arg1) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void onFailure(Throwable t) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
	}
	
	

	
}
