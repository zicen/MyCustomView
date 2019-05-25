package com.zhenquan.mycustomview.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.zhenquan.mycustomview.R
import com.zhenquan.mycustomview.customview.pieview.PieData
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        val arrayListOf = arrayListOf<PieData>()
        arrayListOf.add(PieData("name1", 100f))
        arrayListOf.add(PieData("name2", 200f))
        arrayListOf.add(PieData("name3", 300f))
        arrayListOf.add(PieData("name4", 10f))
        arrayListOf.add(PieData("name5", 800f))
        arrayListOf.add(PieData("name6", 80f))
        pieview.setData(arrayListOf)
    }
}
