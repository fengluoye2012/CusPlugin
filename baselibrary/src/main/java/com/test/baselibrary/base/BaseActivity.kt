package com.test.imageload.base

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * 抽象类
 */
abstract class BaseActivity : AppCompatActivity() {

    var act: Activity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        act = this
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initData()
        initListeners()
    }

    abstract fun getLayoutId(): Int

    open fun initData() {}

    open fun initListeners() {}
}