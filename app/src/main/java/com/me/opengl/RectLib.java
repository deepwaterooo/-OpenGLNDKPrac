package com.me.opengl;

public class RectLib {
    static {
        System.loadLibrary("opengl"); // 这里是不是应该合并为一个呢？
    }
    public static native boolean initR();
    public static native void resizeR(int width,int height);
    public static native void stepR();
    public static native void destroyR();
}
