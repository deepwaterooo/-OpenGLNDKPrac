package com.me.opengl

import android.opengl.GLSurfaceView
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class TriangleActivity : AppCompatActivity() {
    private val TAG = "opengl TriangleActivity"

    lateinit var triangleView : GLSurfaceView
    lateinit var jniRender : RenderJni

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        triangleView = TriangleView(this)
        setContentView(triangleView)
    }
    
    override fun onPause() {
        super.onPause()
        triangleView.onPause()
    }
    override fun onResume() {
        super.onResume()
        triangleView.onResume()
    }
}