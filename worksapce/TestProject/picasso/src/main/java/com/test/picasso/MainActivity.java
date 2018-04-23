package com.test.picasso;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    //private String url = "http://jiangsu.china.com.cn/uploadfile/2016/0212/1455274218663759.jpg";
//private String url = "http://test.huxin.biz/file/download/user/pt/0/18664303806.jpg";

    String url = "http://sz.meituan.com/favicon.ico";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Uri uri = Uri.parse(url);
        Log.e("mm","getScheme = "+uri.getScheme());
        Log.e("mm", "path = " + uri.getPath());


        ImageView iv = (ImageView) findViewById(R.id.iv);
        Picasso.with(getApplicationContext()).setIndicatorsEnabled(true);
        Picasso.with(getApplicationContext()).setLoggingEnabled(true);
        Picasso.with(getApplicationContext()).load(url).fit().centerCrop().into(iv);
    }
}
