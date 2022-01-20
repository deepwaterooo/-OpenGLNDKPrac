package com.me.opengl;

public class Cube {
    static {
        System.loadLibrary("opengl");
    }
    // JNIEXPORT jboolean JNICALL Java_com_github_cccxm_gles_model_CubeLib_init(JNIEnv *env, jclass type);
    // JNIEXPORT void JNICALL Java_com_github_cccxm_gles_model_CubeLib_resize(JNIEnv *env, jclass type,
    // JNIEXPORT void JNICALL Java_com_github_cccxm_gles_model_CubeLib_render(JNIEnv *env, jclass type);
    // JNIEXPORT void JNICALL Java_com_github_cccxm_gles_model_CubeLib_destroy(JNIEnv *env, jclass type);

    // 初始化本地GLES
    public static native boolean initC();
    // 为本地GLES设置宽和高
    public static native void resizeC(int width, int height);
    // 用来绘制图形
    public static native void renderC();
    public static native void destroyC();
}