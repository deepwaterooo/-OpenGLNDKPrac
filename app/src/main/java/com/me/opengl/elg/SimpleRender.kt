package com.me.opengl.elg

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import com.me.opengl.drawer.IDrawer
import javax.microedition.khronos.opengles.GL10
import javax.microedition.khronos.egl.EGLConfig

// 这只是从自己主熟悉的java语言用kotlin改写了一下最普通的OpenGL GLSurfaceView里画三角形有流程，熟悉语言与openGL画图基础而已
class SimpleRender: GLSurfaceView.Renderer {

    private val drawers = mutableListOf<IDrawer>()

    // 实现GLSurfaceView.Renderer的三个默认函数，这里这个的 参数可空设置
    override fun onSurfaceCreated(gl: GL10?, config: javax.microedition.khronos.egl.EGLConfig?) {
        // 设置清屏色
        GLES20.glClearColor(0f, 0f, 0f, 0f)
        // 开启混合模式，即半透明
        GLES20.glEnable(GLES20.GL_BLEND)
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA)
        // ???
        val textureIds = OpenGLTools.createTextureIds(drawers.size)
        for ((idx, drawer) in drawers.withIndex()) {
            drawer.setTextureId(textureIds[idx])
        }
    }
    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
        // GLSurfaceView中有的东西，一个一个地画出来
        for (drawer in drawers) {
            drawer.setWorldSize(width, height)
        }
    }
    override fun onDrawFrame(gl: GL10?) {
        // 清屏设置背景，与画 前景
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT or GLES20.GL_DEPTH_BUFFER_BIT)
        drawers.forEach {
            it.draw()
        }
    }
    fun addDrawer(drawer: IDrawer) {
        drawers.add(drawer)
    }
}