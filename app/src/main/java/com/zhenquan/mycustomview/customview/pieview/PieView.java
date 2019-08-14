package com.zhenquan.mycustomview.customview.pieview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

/**
 * 创建人: zhenquan
 * 创建日期: 2018/7/4
 * 邮箱: 1140377034@qq.com
 * github: https://github.com/zicen
 */

public class PieView extends View {
    // 颜色表 (注意: 此处定义颜色使用的是ARGB，带Alpha通道的)
    private int[] mColors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080,
            0xFFE6B800, 0xFF7CFC00};
    // 数据
    private ArrayList<PieData> mData;
    // 饼状图初始绘制角度
    private float mStartAngle = 0;
    private Paint mPaint = new Paint();
    private int mWidth, mHeight;

    public PieView(Context context) {
        this(context, null);
    }

    public PieView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (null == mData) return;
        float currentStartAngle = mStartAngle;
        canvas.translate(mWidth / 2, mHeight / 2);
        float r = (float) (Math.min(mWidth, mHeight) / 2 * 0.8);
        RectF rectF = new RectF(-r, -r, r, r);
        for (int i = 0; i < mData.size(); i++) {
            PieData pieData = mData.get(i);
            mPaint.setColor(pieData.getColor());
            canvas.drawArc(rectF, currentStartAngle, pieData.getAngle(), true, mPaint);
            currentStartAngle += pieData.getAngle();
        }


    }

    public void setStartAngle(int angle) {
        this.mStartAngle = angle;
        invalidate();
    }

    public void setData(ArrayList<PieData> data) {
        this.mData = data;
        initData(data);
        invalidate();   // 刷新
    }

    private void initData(ArrayList<PieData> data) {
        if (null == data || data.size() == 0) return;
        float sumValue = 0;
        for (int i = 0; i < data.size(); i++) {
            PieData pieData = data.get(i);
            sumValue += pieData.getValue();
            int j = i % mColors.length;
            pieData.setColor(mColors[j]);
        }

        float sumAngle = 0;
        for (int i = 0; i < data.size(); i++) {
            PieData pieData = data.get(i);
            float percentage = pieData.getValue() / sumValue;
            float angle = percentage * 360;
            pieData.setPercentage(percentage);
            pieData.setAngle(angle);
            sumAngle += angle;
        }

    }
}
