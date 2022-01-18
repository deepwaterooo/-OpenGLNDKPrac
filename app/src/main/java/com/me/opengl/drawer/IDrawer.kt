package com.me.opengl.drawer

import android.graphics.SurfaceTexture

interface IDrawer {
    fun draw()
    
    fun setVideoSize(videoW: Int, videoH: Int)
    fun setWorldSize(worldW: Int, worldH: Int)
    fun setAlpha(alpha: Float)
    fun setTextureId(id: Int)

    fun getSurfaceTexture(cb: (st: SurfaceTexture) -> Unit) { }
    fun release() 
}