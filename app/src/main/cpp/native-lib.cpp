#include <jni.h>
#include <string>

extern "C"{
#include "main.h"
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
