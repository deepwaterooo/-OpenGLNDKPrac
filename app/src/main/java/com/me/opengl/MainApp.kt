package com.me.opengl

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log

// 为什么要加一个application level context呢？后面用到ndk多线程的时候，可能会需要这样一个application level的context
@SuppressLint("StaticFieldLeak")
var CONTEXT: Context? = null

// Android中的app在AndroidManifest.xml中可以指定进程类
// 指定了MainApp类，MainApp类必然是要继承Application类的
// 一般在这个函数中可以做一些拥有进程生命周期数据结构的初始化工作，例如查询归属地的结构初始化，这样之后查询归属地直接调用接口即可，不用在每个调用的地方都要考虑初始化的问题
class MainApp: Application() {

    override fun onCreate() {
        super.onCreate()
        // AndroidManifest.xml中注册名字后，CONTEXT会变为非空，要不然application level onCreate()未必会执行
        CONTEXT = this
    }
}

// ContentProvider的安装比Application的onCreate回调还要早！！因此，分析到这里我们已经明白了前面提出的那个问题，
// 进程启动之后会在Applition类的onCreate 回调之前，在Application对象创建之后完成ContentProvider的安装
