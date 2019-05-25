package com.zhenquan.mycustomview.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.zhenquan.mycustomview.R
import com.zhenquan.mycustomview.loding.LeafLoadingActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_edit.setOnClickListener(this)
        btn_pie.setOnClickListener(this)
        btn_leafloading.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_edit -> {
                startActivity(Intent(MainActivity@ this, EditActivity::class.java))
            }
            R.id.btn_pie -> {
                startActivity(Intent(MainActivity@ this, SecondActivity::class.java))
            }
            R.id.btn_leafloading -> {
                startActivity(
                    Intent(
                        MainActivity@ this,
                        LeafLoadingActivity::class.java
                    )
                )
            }
        }
    }

    inline fun consume(f: () -> Unit): Boolean {
        f()
        return true;
    }
}
