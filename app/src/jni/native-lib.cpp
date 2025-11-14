#include <jni.h>
#include <jni.h>
#include <string>
#include "edge_processor.h"
#include <android/log.h>
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,"native-lib",__VA_ARGS__)

extern "C" JNIEXPORT jbyteArray JNICALL
Java_com_yourorg_edgeviewer_NativeBridge_processFrame(
    JNIEnv* env,
    jclass clazz,
    jbyteArray inputBytes,
    jint width,
    jint height,
    jboolean applyEdges) {

    jsize len = env->GetArrayLength(inputBytes);
    jbyte* bytes = env->GetByteArrayElements(inputBytes, NULL);

    std::vector<unsigned char> inbuf((unsigned char*)bytes, (unsigned char*)bytes + len);
    std::vector<unsigned char> outbuf;
    process_rgba_frame(inbuf, outbuf, width, height, applyEdges);

    jbyteArray outArray = env->NewByteArray(outbuf.size());
    env->SetByteArrayRegion(outArray, 0, (jsize)outbuf.size(), (jbyte*)outbuf.data());

    env->ReleaseByteArrayElements(inputBytes, bytes, JNI_ABORT);
    return outArray;
}
