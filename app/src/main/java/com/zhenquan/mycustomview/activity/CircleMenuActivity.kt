package com.zhenquan.mycustomview.activity

import android.animation.*
import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationSet
import android.view.animation.TranslateAnimation
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.zhenquan.mycustomview.R
import com.zhenquan.mycustomview.utils.DisplayUtils
import kotlinx.android.synthetic.main.activity_circle_menu.*
import kotlinx.android.synthetic.main.activity_text.*

class CircleMenuActivity : AppCompatActivity() {
    private val menuViews = ArrayList<View>()
    private var isOpened = false
    var context: Context? = null
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
