package com.zhenquan.mycustomview.activity

import android.animation.*
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.blankj.utilcode.util.ScreenUtils
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.zhenquan.mycustomview.R
import com.zhenquan.mycustomview.utils.DisplayUtils
import kotlinx.android.synthetic.main.activity_circle_menu.*

class CircleMenuActivity : AppCompatActivity() {
    private val menuViews = ArrayList<View>()
    private var isOpened = false
    var context: Context? = null

    // 弹幕距离屏幕左侧的距离
    val DANMU_ENTER_END_X = DisplayUtils.dp2px(10F)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_circle_menu)
        context = this;


        val menuFab = findViewById<FloatingActionButton>(R.id.fab_menu)
        menuFab.setOnClickListener {
            switchMenu(isOpened)
            isOpened = !isOpened
        }
        btn_send.setOnClickListener {

            // 做动画 总共三个关键帧 第一个关键帧是进入动画 1s 占比是三分之一，然后是等待动画
            val decelerateInterpolator = DecelerateInterpolator()
            val accelerateDecelerateInterpolator = AccelerateDecelerateInterpolator()
            val kfAlpha0 = Keyframe.ofFloat(0f, 0f)
            val kfAlpha1 = Keyframe.ofFloat(0.34f, 1f)
            kfAlpha1.interpolator = decelerateInterpolator
            val kfAlpha2 = Keyframe.ofFloat(0.67f, 1f)
            val kfAlpha3 = Keyframe.ofFloat(1f, 0f)
            kfAlpha3.interpolator = accelerateDecelerateInterpolator

            val screenLeftX = -ScreenUtils.getAppScreenWidth() / 2
            val moveLeft = screenLeftX + DANMU_ENTER_END_X
            val kfTrans0 = Keyframe.ofFloat(0f,0f)
            val kfTrans1 = Keyframe.ofFloat(0.34f,moveLeft)
            kfTrans1.interpolator = decelerateInterpolator
            val kfTrans2 = Keyframe.ofFloat(0.67f, moveLeft)
            val kfTrans3 = Keyframe.ofFloat(1f, moveLeft -(live_danmu.width + DANMU_ENTER_END_X))
            kfTrans3.interpolator = accelerateDecelerateInterpolator


            val alphaPvh = PropertyValuesHolder.ofKeyframe(View.ALPHA, kfAlpha0,kfAlpha1, kfAlpha2, kfAlpha3)
            val transPvh = PropertyValuesHolder.ofKeyframe(View.TRANSLATION_X, kfTrans0, kfTrans1, kfTrans2,kfTrans3)
            val objectAnimator = ObjectAnimator.ofPropertyValuesHolder(live_danmu, alphaPvh,transPvh)
            objectAnimator.duration = 3000
            objectAnimator.start()

        }
    }

    private fun switchMenu(isOpen: Boolean) {
        val startRadius = dpToPixel(if (isOpen) 90 else 0)
        val endRadius = dpToPixel(if (isOpen) 0 else 90)
        val anim = ValueAnimator.ofInt(startRadius, endRadius)
        anim.duration = 300
        anim.addUpdateListener { valueAnimator ->
            val radius: Int = valueAnimator.animatedValue as Int
            menuViews.forEach { view ->
                val lp = view.layoutParams as ConstraintLayout.LayoutParams
                lp.circleRadius = radius
                view.layoutParams = lp
            }
        }
        anim.start()
    }

    fun Context.dpToPixel(dp: Int): Int {
        val displayMetrics = this.resources.displayMetrics
        return if (dp < 0) dp else Math.round(dp * displayMetrics.density)
    }

}
