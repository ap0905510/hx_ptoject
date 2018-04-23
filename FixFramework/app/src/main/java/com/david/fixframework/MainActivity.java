package com.david.fixframework;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    TextView result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result = (TextView) findViewById(R.id.result);
        DexManager.getInstance().setContext(this);
    }


    public void jisuan(View view) {
        /**
         *
         */
        Caclutor caclutor=new Caclutor();

        Caclutor caclutor1=new Caclutor();
        Caclutor caclutor3=new Caclutor();
        caclutor.caculator();
        caclutor1.caculator();
        caclutor3.caculator();
        result.setText(" 结果  "+caclutor3.caculator());
        Log.i("tuch", "jisuan: "+caclutor1.caculator());
        ;
    }

    public void fix(View view) {
        Log.i("tuch", " 路劲  ：  " + Environment.getExternalStorageDirectory());
        DexManager.getInstance().loadFile(new File(Environment.getExternalStorageDirectory(),"out1.dex"));
    }
}
