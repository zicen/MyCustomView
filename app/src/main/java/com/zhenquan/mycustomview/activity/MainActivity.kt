package com.zhenquan.mycustomview.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.zhenquan.mycustomview.R
import com.zhenquan.mycustomview.loding.LeafLoadingActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_pie.setOnClickListener {
            startActivity(Intent(MainActivity@this, SecondActivity::class.java))
        }
        btn_leafloading.setOnClickListener { startActivity(Intent(MainActivity@this, LeafLoadingActivity::class.java)) }
    }
}
