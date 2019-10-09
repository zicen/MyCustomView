package com.zhenquan.mycustomview;

import android.app.Application;
import android.content.Context;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;

public class MyApplication extends Application {
    private static MyApplication baseApplication;
    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication = this;
        Utils.init(this);

    }
    public static Context getAppContext() {
        return baseApplication;
    }

}
