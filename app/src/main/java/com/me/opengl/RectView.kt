package com.me.opengl

import android.content.Context
import android.opengl.GLSurfaceView
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class RectView(context: Context) : GLSurfaceView(context){
    init {
        setEGLConfigChooser(8,8,8,0,16,0)
        setEGLContextClientVersion(3)
        setRenderer(RectRender())
    }
    override fun onPause() {
        RectLib.destroyR()
        super.onPause()
    }
    class RectRender:GLSurfaceView.Renderer{
        override fun onDrawFrame(gl: GL10?) {
            RectLib.stepR()
        }
        override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
            RectLib.resizeR(width, height)
        }
        override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
            RectLib.initR()
        }
    }
}