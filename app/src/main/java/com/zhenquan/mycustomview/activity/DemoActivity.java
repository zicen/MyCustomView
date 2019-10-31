package com.zhenquan.mycustomview.activity;

import android.animation.*;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import com.zhenquan.mycustomview.R;
import com.zhenquan.mycustomview.customview.maoer.LiveGlobalNoticeView;
import com.zhenquan.mycustomview.utils.DisplayUtils;
import com.zhenquan.mycustomview.utils.QMUIDirection;
import com.zhenquan.mycustomview.utils.QMUIViewHelper;

import static com.zhenquan.mycustomview.utils.DisplayUtils.dp2px;

public class DemoActivity extends AppCompatActivity {

    private ObjectAnimator anim_translationX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        ConstraintLayout container = findViewById(R.id.container);
        LinearLayout ll_container = findViewById(R.id.ll_container);
        LiveGlobalNoticeView live_global2 = findViewById(R.id.live_global2);
        AppCompatButton btn_start_anim = findViewById(R.id.btn_start_anim);
        AppCompatButton btn_start_anim2 = findViewById(R.id.btn_start_anim2);

        live_global2.setOnClickListener(v -> ToastUtils.showShort("点击了"));
        btn_start_anim.setOnClickListener(v -> {
            // 位移动画：水平左移然后复位
            if (anim_translationX == null) {
                anim_translationX = ObjectAnimator.ofFloat(live_global2, "translationX", -ScreenUtils.getAppScreenWidth() - live_global2.getWidth());
                anim_translationX.setDuration(8000);
                anim_translationX.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        live_global2.setX(ScreenUtils.getAppScreenWidth());
                        live_global2.requestLayout();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
            }

            if (anim_translationX.isRunning()) {
                anim_translationX.cancel();
                anim_translationX.start();
            } else {
                anim_translationX.start();
            }
        });
        btn_start_anim2.setOnClickListener(v -> {
//            ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,
//                    ConstraintLayout.LayoutParams.WRAP_CONTENT);
//
//            ImageView imageView = new ImageView(DemoActivity.this);
//            imageView.setId(R.id.notice_global);
//            imageView.setImageResource(R.mipmap.bg_live_colorful_notice);
//
//            layoutParams.topToBottom = R.id.btn_start_anim2;
//            layoutParams.leftToRight = R.id.container;
//
//            imageView.setLayoutParams(layoutParams);
//
//            container.addView(imageView);
//
//            imageView.post(() -> {
//                LogUtils.e("getWidth:" + imageView.getWidth());
//                startAnima(container, imageView);
//            });


            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int)DisplayUtils.dp2px(325),(int)DisplayUtils.dp2px(26));

            LiveGlobalNoticeView imageView = new LiveGlobalNoticeView(DemoActivity.this);
            imageView.setId(R.id.notice_global);
            imageView.setData("test");

            imageView.setLayoutParams(layoutParams);

            ll_container.addView(imageView);

            imageView.post(() -> {
                LogUtils.e("getWidth:" + imageView.getWidth());
                startAnima(ll_container, imageView);
            });


//            FrameLayout frameLayout = new FrameLayout(DemoActivity.this);
//            FrameLayout.LayoutParams tvParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
//            tvParams.leftMargin = (int) DisplayUtils.dp2px(10);
//            tvParams.gravity = Gravity.CENTER_VERTICAL;
//            TextView textView = new TextView(DemoActivity.this);
//            textView.setTextSize(12f);
//            textView.setTextColor(Color.WHITE);
//            textView.setText("小粉丝 给主播 爱抖露 送出 1 个礼物，快来围观吧~");
//            textView.setBackgroundResource(R.mipmap.bg_live_colorful_notice);
//            textView.setPadding((int) dp2px(20), (int) dp2px(5), (int) dp2px(20), (int) dp2px(5));
//            textView.setLayoutParams(tvParams);
//            frameLayout.addView(textView, 0);
//
//            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
//            params.gravity = Gravity.START | Gravity.CENTER_VERTICAL;
//            ImageView imageView = new ImageView(DemoActivity.this);
//            imageView.setImageResource(R.mipmap.bg_live_star_left);
//            imageView.setLayoutParams(params);
//            frameLayout.addView(imageView, 1);
//
//            ll_container.addView(frameLayout);
//            frameLayout.post(() -> {
//                LogUtils.e("getWidth:" + frameLayout.getWidth());
//                startAnima(ll_container, frameLayout);
//            });
        });
    }

    private void startAnima(ViewGroup container, View imageView) {
        ObjectAnimator anim_translationX = ObjectAnimator.ofFloat(imageView, "translationX", -ScreenUtils.getAppScreenWidth() - imageView.getMeasuredWidth());
        anim_translationX.setDuration(8000);
        anim_translationX.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                container.removeView(imageView);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        anim_translationX.start();
    }

}
