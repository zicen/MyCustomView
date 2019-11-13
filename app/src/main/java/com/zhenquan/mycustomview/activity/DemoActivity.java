package com.zhenquan.mycustomview.activity;

import android.animation.*;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatButton;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.ToastUtils;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import com.zhenquan.mycustomview.R;

public class DemoActivity extends AppCompatActivity {

    private ObjectAnimator anim_translationX;
    private AppCompatButton btn_start_anim;
    private View btn_set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        btn_start_anim = findViewById(R.id.btn_start_anim);
        btn_set = findViewById(R.id.btn_set);

    }

    private void startAnima(View view) {
        // 位移动画：水平左移然后复位
        if (anim_translationX == null) {
            anim_translationX = ObjectAnimator.ofFloat(view, "translationX", -ScreenUtils.getAppScreenWidth() - view.getWidth());
            anim_translationX.setDuration(8000);
            anim_translationX.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    view.setX(ScreenUtils.getAppScreenWidth());
                    view.requestLayout();
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
    }

}
