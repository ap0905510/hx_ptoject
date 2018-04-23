package com.yw.ywproject;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    MapFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initTranslateFragment();
    }

    public void show(View view) {
        Toast.makeText(this, "show", Toast.LENGTH_SHORT).show();
        showFragment();
    }

    public void hide(View view) {
        Toast.makeText(this, "hide", Toast.LENGTH_SHORT).show();
        hideFragment();
    }

    private void initTranslateFragment() {
        fragment = MapFragment.newInstance();
    }

    private void showFragment() {
        notNull();
        FragmentTransaction showTransaction = getSupportFragmentManager().beginTransaction();
        if (!fragment.isHidden()) {
            showTransaction.add(R.id.frag_container, fragment).commit();
        } else {
            showTransaction.show(fragment).commit();
        }
    }

    public void hideFragment() {
        notNull();
        FragmentTransaction hideTransaction = getSupportFragmentManager().beginTransaction();
        if (fragment.isAdded()) {
            hideTransaction.hide(fragment).commit();
        }
    }

    private void removeFragment() {
        notNull();
        FragmentTransaction removeTransaction = getSupportFragmentManager().beginTransaction();
        if (fragment.isAdded()) {
            removeTransaction.remove(fragment).commit();
        }
    }

    private void notNull() {
        if (fragment != null) {
            return;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        removeFragment();
    }
}
