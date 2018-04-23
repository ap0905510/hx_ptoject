#include <jni.h>
#include <string>
#include <unistd.h>
#include "Java_com_yw_ffmpeg_VideoView.h"

extern "C" {
#include <libavformat/avformat.h>
#include <libswscale/swscale.h>
#include <libavcodec/avcodec.h>
#include <android/native_window_jni.h>

JNIEXPORT void JNICALL
Java_com_yw_ffmpeg_VideoView_render(JNIEnv *env, jobject instance, jstring input_,
                                    jobject surface) {
    const char *input = env->GetStringUTFChars(input_, 0);

    LOGI("Hello FFmpeg begin...");

    av_register_all();

    AVFormatContext *pFormatCtx = avformat_alloc_context();
    //第四个参数是 可以传一个 字典   是一个入参出参对象
    if (avformat_open_input(&pFormatCtx, input, NULL, NULL) != 0) {
        LOGE("%s", "打开输入视频文件失败");
        return;
    }
    //3.获取视频信息
    if (avformat_find_stream_info(pFormatCtx, NULL) < 0) {
        LOGE("%s", "获取视频信息失败");
        return;
    }


    int vidio_stream_idx = -1;
    int i = 0;
    for (int i = 0; i < pFormatCtx->nb_streams; ++i) {
        if (pFormatCtx->streams[i]->codec->codec_type == AVMEDIA_TYPE_VIDEO) {
            LOGE("  找到视频id %d", pFormatCtx->streams[i]->codec->codec_type);
            vidio_stream_idx = i;
            break;
        }
    }

//    获取视频编解码器
    AVCodecContext *pCodecCtx = pFormatCtx->streams[vidio_stream_idx]->codec;
    LOGE("获取视频编码器上下文 %p  ", pCodecCtx);
//    加密的用不了
    AVCodec *pCodex = avcodec_find_decoder(pCodecCtx->codec_id);
    LOGE("获取视频编码 %p", pCodex);
//版本升级了
    if (avcodec_open2(pCodecCtx, pCodex, NULL) < 0) {


    }
    AVPacket *packet = (AVPacket *) av_malloc(sizeof(AVPacket));
//    av_init_packet(packet);
//    像素数据
    AVFrame *frame;
    frame = av_frame_alloc();
//    RGB
    AVFrame *rgb_frame = av_frame_alloc();
//    给缓冲区分配内存
    //只有指定了AVFrame的像素格式、画面大小才能真正分配内存
    //缓冲区分配内存
    uint8_t *out_buffer = (uint8_t *) av_malloc(
            avpicture_get_size(AV_PIX_FMT_RGBA, pCodecCtx->width, pCodecCtx->height));
    LOGE("宽  %d,  高  %d  ", pCodecCtx->width, pCodecCtx->height);
//设置yuvFrame的缓冲区，像素格式
    int re = avpicture_fill((AVPicture *) rgb_frame, out_buffer, AV_PIX_FMT_RGBA, pCodecCtx->width,
                            pCodecCtx->height);
    LOGE("申请内存%d   ", re);

//    输出需要改变
    int length = 0;
    int got_frame;
//    输出文件
    int frameCount = 0;
    SwsContext *swsContext = sws_getContext(pCodecCtx->width, pCodecCtx->height, pCodecCtx->pix_fmt,
                                            pCodecCtx->width, pCodecCtx->height, AV_PIX_FMT_RGBA,
                                            SWS_BICUBIC, NULL, NULL, NULL
    );
    ANativeWindow *nativeWindow = ANativeWindow_fromSurface(env, surface);
//    视频缓冲区
    ANativeWindow_Buffer outBuffer;
//    ANativeWindow
    while (av_read_frame(pFormatCtx, packet) >= 0) {
//        AvFrame
        if (packet->stream_index == vidio_stream_idx) {
            length = avcodec_decode_video2(pCodecCtx, frame, &got_frame, packet);
            LOGE(" 获得长度   %d ", length);

//非零   正在解码
            if (got_frame) {
//            绘制之前   配置一些信息  比如宽高   格式
                ANativeWindow_setBuffersGeometry(nativeWindow, pCodecCtx->width, pCodecCtx->height,
                                                 WINDOW_FORMAT_RGBA_8888);
//            绘制
                ANativeWindow_lock(nativeWindow, &outBuffer, NULL);
//     h 264   ----yuv          RGBA
                LOGI("解码%d帧", frameCount++);
                //转为指定的YUV420P
                sws_scale(swsContext, (const uint8_t *const *) frame->data, frame->linesize, 0,
                          pCodecCtx->height, rgb_frame->data,
                          rgb_frame->linesize);
//rgb_frame是有画面数据
                uint8_t *dst = (uint8_t *) outBuffer.bits;
                //拿到一行有多少个字节 RGBA
                int destStride = outBuffer.stride * 4;
                //像素数据的首地址
                uint8_t *src = (uint8_t *) rgb_frame->data[0];
//            实际内存一行数量
                int srcStride = rgb_frame->linesize[0];
                int i = 0;
                for (int i = 0; i < pCodecCtx->height; ++i) {
//                memcpy(void *dest, const void *src, size_t n)
                    memcpy(dst + i * destStride, src + i * srcStride, srcStride);
                }

                ANativeWindow_unlockAndPost(nativeWindow);
                usleep(1000 * 16);
            }
        }
        av_free_packet(packet);
    }
    ANativeWindow_release(nativeWindow);
    av_frame_free(&frame);
    avcodec_close(pCodecCtx);
    avformat_free_context(pFormatCtx);

    env->ReleaseStringUTFChars(input_, input);
}
}