package com.youmai.asynctaskloaderdemo;

import java.util.ArrayList;

public interface PickerManagerListener {
    void onItemSelected(int currentCount);
    void onSingleItemSelected(ArrayList<String> paths);
}