package com.squareup.picasso;

import android.content.Context;

import java.io.File;

/**
 * Created by Administrator on 2016/3/25.
 */
public class PicassoTools {

    public static void clearCache(Picasso p) {
        p.cache.clear();
    }

    public static void clearDiskCache(Context context){
        String path = context.getCacheDir()+"/"+Utils.PICASSO_CACHE;
        File file = new File(path);
        File[] fs = file.listFiles();
        for(File f :fs){
            f.delete();
        }
    }
}
