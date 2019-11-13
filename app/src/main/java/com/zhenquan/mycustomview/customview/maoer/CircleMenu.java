package com.zhenquan.mycustomview.customview.maoer;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.zhenquan.mycustomview.R;
import com.zhenquan.mycustomview.utils.DisplayUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zicen on 2019/9/24
 */
public class CircleMenu extends FrameLayout implements View.OnClickListener {
    private CardView mCardLiveStart;
    private TextView mTxtLiveStart;
    private CardView mCardLiveCenter;
    private TextView mTxtLiveCenter;
    private CardView mCardStatus;
    private ImageView mImgStatus;
    private ConstraintLayout mContainer;
    private boolean isExpand;
    private List<View> menuViews = new ArrayList<>();
    private long mDuration = 300;

    public CircleMenu(@NonNull Context context) {
        super(context, null);
    }

    public CircleMenu(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View contentView = View.inflate(getContext(), R.layout.view_home_live_menu, this);
        mContainer = findViewById(R.id.container);
        mCardLiveStart = findViewById(R.id.card_live_start);
        mTxtLiveStart = findViewById(R.id.txt_live_start);
        mCardLiveCenter = findViewById(R.id.card_live_center);
        mTxtLiveCenter = findViewById(R.id.txt_live_center);
        mCardStatus = findViewById(R.id.card_status);
        mImgStatus = findViewById(R.id.img_status);
        mCardLiveStart.setOnClickListener(this);
        mCardLiveCenter.setOnClickListener(this);
        menuViews.add(mCardLiveStart);
        menuViews.add(mCardLiveCenter);
        mCardStatus.setOnClickListener(v -> {
            isExpand = !isExpand;
            toggle();
        });
    }


    private void toggle() {
        mCardStatus.setCardBackgroundColor(isExpand ? Color.parseColor("#ffffff") : Color.parseColor("#e6635c"));
        startStatusImgAnima();
        startAlphaChangeAnima();
        startRaduisChangeAnima();
        if (isExpand) {
            if (stateChangedLisener != null) {
                stateChangedLisener.onExpand();
            }
        } else {
            if (stateChangedLisener != null) {
                stateChangedLisener.onHide();
            }
        }
    }


    private void startRaduisChangeAnima() {
        int startRadius = (int) DisplayUtils.dp2px(isExpand ? 0 : 60);
        int endRadius = (int) DisplayUtils.dp2px(isExpand ? 60 : 0);
        ValueAnimator valueAnimator = ValueAnimator.ofInt(startRadius, endRadius);
        valueAnimator.setDuration(mDuration);
        valueAnimator.addUpdateListener(animation -> {
            int radius = (int) animation.getAnimatedValue();
            for (View view : menuViews) {
                ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) view.getLayoutParams();
                layoutParams.circleRadius = radius;
                view.setLayoutParams(layoutParams);
            }
        });
        valueAnimator.start();
    }

    private void startAlphaChangeAnima() {
        if (isExpand) {
            for (View view : menuViews) {
                view.setVisibility(VISIBLE);
            }
        }
        int startAlpha = (int) DisplayUtils.dp2px(isExpand ? 0 : 1);
        int endAlpha = (int) DisplayUtils.dp2px(isExpand ? 1 : 0);
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(startAlpha, endAlpha);
        valueAnimator.setDuration(mDuration);
        valueAnimator.addUpdateListener(animation -> {
            float radius = (float) animation.getAnimatedValue();
            for (View view : menuViews) {
                view.setAlpha(radius);
            }
        });
        valueAnimator.start();
    }

    private void startStatusImgAnima() {
        int startAnchor = isExpand ? 0 : -45;
        int endAnchor = isExpand ? -45 : 0;
        mImgStatus.setImageDrawable(getResources().getDrawable(isExpand ? R.drawable.ic_vector_delete_menu : R.drawable.ic_vector_add_menu));
        mImgStatus.setRotation(startAnchor);
        PropertyValuesHolder pvhRotation = PropertyValuesHolder.ofFloat(View.ROTATION, endAnchor);
        ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(mImgStatus, pvhRotation);
        animation.start();
    }

    public void setDuration(long duration) {
        this.mDuration = duration;
    }

    public void closeMenu() {
        if (!isExpand) return;
        isExpand = false;
        toggle();
    }

    public void expandMenu() {
        if (isExpand) return;
        isExpand = true;
        toggle();
    }


    private OnMenuClickLisener onMenuClickLisener;
    private OnMenuStateChangedLisener stateChangedLisener;


    public void setOnMenuClickLisener(OnMenuClickLisener lisener) {
        this.onMenuClickLisener = lisener;
    }

    public void setOnMenuStateChangedLisener(OnMenuStateChangedLisener lisener) {
        this.stateChangedLisener = lisener;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.card_live_center:
                if (onMenuClickLisener != null) {
                    onMenuClickLisener.onLiveCenterMenuClicked();
                }
                break;
            case R.id.card_live_start:
                if (onMenuClickLisener != null) {
                    onMenuClickLisener.onStartLiveMenuClicked();
                }
                break;
        }
    }

    public interface OnMenuClickLisener {
        void onStartLiveMenuClicked();

        void onLiveCenterMenuClicked();
    }

    public interface OnMenuStateChangedLisener {
        void onExpand();

        void onHide();
    }
}
