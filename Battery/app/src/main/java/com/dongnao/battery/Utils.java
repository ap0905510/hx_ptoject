package com.dongnao.battery;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by Administrator on 2018/1/15 0015.
 */

public class Utils {
    public static void safeColose(Closeable closeable){
        if (null != closeable){
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
