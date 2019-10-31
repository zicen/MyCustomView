package com.zhenquan.mycustomview.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.zhenquan.mycustomview.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_edit.setOnClickListener(this)
        btn_pie.setOnClickListener(this)
        btn_text.setOnClickListener(this)
        btn_progress_ring.setOnClickListener(this)
        btn_circle_download.setOnClickListener(this)
        btn_shadow_helper.setOnClickListener(this)
        btn_path.setOnClickListener(this)
        btn_circle_menu.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_edit -> {
                startActivity(Intent(MainActivity@ this, EditActivity::class.java))
            }
            R.id.btn_text -> {
                startActivity(Intent(MainActivity@ this, DemoActivity::class.java))
            }
            R.id.btn_pie -> {
                startActivity(Intent(MainActivity@ this, PieViewActivity::class.java))
            }
            R.id.btn_progress_ring -> {
                startActivity(Intent(MainActivity@ this, ProgressRingActivity::class.java))
            }
            R.id.btn_circle_download -> {
                startActivity(Intent(MainActivity@ this, CircleDownloadProgressBarActivity::class.java))
            }
            R.id.btn_shadow_helper -> {
                startActivity(Intent(MainActivity@ this, ShadowHelperAvtivity::class.java))
            }
            R.id.btn_path->{
                startActivity(Intent(MainActivity@ this, PathActivity::class.java))
            }
            R.id.btn_circle_menu->{
                startActivity(Intent(MainActivity@ this, CircleMenuActivity::class.java))
            }
        }
    }


}
