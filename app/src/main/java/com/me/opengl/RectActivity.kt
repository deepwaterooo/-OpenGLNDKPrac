package com.me.opengl;

import android.opengl.GLSurfaceView
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class RectActivity : AppCompatActivity() {
    lateinit var view: GLSurfaceView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = RectView(this)
        setContentView(view)
    }

    override fun onResume() {
        super.onResume()
        view.onResume()
    }

    override fun onPause() {
        super.onPause()
        view.onPause()
    }
}