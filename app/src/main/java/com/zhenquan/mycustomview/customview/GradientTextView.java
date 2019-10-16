package com.zhenquan.mycustomview.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;
import com.zhenquan.mycustomview.R;

/**
 * Android 渐变色TextView
 */
public class GradientTextView extends AppCompatTextView {
    private int startColor;
    private int endColor;

    public GradientTextView(Context context) {
        super(context, null);
    }

    public GradientTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.GradientTextView);
        startColor = typedArray.getColor(R.styleable.GradientTextView_startColor, getCurrentTextColor());
        endColor = typedArray.getColor(R.styleable.GradientTextView_endColor, getCurrentTextColor());
        typedArray.recycle();
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onLayout(boolean changed,
                            int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            getPaint().setShader(new LinearGradient(
                    0, 0, getWidth(), getHeight(),
                    startColor, endColor,
                    Shader.TileMode.CLAMP));
        }
    }


}

