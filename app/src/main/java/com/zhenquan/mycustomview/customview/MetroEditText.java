package com.zhenquan.mycustomview.customview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Paint;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

/**
 * Created by zicen on 2019-12-08.
 */
public class MetroEditText extends AppCompatEditText {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private boolean haveChanged = false;
    public MetroEditText(Context context) {
        this(context,null);
    }

    public MetroEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (haveChanged && s.length() > 0) {
                    getAnima().start();
                }else {
                    getAnima().reverse();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private float alphaAndTrans;

    public float getAlphaAndTrans() {
        return alphaAndTrans;
    }

    public void setAlphaAndTrans(float alphaAndTrans) {
        this.alphaAndTrans = alphaAndTrans;
        invalidate();
    }

    private ObjectAnimator getAnima(){
        return  ObjectAnimator.ofFloat(MetroEditText.this,"alphaAndTrans",0,1);
    }
}
