package com.zhenquan.mycustomview.customview.maoer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.zhenquan.mycustomview.R;
import com.zhenquan.mycustomview.utils.QMUIDirection;
import com.zhenquan.mycustomview.utils.QMUIViewHelper;
import com.zhenquan.mycustomview.utils.timer.BaseTimerTask;
import com.zhenquan.mycustomview.utils.timer.ITimerListener;

import java.util.Timer;

/**
 * Created by zicen on 2019/10/23
 */
public class LiveRankStatusView extends FrameLayout implements ITimerListener {
    private TextView mTxtRevenue;
    private TextView mTxtRankHour;
    private TextView mTxtRankUp;

    private Status mStatus = Status.SHOW_REVENUE_STATUS;
    private Timer mTimer;

    public LiveRankStatusView(@NonNull Context context) {
        super(context, null);
    }

    public LiveRankStatusView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(getContext()).inflate(R.layout.view_live_rank_status, this, true);

        mTxtRevenue = findViewById(R.id.txt_revenue);
        mTxtRankHour = findViewById(R.id.txt_rank_hour);
        mTxtRankUp = findViewById(R.id.txt_rank_up);

        //设置定时器，每 5 秒执行一次，切换自定义控件的状态
        mTimer = new Timer();
        final BaseTimerTask task = new BaseTimerTask(this);
        mTimer.schedule(task, 5000, 5000);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    @Override
    public void onTimer() {
        resetStatus();
//        LogUtils.e("onTimer:" + mStatus.name());
        LiveRankStatusView.this.post(this::resetUiStatus);
    }

    private void resetStatus() {
        if (mStatus == Status.SHOW_REVENUE_STATUS) {
            mStatus = Status.SHOW_RANK_HOUR_STATUS;
        } else if (mStatus == Status.SHOW_RANK_HOUR_STATUS) {
            mStatus = Status.SHOW_RANK_UP_STATUS;
        } else {
            mStatus = Status.SHOW_REVENUE_STATUS;
        }
    }

    private void resetUiStatus() {
        if (mStatus == Status.SHOW_REVENUE_STATUS) {
            QMUIViewHelper.slideOut(mTxtRankUp, 250, null, true, QMUIDirection.BOTTOM_TO_TOP);
            QMUIViewHelper.slideIn(mTxtRevenue, 250, null, true, QMUIDirection.BOTTOM_TO_TOP);
        } else if (mStatus == Status.SHOW_RANK_HOUR_STATUS) {
            QMUIViewHelper.slideOut(mTxtRevenue, 250, null, true, QMUIDirection.BOTTOM_TO_TOP);
            QMUIViewHelper.slideIn(mTxtRankHour, 250, null, true, QMUIDirection.BOTTOM_TO_TOP);
        } else {
            QMUIViewHelper.slideOut(mTxtRankHour, 250, null, true, QMUIDirection.BOTTOM_TO_TOP);
            QMUIViewHelper.slideIn(mTxtRankUp, 250, null, true, QMUIDirection.BOTTOM_TO_TOP);
        }
    }

    enum Status {
        SHOW_REVENUE_STATUS, // 显示收益的状态
        SHOW_RANK_HOUR_STATUS,// 显示小时榜的状态
        SHOW_RANK_UP_STATUS// 显示还差多少收益上升名次的状态
    }


}
