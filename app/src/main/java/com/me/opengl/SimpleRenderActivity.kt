package com.me.opengl

import android.graphics.BitmapFactory
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.me.opengl.databinding.ActivitySimpleRenderBinding
import com.me.opengl.drawer.BitmapDrawer
import com.me.opengl.drawer.IDrawer
import com.me.opengl.drawer.TriangleDrawer
import com.me.opengl.elg.SimpleRender

class SimpleRenderActivity : AppCompatActivity() {
    private val TAG = "opengl SimpleRenderActivity"
    private lateinit var binding: ActivitySimpleRenderBinding

    private lateinit var drawer: IDrawer

    override fun onCreate(savedInstanceBundle: Bundle?) {
        super.onCreate(savedInstanceBundle)
        binding = ActivitySimpleRenderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        drawer = if (intent.getIntExtra("type", 0) == 0) {
            TriangleDrawer()
        } else { 
            BitmapDrawer(BitmapFactory.decodeResource(CONTEXT!!.resources, R.drawable.cover))
        }
        initRender(drawer)
    }
    private fun initRender(drawer: IDrawer) {
        binding.glSurface.setEGLContextClientVersion(2)
        val render = SimpleRender()
        render.addDrawer(drawer)
        binding.glSurface.setRenderer(render)
    }
    override fun onDestroy() {
        drawer.release()
        super.onDestroy()
    }
}