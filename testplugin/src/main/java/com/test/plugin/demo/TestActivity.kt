package com.test.plugin.demo

import com.test.baselibrary.base.BaseActivity
import com.test.plugin.R

class TestActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_test
    }
}