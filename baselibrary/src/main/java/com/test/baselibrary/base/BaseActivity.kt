package com.test.baselibrary.base

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * 抽象类
 */
abstract class BaseActivity : AppCompatActivity() {

    protected val act: Activity by lazy { this }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initData()
        initListeners()
    }

    abstract fun getLayoutId(): Int

    open fun initData() {}

    open fun initListeners() {}
}