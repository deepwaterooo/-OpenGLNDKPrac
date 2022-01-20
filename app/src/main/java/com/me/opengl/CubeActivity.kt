package com.me.opengl

import android.opengl.GLSurfaceView
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class CubeActivity : AppCompatActivity() {
    private val TAG = "opengl CubeActivity"

    lateinit var cubeView : GLSurfaceView
    // lateinit var jniRender : RenderJni

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cubeView = CubeView(this)
        setContentView(cubeView)
    }
    
    override fun onPause() {
        super.onPause()
        cubeView.onPause()
    }
    override fun onResume() {
        super.onResume()
        cubeView.onResume()
    }
}