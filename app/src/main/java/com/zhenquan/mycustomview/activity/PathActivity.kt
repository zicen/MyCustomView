package com.zhenquan.mycustomview.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zhenquan.mycustomview.R
import kotlinx.android.synthetic.main.activity_path.*

class PathActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_path)


        radio_group.setOnCheckedChangeListener { group, checkedId ->
            run {
                if (checkedId == R.id.rb_1) {
                    bezier.setMode(true)
                } else {
                    bezier.setMode(false)
                }
            }
        }
    }
}
