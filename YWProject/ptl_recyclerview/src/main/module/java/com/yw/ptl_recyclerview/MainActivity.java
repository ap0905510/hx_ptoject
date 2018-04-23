package com.yw.ptl_recyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.yw.ptl_recyclerview.custom.CustomManagerActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_linear_manager:
                LinearManagerActivity.actionStart(this);
                break;
            case R.id.btn_grid_manager:
                GridManagerActivity.actionStart(this);
                break;
            case R.id.btn_staggred_grid_manager:
                StaggredGridManagerActivity.actionStart(this);
                break;
            case R.id.btn_auto_load:
                AutoLoadActivity.actionStart(this);
                break;
            case R.id.btn_custom_load:
                CustomManagerActivity.actionStart(this);
                break;
        }
    }




}
