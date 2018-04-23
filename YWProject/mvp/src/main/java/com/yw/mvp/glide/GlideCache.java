package com.yw.mvp.glide;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.module.GlideModule;
import com.bumptech.glide.request.RequestOptions;
import com.yw.mvp.config.FileConfig;

/**
 * 作者：create by YW
 * 日期：2018.02.23 15:56
 * 描述：
 */
public class GlideCache implements GlideModule {

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        //要将 Glide v4 默认的 DecodeFormat 改回 DecodeFormat.PREFER_RGB_565
        //设置图片的显示格式ARGB_8888(指图片大小为32bit)
        //builder.setDecodeFormat(DecodeFormat.PREFER_RGB_565);
        builder.setDefaultRequestOptions(new RequestOptions().format(DecodeFormat.PREFER_RGB_565));

        //设置磁盘缓存目录（和创建的缓存目录相同）
        String downloadDirectoryPath = FileConfig.getGlideCachePath();

        //设置缓存的大小为150M
        int cacheSize = 150 * 1024 * 1024;
        builder.setDiskCache(new DiskLruCacheFactory(downloadDirectoryPath, cacheSize));
    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {

    }

}
