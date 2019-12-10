package com.zhenquan.mycustomview.customview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

import com.zhenquan.mycustomview.utils.DisplayUtils;

/**
 * Created by zicen on 2019-12-08.
 */
public class MaterialEditText extends AppCompatEditText {
    private static final float TEXT_SIZE = DisplayUtils.dp2px(12);
    private static final float TEXT_MARGIN = DisplayUtils.dp2px(8);
    private static final float VERTICAL_OFFSET = DisplayUtils.dp2px(38);
    private static final float HORIZONTAL_OFFSET = DisplayUtils.dp2px(5);
    private static final float EXTRA_OFFSET = DisplayUtils.dp2px(16);


    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Rect backgroundPadding = new Rect();
    boolean floatingLabelShown;
    private ObjectAnimator animator;

    public MaterialEditText(Context context) {
        this(context, null);
    }

    public MaterialEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        paint.setTextSize(TEXT_SIZE);
        refreshPadding();

        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!floatingLabelShown && !TextUtils.isEmpty(s)) {
                    floatingLabelShown = true;
                    getAnima().start();
                } else if (floatingLabelShown && TextUtils.isEmpty(s)){
                    floatingLabelShown = false;
                    getAnima().reverse();
                }
            }
        });
    }

    private void refreshPadding() {
        getBackground().getPadding(backgroundPadding);
        setPadding(backgroundPadding.left, (int) (backgroundPadding.top + TEXT_SIZE + TEXT_MARGIN), backgroundPadding.right, backgroundPadding.bottom);
    }

    private float floatingLabelFraction;

    private ObjectAnimator getAnima() {
        if (animator == null) {
            animator = ObjectAnimator.ofFloat(MaterialEditText.this, "floatingLabelFraction", 0,1);
        }
        return animator;
    }

    public float getFloatingLabelFraction() {
        return floatingLabelFraction;
    }

    public void setFloatingLabelFraction(float floatingLabelFraction) {
        this.floatingLabelFraction = floatingLabelFraction;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setAlpha((int) (floatingLabelFraction * 0xff));
        float extraOffset = -EXTRA_OFFSET * floatingLabelFraction;
        canvas.drawText(getHint().toString(), HORIZONTAL_OFFSET, VERTICAL_OFFSET + extraOffset, paint);
    }
}
