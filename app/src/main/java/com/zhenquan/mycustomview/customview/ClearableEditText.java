package com.zhenquan.mycustomview.customview;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.zhenquan.mycustomview.R;
import com.zhenquan.mycustomview.loding.UiUtils;


public class ClearableEditText extends AppCompatEditText {
    private static final int DRAWABLE_LEFT = 0;
    private static final int DRAWABLE_TOP = 1;
    private static final int DRAWABLE_RIGHT = 2;
    private static final int DRAWABLE_BOTTOM = 3;
    private Drawable mClearDrawable;
    private Drawable mDrawableLeft;

    public ClearableEditText(Context context) {
        super(context);
        init(context);
    }

    public ClearableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ClearableEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    private void init(Context context) {
        mClearDrawable = ContextCompat.getDrawable(context, R.drawable.delete_search_history);
        mDrawableLeft = ContextCompat.getDrawable(context, R.drawable.search_hint);
        mClearDrawable.setBounds(0, 0, UiUtils.dipToPx(context, 15), UiUtils.dipToPx(context, 15));
        mDrawableLeft.setBounds(0, 0, UiUtils.dipToPx(context, 15), UiUtils.dipToPx(context, 15));
        setCompoundDrawablePadding(UiUtils.dipToPx(context, 9));
        setCompoundDrawables(mDrawableLeft, null, null, null);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        setClearIconVisible(hasFocus() && text.length() > 0);
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        setClearIconVisible(focused && length() > 0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                Drawable drawable = getCompoundDrawables()[DRAWABLE_RIGHT];
                if (drawable != null && event.getX() <= (getWidth() - getPaddingRight())
                        && event.getX() >= (getWidth() - getPaddingRight() - drawable.getBounds().width())) {
                    setText("");
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    private void setClearIconVisible(boolean visible) {
        setCompoundDrawables(mDrawableLeft, null, visible ? mClearDrawable : null, null);
    }
}
