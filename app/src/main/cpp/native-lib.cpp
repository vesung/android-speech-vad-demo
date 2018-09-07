#include <jni.h>
#include <string>
#include "speech_vad.h"

extern "C"{
#include "main.h"
#include "speech_vad.h"
}


extern "C"
JNIEXPORT jstring
JNICALL
Java_com_github_vesung_speechvaddemo_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {

    std::string ret = docutf();
    std::string hello = "语音文件切分完成\n" + ret;
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT void JNICALL
Java_com_github_vesung_speechvaddemo_MainActivity_vadOpen(JNIEnv *env, jobject instance) {

    vad_open();
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_github_vesung_speechvaddemo_MainActivity_vadProcessFrame(JNIEnv *env, jobject instance,
                                                                  jbyteArray frame_) {
    jbyte *frame = env->GetByteArrayElements(frame_, NULL);
    jsize len = env->GetArrayLength(frame_);
    int is_active = -1;
    if(len > 0){
        int bytesize = sizeof(jbyte) * len;
        int16_t *int16_frame = (int16_t *) malloc(bytesize);
        memcpy(int16_frame, frame, bytesize);
        is_active = vad_process_frame(int16_frame);
        free(int16_frame);
    }

    env->ReleaseByteArrayElements(frame_, frame, 0);
    return is_active;
}

extern "C"
JNIEXPORT void JNICALL
Java_com_github_vesung_speechvaddemo_MainActivity_vadClose(JNIEnv *env, jobject instance) {
    env->ExceptionOccurred();

    vad_close();

}