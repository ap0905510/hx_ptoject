//
// Created by Administrator on 2018/1/18.
//

#include <jni.h>
#include <string>
#include <android/log.h>
#include <android/bitmap.h>
#include <malloc.h>
#include "android/bitmap.h"
#include <jpeglib.h>

#define TAG "YW"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, TAG, __VA_ARGS__)

void write_JPEG_file(uint8_t *temp, int w, int h, jint q, const char *path);

extern "C"
JNIEXPORT void JNICALL Java_com_dongnao_bitmapcompress_MainActivity_nativeCompress
        (JNIEnv *env, jobject instance, jobject bitmap, jint q, jstring path_) {
    const char* path = env->GetStringUTFChars(path_, 0);

    // 1.获取Bitmap rgb 数据
    AndroidBitmapInfo info;

    // 2.获取Bitmap的信息，如宽高  info入参出参变量
    AndroidBitmap_getInfo(env, bitmap, &info);

    // 3.锁定bitmap的pixels内存地址
    uint8_t *pixels;
    AndroidBitmap_lockPixels(env, bitmap, (void **) &pixels);

    int w = info.width;
    int h = info.height;
    int color;

    // 4.申请内存 rgb
    uint8_t *data = (uint8_t *) malloc(sizeof(w * h * 4));

    uint8_t *temp = data;
    uint8_t r, g, b;

    for (int i = 0; i < h; ++i) {
        for (int j = 0; j < w; ++j) {
            color = *(int*) pixels;
            //argb
            r = (color >> 16) && 0xFF;
            g = (color >> 8) && 0xFF;
            b = (color) && 0xFF;

            *data = b;
            *(data+1) = g;
            *(data+2) = r;

            data += 3;
            pixels += 4;
        }
    }

    write_JPEG_file(temp, w, h, q, path);

    AndroidBitmap_unlockPixels(env, bitmap);
    free(data);
    env->ReleaseStringUTFChars(path_, path);
}

void write_JPEG_file(uint8_t *data, int w, int h, jint q, const char *path) {
    jpeg_compress_struct jcs;
    jpeg_error_mgr error;
    jcs.err = jpeg_std_error(&error);

    jpeg_create_compress(&jcs);

    FILE *file = fopen(path, "wb");
    jpeg_stdio_dest(&jcs, file);

    jcs.image_width = w;
    jcs.image_height = h;

    jcs.input_components = 3;
    jcs.in_color_space = JCS_RGB;

    jcs.optimize_coding = 1;
    jpeg_set_quality(&jcs, q, 1);

    jpeg_start_compress(&jcs, 1);

    // x写入
    int row_strike = w * 3;
    JSAMPROW  row[1];
    while (jcs.next_scanline < h) { //逐行扫
        uint8_t *pixels = data + jcs.next_scanline * row_strike;
        row[0] = pixels;
        jpeg_write_scanlines(&jcs, row, 1);
    }

    jpeg_finish_compress(&jcs);
    fclose(file);
    jpeg_destroy_compress(&jcs);
}
