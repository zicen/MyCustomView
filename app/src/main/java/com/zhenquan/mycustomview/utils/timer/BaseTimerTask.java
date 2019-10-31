package com.zhenquan.mycustomview.utils.timer;

import java.util.TimerTask;

/**
 * Created by zicen on 2018/7/5
 */
public class BaseTimerTask extends TimerTask {

    private ITimerListener mITimerListener = null;

    public BaseTimerTask(ITimerListener mITimerListener) {
        this.mITimerListener = mITimerListener;
    }

    @Override
    public void run() {
        if (mITimerListener != null) {
            mITimerListener.onTimer();
        }
    }

}
