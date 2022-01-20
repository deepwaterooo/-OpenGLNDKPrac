package com.me.opengl

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import com.me.opengl.databinding.ActivityMainBinding
import com.me.opengl.drawer.BitmapDrawer
import com.me.opengl.drawer.IDrawer
import com.me.opengl.drawer.TriangleDrawer
import com.me.opengl.elg.SimpleRender

class MainActivity : AppCompatActivity() {
    private val TAG = "opengl MainActivity"
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate()") 
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    
    fun clickSimpleTriangle(view: View) {
        val intent = Intent(this, SimpleRenderActivity::class.java)
        intent.putExtra("type", 0)
        startActivity(intent)
    }
    fun clickSimpleTexture(view: View) {
        val intent = Intent(this, SimpleRenderActivity::class.java)
        intent.putExtra("type", 1)
        startActivity(intent)
    }
    fun clickNDKDrawTriangle(view: View) {
        val intent = Intent(this, TriangleActivity::class.java)
        startActivity(intent)
    }
    fun clickDrawRect(view: View) {
        val intent = Intent(this, RectActivity::class.java)
        startActivity(intent)
    }
    fun clickDrawCube(view: View) {
        val intent = Intent(this, CubeActivity::class.java)
        startActivity(intent)
    }

    // 如果接下来需要便用相机，需要申请权限
    // external fun stringFromJNI(): String

    companion object {
        // Used to load the 'opengl' library on application startup.
        init {
            System.loadLibrary("opengl")
        }
    }
}