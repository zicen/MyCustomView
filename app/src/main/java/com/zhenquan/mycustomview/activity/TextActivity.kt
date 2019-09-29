package com.zhenquan.mycustomview.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.blankj.utilcode.util.ToastUtils
import com.zhenquan.mycustomview.R
import com.zhenquan.mycustomview.utils.DisplayUtils
import kotlinx.android.synthetic.main.activity_text.*

class TextActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text)
        tbv.setOnClickListener { ToastUtils.showShort("HHHHHHH") }
    }


}
