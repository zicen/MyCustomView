package com.zhenquan.mycustomview.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * 创建人: zhenquan
 * 创建日期: 2018/7/4
 * 邮箱: 1140377034@qq.com
 * github: https://github.com/zicen
 */

public class CustomView1 extends View {
    // 1.创建一个画笔
    private Paint mPaint = new Paint();

    // 2.初始化画笔
    private void initPaint() {
        mPaint.setColor(Color.BLACK);//设置画笔颜色
        mPaint.setStyle(Paint.Style.FILL);//设置画笔模式为填充.画笔有三种模式:STROKE(描边),FILL(填充),FILL_AND_STROKE(描边加填充)
        mPaint.setStrokeWidth(50);//设置画笔宽度
    }

    // 3.在构造函数中初始化
    public CustomView1(Context context) {
        super(context);
        initPaint();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //1.绘制点
        mPaint.setColor(Color.RED);
        canvas.drawPoint(50, 50, mPaint);
        canvas.drawPoints(new float[]{
                60, 60,
                70, 70,
                80, 80
        }, mPaint);
        //2.在坐标(300,300)(500,600)之间绘制一条直线
        canvas.drawLine(50, 50, 500, 60, mPaint);

        //3.绘制矩形
        /**
         * Canvas提供了三种重载方法，第一种就是提供四个数值(矩形左上角和右下角两个点的坐标)来确定一个矩形进行绘制。
         * 其余两种是先将矩形封装为Rect或RectF(实际上仍然是用两个坐标点来确定的矩形)，然后传递给Canvas绘制
         * Rect和RectF两者最大的区别就是精度不同，Rect是int(整形)的，而RectF是float(单精度浮点型)的
         */
        mPaint.setColor(Color.GRAY);
        canvas.drawRect(100, 100, 800, 400, mPaint);

        Rect rect = new Rect(100, 100, 800, 400);
        canvas.drawRect(rect, mPaint);

        RectF rectF = new RectF(100, 100, 800, 400);
        canvas.drawRect(rectF, mPaint);
        //4.绘制圆 (绘制圆形有四个参数，前两个是圆心坐标，第三个是半径，最后一个是画笔。)
        mPaint.setColor(Color.GREEN);
        canvas.drawCircle(500,500,400,mPaint);  // 绘制一个圆心坐标在(500,500)，半径为400 的圆。
        //5.绘制圆弧
        /**
         * 相比于绘制椭圆，绘制圆弧还多了三个参数：
         * startAngle  // 开始角度
         sweepAngle  // 扫过角度
         useCenter   // 是否使用中心
         使用了中心点之后绘制出来类似于一个扇形，而不使用中心点则是圆弧起始点和结束点之间的连线加上圆弧围成的图形。
         */
        RectF rectF2 = new RectF(100,700,600,1200);
        mPaint.setColor(Color.GRAY);
        canvas.drawRect(rectF2,mPaint);
        mPaint.setColor(Color.BLUE);
        canvas.drawArc(rectF2,0,90,true,mPaint);
        //6.画笔的三种模式
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(40);     //为了实验效果明显，特地设置描边宽度非常大

        // 描边
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(200,200,100,paint);

        // 填充
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(200,500,100,paint);

        // 描边加填充
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawCircle(200, 800, 100, paint);
    }


    public CustomView1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomView1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthsize = MeasureSpec.getSize(widthMeasureSpec); //取出宽度的确切数值
        int widthmode = MeasureSpec.getMode(widthMeasureSpec);//取出宽度的测量模式
        int heightsize = MeasureSpec.getSize(heightMeasureSpec);//取出高度的确切数值
        int heightmode = MeasureSpec.getMode(heightMeasureSpec);//取出高度的测量模式

        Log.e("CustomView1", "widthsize:" + widthsize);
        Log.e("CustomView1", "widthmode:" + widthmode);
        Log.e("CustomView1", "heightsize:" + heightsize);
        Log.e("CustomView1", "heightmode:" + heightmode);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.e("CustomView1", "onSizeChanged w:" + w);
        Log.e("CustomView1", "onSizeChanged h:" + h);
    }
}
