package com.me.opengl.drawer

import android.graphics.SurfaceTexture
import android.opengl.GLES20
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

class TriangleDrawer : IDrawer {

    // 这几个点之间有什么关系？三个顶点
    private val mVerCoos = floatArrayOf(-1f, -1f, 1f, -1f, 0f, 1f)
    private val mTexCoos = floatArrayOf(0f, 1f, 1f, 1f, 0.5f, 0f)

    // 纹理ID
    private var mTextureId: Int = -1
    // OpenGL着色器程序ID
    private var mProgram: Int = -1

    // 顶点坐标接收者
    private var mVertexPosHandler: Int = -1
    // 纹理坐标接收者
    private var mTexturePosHandler: Int = -1

    private lateinit var mVertexBuffer: FloatBuffer
    private lateinit var mTextureBuffer: FloatBuffer

    init {
        initPos()
    }
    //【步骤1: 初始化顶点坐标】
    private fun initPos() {
        val bb = ByteBuffer.allocateDirect(mVerCoos.size * 4)
        bb.order(ByteOrder.nativeOrder())
        // 将坐标数据转换为FloatBuffer，用以传入给OpenGL ES程序
        mVertexBuffer = bb.asFloatBuffer()
        mVertexBuffer.put(mVerCoos)
        mVertexBuffer.position(0)

        val cc = ByteBuffer.allocateDirect(mTexCoos.size * 4)
        cc.order(ByteOrder.nativeOrder())
        mTextureBuffer = cc.asFloatBuffer()
        mTextureBuffer.put(mTexCoos)
        mTextureBuffer.position(0)
    }

    override fun draw() {
        if (mTextureId != -1) {
            // 【步骤2: 创建、编译并启动OpenGL着色器】: ？？？ 不是应该写在construtor以便只执行一次的吗？好在这个例子只画一次
            createGLPrg()
            // 【步骤3: 开始渲染绘制】
            doDraw()
        }
    }
    // 着色程序包含 OpenGL 着色语言 (GLSL) 代码，必须先对其进行编译，然后才能在 OpenGL ES 环境中使用
    // 要绘制形状，您必须编译着色程序代码，将它们添加到 OpenGL ES 程序对象中，然后关联该程序。该操作需要在绘制对象的构造函数中完成，因此只需执行一次。
    private fun createGLPrg() {
        if (mProgram == -1) {
            // 加载 到 OpenGL ES 程序对象中: 加载
            val vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, getVertexShader())
            val fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, getFragmentShader())

            // 创建 OpenGL ES程序，注意：需要在OpenGL渲染线程中创建，否则无法渲染
            mProgram = GLES20.glCreateProgram()
            // 将顶点着色器 关联 到程序中
            GLES20.glAttachShader(mProgram, vertexShader)
            // 将片元着色器 关联 到程序中
            GLES20.glAttachShader(mProgram, fragmentShader)
            // 连接到着色器程序，关联该程序
            GLES20.glLinkProgram(mProgram)

            // get handle to vertex shader's vPosition member
            mVertexPosHandler = GLES20.glGetAttribLocation(mProgram, "aPosition")
            mTexturePosHandler = GLES20.glGetAttribLocation(mProgram, "aCoordinate")
        }
        // 使用OpenGL程序 Add program to OpenGL ES environment
        GLES20.glUseProgram(mProgram)
    }
    private fun doDraw() {
        // 启用顶点的句柄
        GLES20.glEnableVertexAttribArray(mVertexPosHandler) // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(mTexturePosHandler)
        // 设置着色器参数， 第二个参数表示一个顶点包含的数据数量，这里为xy，所以为2
        GLES20.glVertexAttribPointer(mVertexPosHandler, 2, GLES20.GL_FLOAT, false, 0, mVertexBuffer)
        GLES20.glVertexAttribPointer(mTexturePosHandler, 2, GLES20.GL_FLOAT, false, 0, mTextureBuffer)
        // 开始绘制
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 3)
    }
    private fun loadShader(type: Int, shaderCode: String): Int {
        // 根据type创建顶点着色器或者片元着色器
        val shader = GLES20.glCreateShader(type)
        // 将资源加入到着色器中，并编译
        GLES20.glShaderSource(shader, shaderCode)
        GLES20.glCompileShader(shader)

        return shader
    }
    // 顶点着色器
    private fun getVertexShader(): String {
        return "attribute vec4 aPosition;" +
        "void main() {" +
                      "  gl_Position = aPosition;" +
                      "}"
    }
    // 片段着色器
    private fun getFragmentShader(): String {
        // gl_FragColor跟上面的gl_Position一样是GLSL语言中的内建变量，不过它是Frament Shader中输出的颜色向量，在OpenGL中颜色向量用vec4(r,g,b,a)表示
        // 在Android中我们的rgba通常是0-255的整数，不过在这里是0.0f-1.0f的浮点数，上面的代码意思就是给每一个片段都涂上红色
        return "precision mediump float;" +
        "void main() {" +
                      "  gl_FragColor = vec4(1.0, 0.0, 0.0, 1.0);" +
                      "}"
    }

    override fun setTextureId(id: Int) {
        mTextureId = id
    }
    override fun release() {
        GLES20.glDisableVertexAttribArray(mVertexPosHandler)  // 顶点坐标 
        GLES20.glDisableVertexAttribArray(mTexturePosHandler) // 纹理坐标
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)         // ？
        GLES20.glDeleteTextures(1, intArrayOf(mTextureId), 0) // 删除纹理
        GLES20.glDeleteProgram(mProgram)                      // 删除着色程序？
    }
    override fun setVideoSize(videoW: Int, videoH: Int) { }
    override fun setWorldSize(worldW: Int, worldH: Int) { }
    override fun setAlpha(alpha: Float) { }
    override fun getSurfaceTexture(cb: (st: SurfaceTexture) -> Unit) { }
}