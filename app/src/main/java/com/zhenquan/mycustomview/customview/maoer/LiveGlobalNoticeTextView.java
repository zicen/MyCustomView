package com.zhenquan.mycustomview.customview.maoer;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * Created by zicen on 2019/11/12
 * 可以很长的 TextView
 */
public class LiveGlobalNoticeTextView extends AppCompatTextView {

    public LiveGlobalNoticeTextView(Context context) {
        this(context, null);
    }

    public LiveGlobalNoticeTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = 0;
        int height = 0;
        /**
         * 设置宽度
         */
        int specMode = View.MeasureSpec.getMode(widthMeasureSpec);
        int specSize = View.MeasureSpec.getSize(widthMeasureSpec);
        switch (specMode) {
            case View.MeasureSpec.EXACTLY:
                width = getPaddingLeft() + getPaddingRight() + specSize;
                break;
            case View.MeasureSpec.AT_MOST:
            case View.MeasureSpec.UNSPECIFIED:
                float textWidth = getPaint().measureText(getText().toString());
                width = (int) (getPaddingLeft() + getPaddingRight() + textWidth);
                break;
        }
        /**
         * 设置高度
         */
        specMode = View.MeasureSpec.getMode(heightMeasureSpec);
        specSize = View.MeasureSpec.getSize(heightMeasureSpec);
        switch (specMode) {
            case View.MeasureSpec.EXACTLY:
                height = getPaddingTop() + getPaddingBottom() + specSize;
                break;
            case View.MeasureSpec.AT_MOST:
            case View.MeasureSpec.UNSPECIFIED:
                Paint.FontMetrics fontMetrics = getPaint().getFontMetrics();
                float textHeight = Math.abs((fontMetrics.bottom - fontMetrics.top));
                height = (int) (getPaddingTop() + getPaddingBottom() + Math.max(textHeight,getBackground().getMinimumHeight()));
                break;
        }
        setMeasuredDimension(width, height);
    }


}
