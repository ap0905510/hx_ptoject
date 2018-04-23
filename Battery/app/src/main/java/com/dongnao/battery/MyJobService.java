package com.dongnao.battery;

import android.app.job.JobParameters;
import android.app.job.JobService;

/**
 * Created by Administrator on 2018/1/15 0015.
 */

public class MyJobService extends JobService {
    @Override
    public boolean onStartJob(JobParameters params) {
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
