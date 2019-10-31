package com.zhenquan.mycustomview.customview.maoer;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.blankj.utilcode.util.LogUtils;
import com.zhenquan.mycustomview.R;
import com.zhenquan.mycustomview.utils.DisplayUtils;

import static com.zhenquan.mycustomview.utils.DisplayUtils.dp2px;

/**
 * Created by zicen on 2019/10/31
 */
public class LiveGlobalNoticeView extends FrameLayout {

    private TextView textView;

    public LiveGlobalNoticeView(@NonNull Context context) {
        this(context, null);
    }

    public LiveGlobalNoticeView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        LayoutParams tvParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        tvParams.leftMargin = (int) DisplayUtils.dp2px(10);
        tvParams.gravity = Gravity.CENTER_VERTICAL;
        textView = new TextView(getContext());
        textView.setTextSize(12f);
        textView.setTextColor(Color.WHITE);
        textView.setText("小粉丝 给主播 爱抖露 送出 1 个礼物，快来围观吧~");
        textView.setBackgroundResource(R.mipmap.bg_live_colorful_notice);
        textView.setPadding((int) dp2px(20), (int) dp2px(5), (int) dp2px(20), (int) dp2px(5));
        textView.setLayoutParams(tvParams);
        this.addView(textView, 0);


        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.START | Gravity.CENTER_VERTICAL;
        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(R.mipmap.bg_live_star_left);
        imageView.setLayoutParams(params);
        this.addView(imageView, 1);
    }

    public void setData(String s) {
        if (textView != null) textView.setText(s);
    }
}
