package com.me.opengl

import android.content.Context
import android.opengl.GLSurfaceView
import com.me.opengl.Triangle.step
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class CubeView(context: Context): GLSurfaceView(context) {
    
    init {
        setEGLConfigChooser(8,8,8,0,16,0)
        setEGLContextClientVersion(3)
        setRenderer(CubeRender())
    }
    override fun onPause() {
        Cube.destroyC()
        super.onPause()
    }
    class CubeRender: GLSurfaceView.Renderer {
        override fun onDrawFrame(gl: GL10?) {
            Cube.renderC()
        }
        override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
            Cube.resizeC(width, height)
        }
        override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
            Cube.initC()
        }
    }
}