package com.test.customplugin

import android.content.Context
import android.content.res.AssetManager
import android.os.Environment
import android.util.Log
import android.view.View
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.ToastUtils
import com.didi.virtualapk.PluginManager
import com.test.imageload.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.InputStream

class MainActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initListeners() {
        super.initListeners()

        //匿名类的使用
        tv_load_plugin.setOnClickListener {
            View.OnClickListener {

                //接口回调
                PermissionUtils.permission(PermissionConstants.STORAGE)
                    .callback(object : PermissionUtils.SimpleCallback {
                        override fun onGranted() {
                            loadPlugin(it.context)
                        }

                        override fun onDenied() {
                            loadPlugin(it.context)
                        }
                    })
            }
        }
    }

    private fun loadPlugin(base: Context) {
        if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
            ToastUtils.showShort("sdcard was NOT MOUNTED!")
            return
        }

        val pluginManager: PluginManager = PluginManager.getInstance(base)
        val apk = File(Environment.getExternalStorageState(), "Test.apk")
        if (apk.exists()) {
            try {
                pluginManager.loadPlugin(apk)
                LogUtils.i("Loaded plugin from apk: $apk")
            } catch (e: Exception) {
                LogUtils.e(Log.getStackTraceString(e))
            }
        } else {
            try {
                val file = File(base.filesDir, "Test.apk")
                val inputStream: InputStream = base.assets.open("Test.apk", AssetManager.ACCESS_STREAMING)
                val outputStream = java.io.FileOutputStream(file)
                val buf = ByteArray(1024)

                var len = inputStream.read(buf)
                //while 循环中条件语句中不能存在赋值语句
                while (len > 0) {
                    outputStream.write(buf, 0, len)
                    len = inputStream.read(buf)
                }

                outputStream.close()
                inputStream.close()
                pluginManager.loadPlugin(file)
                LogUtils.i("Loaded plugin from assets: $file")
            } catch (e: Exception) {
                LogUtils.e(Log.getStackTraceString(e))
            }
        }
    }
}
