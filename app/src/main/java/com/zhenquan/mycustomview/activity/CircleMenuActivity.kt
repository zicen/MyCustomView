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
//    private void realSendEnterNotice(SocketJoinQueueItemBean socketJoinQueueItemBean) {
//        stopEnterNoticeAnim();
//        mLiveEnterNotice.setData(socketJoinQueueItemBean);
//
//        // mLiveEnterNotice.setLayerType(View.LAYER_TYPE_HARDWARE,null);
//        int screenLeftX = -ScreenUtils.getAppScreenWidth() / 2;
//        // 第一个动画
//        PropertyValuesHolder pvhAlpha = PropertyValuesHolder.ofFloat("alpha", 0f, 1f);
//        PropertyValuesHolder pvhTranslationX = PropertyValuesHolder.ofFloat("translationX",
//        screenLeftX + DANMU_ENTER_END_X);
//        ObjectAnimator anim = ObjectAnimator.ofPropertyValuesHolder(mLiveEnterNotice, pvhAlpha, pvhTranslationX);
//        anim.setDuration(DANMU_ENTER_DURATION);
//        anim.setInterpolator(new DecelerateInterpolator());
//
//        // 第二个动画
//        PropertyValuesHolder pvhAlpha2 = PropertyValuesHolder.ofFloat("alpha", 1f, 0f);
//        PropertyValuesHolder pvhTranslationX2 = PropertyValuesHolder.ofFloat("translationX",
//        screenLeftX - mLiveEnterNotice.getWidth());
//        ObjectAnimator anim2 = ObjectAnimator.ofPropertyValuesHolder(mLiveEnterNotice, pvhAlpha2, pvhTranslationX2);
//        anim2.setDuration(DANMU_QUIT_DURATION);
//        anim2.setInterpolator(new AccelerateInterpolator());
//
//        mEnterNoticeanimSet = new AnimatorSet();
//        // mEnterNoticeanimSet.setInterpolator(new AccelerateDecelerateInterpolator());
//        // 动画执行的监听回调事件
//        mEnterNoticeanimSet.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                // 动画结束 设置控件到初始位置
//                if (mLiveEnterNotice == null || isQuitRoom) {
//                    return;
//                }
//                // mLiveEnterNotice.setLayerType(View.LAYER_TYPE_NONE,null);
//                mLiveEnterNotice.setX(ScreenUtils.getAppScreenWidth());
//                mLiveEnterNotice.requestLayout();
//                LiveNoticeQueueManager.getInstance().getLiveEnterNoticeQueue().notifyTask();
//            }
//        });
//
//        mEnterNoticeanimSet.play(anim);
//        mEnterNoticeanimSet.play(anim2)
//            .after(DANMU_STAY_DURATION + DANMU_ENTER_DURATION);
//        // 正式启动动画集
//        mEnterNoticeanimSet.start();
//
//    }
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
