package com.zhenquan.mycustomview.activity

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.TextViewCompat
import com.blankj.utilcode.util.ToastUtils
import com.blankj.utilcode.util.Utils
import com.zhenquan.mycustomview.R
import kotlinx.android.synthetic.main.activity_toast.*

class ToastActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_toast2 ->{
                com.zhenquan.mycustomview.utils.ToastUtils.showShort("Test")
            }
            R.id.btn_toast -> {
                Toast.makeText(this, "test", Toast.LENGTH_SHORT).show()
            }
            R.id.btn_toast_util -> {
                ToastUtils.setBgResource(-1)
                ToastUtils.showShort("Test")
            }
            R.id.btn_toast_util_custom -> {
                ToastUtils.setBgResource(R.drawable.shape_toast)
                ToastUtils.showShort("Test")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toast)
        btn_toast.setOnClickListener(this)
        btn_toast2.setOnClickListener(this)
        btn_toast_util.setOnClickListener(this)
        btn_toast_util_custom.setOnClickListener(this)
    }
}
