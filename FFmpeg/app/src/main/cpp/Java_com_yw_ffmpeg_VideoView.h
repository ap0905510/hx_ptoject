//
// Created by Administrator on 2018/1/22.
//

#include <jni.h>
#include <android/log.h>

#define TAG "YW"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, TAG, __VA_ARGS__)

#ifndef FFMPEG_JAVA_COM_YW_FFMPEG_VIDEOVIEW_H
#define FFMPEG_JAVA_COM_YW_FFMPEG_VIDEOVIEW_H

#endif //FFMPEG_JAVA_COM_YW_FFMPEG_VIDEOVIEW_H

extern "C"
JNIEXPORT void JNICALL
Java_com_yw_ffmpeg_VideoView_render(JNIEnv*, jobject, jstring , jobject);