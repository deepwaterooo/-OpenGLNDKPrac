package com.me.opengl;

public class Triangle {
    static {
        System.loadLibrary("opengl");
    }
    // 初始化本地GLES
    public static native boolean init();
    // 为本地GLES设置宽和高
    public static native void resize(int width, int height);
    // 用来绘制图形
    public static native void step();
}