#include <jni.h>
#include <string>
#include <android/bitmap.h>
#include <malloc.h>

#include <jpeglib.h>


void write_JPEG_file(uint8_t *data, int w, int h, jint q, const char *path) {
    // 1.声明jpeg压缩对象
    jpeg_compress_struct jcs;
    // 错误回调
    jpeg_error_mgr error;
    jcs.err = jpeg_std_error(&error);
    // 创建压缩对象
    jpeg_create_compress(&jcs);

    // 2.指定存储文件
    FILE *file = fopen(path, "wb");
    jpeg_stdio_dest(&jcs, file);

    // 3.设置压缩参数
    jcs.image_width = w;
    jcs.image_height = h;

    // bgr
    jcs.input_components = 3;
    jcs.in_color_space = JCS_RGB;

    // 开启哈夫曼 1=true 0=false
    jcs.optimize_coding = 1;
    jpeg_set_quality(&jcs, q, 1);

    // 开始压缩
    jpeg_start_compress(&jcs, 1);

    // 循环写入每一行
    int row_strike = w * 3;
    JSAMPROW row[1]; // char* row[1];
    while(jcs.next_scanline < h) {
        // 拿一行数据
        uint8_t *pixels = data + jcs.next_scanline * row_strike;
        row[0] = pixels;
        jpeg_write_scanlines(&jcs, row, 1);
    }

    // 完成压缩
    jpeg_finish_compress(&jcs);

    // 关闭文件
    fclose(file);
    // 释放资源
    jpeg_destroy_compress(&jcs);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_dongnao_bitmapcompress_MainActivity_nativeCompress(JNIEnv *env, jobject instance,
                                                            jobject bitmap, jint q, jstring path_) {
    const char *path = env->GetStringUTFChars(path_, 0);

    // 从bitmap获取rgb数据
    AndroidBitmapInfo info;
    // 获得bitmap信息 如：宽，高
    AndroidBitmap_getInfo(env, bitmap, &info);

    uint8_t *pixels;
    // 锁定bitmapd的pixels内存地址
    AndroidBitmap_lockPixels(env, bitmap, (void **) &pixels);
    // jpeg alpha=透明?
    // 去掉 透明度
    // 获取bitmap的宽高
    int w = info.width;
    int h = info.height;
    int color;

    // rgb
    // 申请内存
    uint8_t *data = (uint8_t *) malloc(sizeof(w * h * 3));
    uint8_t *temp = data;
    uint8_t  r, g, b;

    for (int i = 0; i < h; ++i) {
        for (int j = 0; j < w; ++j) {
            color = * (int*) pixels;
            // argb
            r = (color >> 16) && 0xFF;
            g = (color >> 8) && 0xFF;
            b = color && 0xFF;

            /**
             * 以前主流bgr
             * libjpeg  bgr
             */
            *data = b;
            *(data + 1) = g;
            *(data + 2) = r;

            data += 3;
            // 指针，跳过4个字节
            pixels += 4;
        }
    }
    write_JPEG_file(temp, w, h, q, path);
    AndroidBitmap_unlockPixels(env, bitmap);
    delete(data);
    env->ReleaseStringUTFChars(path_, path);
}

