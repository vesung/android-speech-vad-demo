#include <jni.h>
#include <string>

extern "C"{
#include "main.h"
#include "speech_vad.h"
}

JNIEnv *jniEnv;

void cut_file_listener_func(char * filename, int size){
    __android_log_print(ANDROID_LOG_INFO, "speech_vad.c", "cutfile_event %s\n", filename);

    // 初始化listener
    jclass listener_cls = jniEnv->FindClass("com/github/vesung/speechvaddemo/VadListener");
    jmethodID initMethod = jniEnv->GetMethodID(listener_cls, "<init>", "()V");
    jobject listener = jniEnv->NewObject(listener_cls, initMethod);

//    jmethodID mid = jniEnv->GetMethodID(listener_cls, "cutfile", "()V");
    jmethodID mid = jniEnv->GetMethodID(listener_cls, "cutfile", "(Ljava/lang/String;I)V");
    jstring str = jniEnv->NewStringUTF(filename);
    jniEnv->CallVoidMethod(listener, mid, str, size);

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
    jniEnv = env;
    event_reg_cut_file_vent(cut_file_listener_func);

}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_github_vesung_speechvaddemo_MainActivity_vadProcessFrame(JNIEnv *env, jobject instance,
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
Java_com_github_vesung_speechvaddemo_MainActivity_vadClose(JNIEnv *env, jobject instance) {
    env->ExceptionOccurred();

    vad_close();

}