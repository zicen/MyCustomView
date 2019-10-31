package com.zhenquan.mycustomview.activity

import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationSet
import android.view.animation.TranslateAnimation
import com.blankj.utilcode.util.ToastUtils
import com.zhenquan.mycustomview.R
import com.zhenquan.mycustomview.utils.DisplayUtils
import kotlinx.android.synthetic.main.activity_circle_menu.*
import kotlinx.android.synthetic.main.activity_text.*

class TextActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text)
        tbv.setOnClickListener {
            val animator = ValueAnimator.ofFloat(DisplayUtils.getScreenWidth(this).toFloat(), 0f)
            animator.duration = 2000;
            animator.startDelay = 1000;
            animator.addUpdateListener {
                val curValue = it.getAnimatedValue() as Float;
                txt.x = curValue;
                txt.requestLayout()
            }
            animator.start()
        }



    }


}
