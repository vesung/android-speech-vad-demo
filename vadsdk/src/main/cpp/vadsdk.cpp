#include <jni.h>
#include <string>

extern "C"{
#include "main.h"
#include "speech_vad.h"
}


extern "C"
JNIEXPORT void JNICALL
Java_com_github_vesung_vadsdk_asr_AsrLiveEngine_vadOpen(JNIEnv *env, jobject instance) {

    vad_open();

}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_github_vesung_vadsdk_asr_AsrLiveEngine_vadProcessFrame(JNIEnv *env, jobject instance,
                                                                  jbyteArray frame_) {
    jbyte *frame = env->GetByteArrayElements(frame_, NULL);
    jsize len = env->GetArrayLength(frame_);
    char* is_active = "";
    if(len > 0){
        int bytesize = sizeof(jbyte) * len;
        int16_t *int16_frame = (int16_t *) malloc(bytesize);
        memcpy(int16_frame, frame, bytesize);
        is_active = vad_process_frame(int16_frame);
        free(int16_frame);
    }

    env->ReleaseByteArrayElements(frame_, frame, 0);

    return env->NewStringUTF(is_active);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_github_vesung_vadsdk_asr_AsrLiveEngine_vadClose(JNIEnv *env, jobject instance) {
    env->ExceptionOccurred();

    vad_close();

}