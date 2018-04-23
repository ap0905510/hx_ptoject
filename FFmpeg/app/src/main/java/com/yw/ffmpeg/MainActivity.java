package com.yw.ffmpeg;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        videoView = findViewById(R.id.video);
    }

    public void play(View view) {
        String input = new File(Environment.getExternalStorageDirectory(),
                "input.mp4").getAbsolutePath();
        videoView.player(input);
    }

}
