#include <jni.h>
#include <string>
#include <dlfcn.h>
#include <android/log.h>

#define LOG_TAG "MinelauncherCore"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

extern "C" JNIEXPORT jboolean JNICALL
Java_com_doralice_minelauncher2_CoreBridge_loadNativeLibrary(JNIEnv* env, jobject thiz, jstring library_path) {
    const char* path = env->GetStringUTFChars(library_path, nullptr);
    
    // dlopen faz a mágica de carregar o motor do Bedrock (libminecraftpe.so) na memória
    void* handle = dlopen(path, RTLD_NOW);
    
    if (!handle) {
        LOGE("Erro crítico ao carregar o motor do Bedrock: %s", dlerror());
        env->ReleaseStringUTFChars(library_path, path);
        return JNI_FALSE;
    }

    LOGI("Motor do Bedrock carregada com sucesso pelo C++: %s", path);
    env->ReleaseStringUTFChars(library_path, path);
    return JNI_TRUE;
}
