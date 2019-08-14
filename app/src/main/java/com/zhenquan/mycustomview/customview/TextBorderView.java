package com.zhenquan.mycustomview.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.zhenquan.mycustomview.R;

/**
 * 可以自定义的圆角以及背景等的 TextView
 */
public class TextBorderView extends AppCompatTextView {

    private int border_Select_Color;//线框选中颜色
    private int border_Color;//线框颜色
    private int background_Select_Color;//背景选中颜色
    private int background_Color;//背景颜色
    private int border_Width = 0;//线框宽度
    private int border_Radius = 0;//圆角
    private Paint paintBg;//画背景的
    private RectF recBg;//背景矩形
    private Paint paintBorder;//画边框的
    private RectF recBorder;//边框矩形
    private DrawFilter mDrawFilter;

    public TextBorderView(Context context) {
        super(context, null);
    }

    public TextBorderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public TextBorderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        parseAttr(attrs);
        initPaint();
    }

    private void parseAttr(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TextBorderView);
        border_Color = typedArray.getColor(R.styleable.TextBorderView_tbv_border_color, 0);
        border_Select_Color = typedArray.getColor(R.styleable.TextBorderView_tbv_border_select_color, 0);
        background_Color = typedArray.getColor(R.styleable.TextBorderView_tbv_bg_color, 0);
        background_Select_Color = typedArray.getColor(R.styleable.TextBorderView_tbv_bg_select_color, 0);
        border_Width = typedArray.getDimensionPixelSize(R.styleable.TextBorderView_tbv_border_width, 0);
        border_Radius = typedArray.getDimensionPixelSize(R.styleable.TextBorderView_tbv_border_radius, 0);
        typedArray.recycle();
    }

    private void initPaint() {
        paintBg = new Paint();
        paintBg.setColor(background_Color);// 画笔颜色

        paintBorder = new Paint();
        paintBorder.setColor(border_Color);// 画笔颜色
        paintBorder.setStrokeWidth((float) border_Width);// 线宽
        paintBorder.setStyle(Paint.Style.STROKE);// 空心效果
        paintBorder.setAntiAlias(true);// 无锯齿
        paintBorder.setDither(true);// 防抖动

        mDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.setDrawFilter(mDrawFilter);// 从canvas层面去除绘制时锯齿
        canvas.drawRoundRect(recBg, border_Radius, border_Radius, paintBg); // 绘制圆角矩形
        canvas.drawRoundRect(recBorder, border_Radius, border_Radius, paintBorder); // 绘制圆角矩形
        super.onDraw(canvas);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                actionDown();
                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                actionUp();
                break;
            default:
                break;
        }
        return true;
    }

    private void actionDown() {
        if (isEnabled()) {
            if (background_Select_Color != 0) {
                paintBg.setColorFilter(new LightingColorFilter(0, background_Select_Color));
            }
            if (border_Select_Color != 0) {
                paintBorder.setColorFilter(new LightingColorFilter(0, border_Select_Color));
            }
            postInvalidateDelayed(10);
        }
    }

    private void actionUp() {
        if (isEnabled()) {
            if (background_Color != 0) {
                paintBg.setColorFilter(new LightingColorFilter(0, background_Color));
            }
            if (border_Color != 0) {
                paintBorder.setColorFilter(new LightingColorFilter(0, border_Color));
            }
            postInvalidateDelayed(10);
        }
    }

    private boolean inRangeOfView(View view, MotionEvent event) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        if (event.getX() < x || event.getX() > (x + view.getWidth()) || event.getY() < y || event.getY() > (y + view.getHeight())) {
            return false;
        }
        return true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        recBg = new RectF(); // RectF对象
        recBg.left = w - getWidth() + border_Width / 2; // 左边
        recBg.top = h - getHeight() + border_Width / 2; // 上边
        recBg.right = w - border_Width / 2; // 右边
        recBg.bottom = h - border_Width / 2; // 下边

        recBorder = new RectF(); // RectF对象
        recBorder.left = w - getWidth() + border_Width / 2; // 左边
        recBorder.top = h - getHeight() + border_Width / 2; // 上边
        recBorder.right = w - border_Width / 2; // 右边
        recBorder.bottom = h - border_Width / 2; // 下边
    }

    public void setBorderSelectColor(int border_Select_Color) {
        this.border_Select_Color = border_Select_Color;
        paintBorder.setColorFilter(new LightingColorFilter(0, border_Select_Color));
        postInvalidateDelayed(10);
    }

    public void setBorderColor(int border_Color) {
        this.border_Color = border_Color;
        paintBorder.setColorFilter(new LightingColorFilter(0, border_Color));
        postInvalidateDelayed(10);
    }

    public void setBackgroundSelectColor(int background_Select_Color) {
        this.background_Select_Color = background_Select_Color;
        paintBg.setColorFilter(new LightingColorFilter(0, background_Select_Color));
        postInvalidateDelayed(10);
    }

    @Override
    public void setBackgroundColor(int color) {
        this.background_Color = color;
        paintBg.setColorFilter(new LightingColorFilter(0, background_Color));
        postInvalidateDelayed(10);
    }

}
