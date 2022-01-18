package com.me.opengl

import android.opengl.GLSurfaceView
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class RenderJni: GLSurfaceView.Renderer {
    // static {
    //     System.loadLibrary("gltest-lib");
    // }
    // private AssetManager mAssetMgr = null;
    // private final String mLogTag = "ndk-build";
    // public native void glesInit();
    // public native void glesRender();
    // public native void glesResize(int width, int height);
    // public native void readShaderFile(AssetManager assetMgr);

    override fun onDrawFrame(gl: GL10?) {
        Triangle.step()
    }
    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        Triangle.resize(width, height)
    }
    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        Triangle.init()
    }
}

// /**
//  * 当创建 GLSurfaceView时,系统调用这个方法.使用这个方法去执行只需要发生一次的动作,
//  * 例如设置OpenGL环境参数或者初始化OpenGL graphic 对象.
//  * @param gl
//  * @param config
//  */
// @Override
// public void onSurfaceCreated(GL10 gl, EGLConfig config) {
//     readShaderFile(mAssetMgr);
//     glesInit();
// }

// /**
//  *  当GLSurfaceView  几何学发生改变时系统调用这个方法.包括 GLSurfaceView  的大小发生改变或者横竖屏发生改变.
//  *  使用这个方法去响应GLSurfaceView 容器的改变
//  * @param gl
//  * @param width
//  * @param height
//  */
// @Override
// public void onSurfaceChanged(GL10 gl, int width, int height) {
//     glesResize(width, height);
// }

// /**
//  *    执行渲染工作：当系统每一次重画 GLSurfaceView 时调用.使用这个方法去作为主要的绘制和重新绘制graphic  对象的执行点.
//  * @param gl
//  */
// @Override
// public void onDrawFrame(GL10 gl) {
//     glesRender();
// }
// }