package com.zhenquan.mycustomview.customview.path;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.zhenquan.mycustomview.R;

public class ComputeBounds extends View {

    private Paint paint;

    public ComputeBounds(Context context) {
        super(context, null);
    }

    public ComputeBounds(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    private void initPaint() {
        paint = new Paint();
        paint.setColor(getResources().getColor(R.color.colorPrimary));
        paint.setStyle(Paint.Style.FILL);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int w = getMeasuredWidth();
        int h = getMeasuredHeight();

        canvas.translate(w / 2, h / 2);
        RectF rectF = new RectF(); // 存放测量结果的矩形

        Path path = new Path();
        path.lineTo(100, -50);
        path.lineTo(100, 50);
        path.close();

        path.addCircle(-100, 0, 100, Path.Direction.CW);
        // 测量结果会放到第一个矩形，第二个 boolean 参数代表是否精确测量，一般写 true 即可
        path.computeBounds(rectF, true);
        canvas.drawPath(path, paint);
        paint.setColor(getResources().getColor(R.color.colorAccent));
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(rectF, paint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);


    }
}
