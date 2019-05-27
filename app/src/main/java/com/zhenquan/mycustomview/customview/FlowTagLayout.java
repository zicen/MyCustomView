package com.zhenquan.mycustomview.customview;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 实现思路：主要看 onMeasure 以及 onLayout
 * onMeasure:
 * 1.获取父控件为 子View设置的测量模式和大小
 * 2.遍历所有子View,然后测量其宽高，当当前行超过父容器给的宽度就换行
 * 3.根据测量模式和最终测量的子View 宽高设置当前子View的大小
 * setMeasuredDimension(modeWidth == View.MeasureSpec.EXACTLY ? sizeWidth : resultWidth,
 *                     modeHeight == View.MeasureSpec.EXACTLY ? sizeHeight : resultHeight);
 *
 *  onLayout：
 *   1. 遍历子控件，记录每个子view的位置，获取测量后的子 View 宽高
 *   2. 根据子View的宽高以及 margin 对子View 进行布局
 *    int left = childLeft + mlp.leftMargin;
 *    int top = childTop + mlp.topMargin;
 *    int right = childLeft + mlp.leftMargin + childWidth;
 *    int bottom = childTop + mlp.topMargin + childHeight;
 *    childView.layout(left, top, right, bottom);
 *
 */
public class FlowTagLayout extends ViewGroup {
    private static final String TAG = FlowTagLayout.class.getSimpleName();
    private int isFirstSelect = 1;//1默认第一个itemselected = true;
    private int singleCheckIndex = -1;

    /**
     * FlowLayout not support checked
     */
    public static final int FLOW_TAG_CHECKED_NONE = 0;
    /**
     * FlowLayout support single-select
     */
    public static final int FLOW_TAG_CHECKED_SINGLE = 1;
    /**
     * FlowLayout support multi-select
     */
    public static final int FLOW_TAG_CHECKED_MULTI = 2;

    /**
     * Should be used by subclasses to listen to changes in the dataset
     */
    private AdapterDataSetObserver mDataSetObserver;

    /**
     * The adapter containing the data to be displayed by this view
     */
    private ListAdapter mAdapter;

    /**
     * the tag click event callback
     */
    private OnTagClickListener mOnTagClickListener;

    /**
     * the tag select event callback
     */
    private OnTagSelectListener mOnTagSelectListener;

    /**
     * 标签流式布局选中模式，默认是不支持选中的
     */
    private int mTagCheckMode = FLOW_TAG_CHECKED_NONE;

    /**
     * 存储选中的tag
     */
    private SparseBooleanArray mCheckedTagArray = new SparseBooleanArray();

    public FlowTagLayout(Context context) {
        super(context);
    }

    public FlowTagLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowTagLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //获取Padding
        // 获得它的父容器为它设置的测量模式和大小
        int sizeWidth = View.MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = View.MeasureSpec.getSize(heightMeasureSpec);
        int modeWidth = View.MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = View.MeasureSpec.getMode(heightMeasureSpec);

        //FlowLayout最终的宽度和高度值
        int resultWidth = 0;
        int resultHeight = 0;

        //测量时每一行的宽度
        int lineWidth = 0;
        //测量时每一行的高度，加起来就是FlowLayout的高度
        int lineHeight = 0;

        //遍历每个子元素
        for (int i = 0, childCount = getChildCount(); i < childCount; i++) {
            View childView = getChildAt(i);
            //测量每一个子view的宽和高
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);

            //获取到测量的宽和高
            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();

            //因为子View可能设置margin，这里要加上margin的距离
            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) childView.getLayoutParams();
            int realChildWidth = childWidth + mlp.leftMargin + mlp.rightMargin;
            int realChildHeight = childHeight + mlp.topMargin + mlp.bottomMargin;

            //如果当前一行的宽度加上要加入的子view的宽度大于父容器给的宽度，就换行
            if ((lineWidth + realChildWidth) > sizeWidth) {
                //换行
                resultWidth = Math.max(lineWidth, realChildWidth);
                resultHeight += realChildHeight;
                //换行了，lineWidth和lineHeight重新算
                lineWidth = realChildWidth;
                lineHeight = realChildHeight;
            } else {
                //不换行，直接相加
                lineWidth += realChildWidth;
                //每一行的高度取二者最大值
                lineHeight = Math.max(lineHeight, realChildHeight);
            }

            //遍历到最后一个的时候，肯定走的是不换行
            if (i == childCount - 1) {
                resultWidth = Math.max(lineWidth, resultWidth);
                resultHeight += lineHeight;
            }

            setMeasuredDimension(modeWidth == View.MeasureSpec.EXACTLY ? sizeWidth : resultWidth,
                    modeHeight == View.MeasureSpec.EXACTLY ? sizeHeight : resultHeight);

        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int flowWidth = getWidth();

        int childLeft = 0;
        int childTop = 0;

        //遍历子控件，记录每个子view的位置
        for (int i = 0, childCount = getChildCount(); i < childCount; i++) {
            View childView = getChildAt(i);

            //跳过View.GONE的子View
            if (childView.getVisibility() == View.GONE) {
                continue;
            }

            //获取到测量的宽和高
            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();

            //因为子View可能设置margin，这里要加上margin的距离
            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) childView.getLayoutParams();

            if (childLeft + mlp.leftMargin + childWidth + mlp.rightMargin > flowWidth) {
                //换行处理
                childTop += (mlp.topMargin + childHeight + mlp.bottomMargin);
                childLeft = 0;
            }
            //布局
            int left = childLeft + mlp.leftMargin;
            int top = childTop + mlp.topMargin;
            int right = childLeft + mlp.leftMargin + childWidth;
            int bottom = childTop + mlp.topMargin + childHeight;
            childView.layout(left, top, right, bottom);

            childLeft += (mlp.leftMargin + childWidth + mlp.rightMargin);
        }
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new ViewGroup.MarginLayoutParams(getContext(), attrs);
    }

    public ListAdapter getAdapter() {
        return mAdapter;
    }

    class AdapterDataSetObserver extends DataSetObserver {
        @Override
        public void onChanged() {
            super.onChanged();
            reloadData();
        }

        @Override
        public void onInvalidated() {
            super.onInvalidated();
        }
    }


    /**
     * 重新加载刷新数据
     */
    private void reloadData() {
        removeAllViews();

        for (int i = 0; i < mAdapter.getCount(); i++) {
            final int j = i;
            mCheckedTagArray.put(i, false);
            final View childView = mAdapter.getView(i, null, this);
            if (childView == null) {
                continue;
            }
            addView(childView, new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            final int finalI = i;

//            if (i == 0) {
//                if (isFirstSelect == 1) {
//                    mCheckedTagArray.put(i, true);
//                    childView.setSelected(true);
//                }
//            }
            if (isFirstSelect > 0) {
                mCheckedTagArray.put(isFirstSelect - 1, true);
                childView.setSelected(true);
            }
            if (mCheckedTagArray.size() > i) {
                boolean chooseValue = mCheckedTagArray.get(i);
                childView.setSelected(chooseValue);
            }

            if (mTagCheckMode == FLOW_TAG_CHECKED_SINGLE && singleCheckIndex == i) {
                childView.setSelected(true);
            }

            childView.setOnClickListener(v -> {
                if (mTagCheckMode == FLOW_TAG_CHECKED_NONE) {
                    if (mOnTagClickListener != null) {
                        mOnTagClickListener.onItemClick(FlowTagLayout.this, childView, j);
                    }
                } else if (mTagCheckMode == FLOW_TAG_CHECKED_SINGLE) {
                    //判断状态
                    if (mCheckedTagArray.get(j)) {
                        mCheckedTagArray.put(j, false);
                        childView.setSelected(false);
                        if (mOnTagSelectListener != null) {
                            mOnTagSelectListener.onItemSelect(FlowTagLayout.this, new ArrayList<>());
                        }
                        return;
                    }

                    for (int k = 0; k < mAdapter.getCount(); k++) {
                        mCheckedTagArray.put(k, false);
                        getChildAt(k).setSelected(false);
                    }
                    mCheckedTagArray.put(j, true);
                    childView.setSelected(true);

                    if (mOnTagSelectListener != null) {
                        mOnTagSelectListener.onItemSelect(FlowTagLayout.this, Arrays.asList(j));
                    }
                } else if (mTagCheckMode == FLOW_TAG_CHECKED_MULTI) {
                    if (j == 0) {
                        if (!mCheckedTagArray.get(j)) {
                            for (int i1 = 0; i1 < mAdapter.getCount(); i1++) {
                                mCheckedTagArray.put(i1, false);
                                getChildAt(i1).setSelected(false);
                            }
                            mCheckedTagArray.put(j, true);
                            getChildAt(j).setSelected(true);
                        } else {
                            mCheckedTagArray.put(j, false);
                            getChildAt(j).setSelected(false);
                        }
                        if (mOnTagSelectListener != null) {
                            List<Integer> list = new ArrayList<>();
                            list.add(0);
                            mOnTagSelectListener.onItemSelect(FlowTagLayout.this, list);
                        }
                        return;
                    } else {
                        mCheckedTagArray.put(0, false);
                        getChildAt(0).setSelected(false);
                        if (mCheckedTagArray.get(j)) {
                            mCheckedTagArray.put(j, false);
                            childView.setSelected(false);
                        } else {
                            mCheckedTagArray.put(j, true);
                            childView.setSelected(true);
                        }
                    }

                    //回调
                    if (mOnTagSelectListener != null) {
                        List<Integer> list = new ArrayList<>();
                        for (int k = 0; k < mAdapter.getCount(); k++) {
                            if (mCheckedTagArray.get(k)) {
                                list.add(k);
                            }
                        }
                        mOnTagSelectListener.onItemSelect(FlowTagLayout.this, list);
                    }
                }
            });
        }
    }

    public void setOnTagClickListener(OnTagClickListener onTagClickListener) {
        this.mOnTagClickListener = onTagClickListener;
    }

    public void setOnTagSelectListener(OnTagSelectListener onTagSelectListener) {
        this.mOnTagSelectListener = onTagSelectListener;
    }

    /**
     * 像ListView、GridView一样使用FlowLayout
     *
     * @param adapter
     */
    public void setAdapter(ListAdapter adapter) {
        if (mAdapter != null && mDataSetObserver != null) {
            mAdapter.unregisterDataSetObserver(mDataSetObserver);
        }

        //清除现有的数据
        removeAllViews();
        mAdapter = adapter;

        if (mAdapter != null) {
            mDataSetObserver = new AdapterDataSetObserver();
            mAdapter.registerDataSetObserver(mDataSetObserver);
        }
    }

    /**
     * 获取标签模式
     *
     * @return
     */
    public int getmTagCheckMode() {
        return mTagCheckMode;
    }

    /**
     * 设置标签选中模式
     *
     * @param tagMode
     */
    public void setTagCheckedMode(int tagMode) {
        this.mTagCheckMode = tagMode;
    }

    public void setSingleCheckIndex(int index) {
        this.singleCheckIndex = index;
        reloadData();
    }

    public void setIsFirstSelect(int flag) {
        isFirstSelect = flag;
    }

    public interface OnTagClickListener {
        void onItemClick(FlowTagLayout parent, View view, int position);
    }

    public interface OnTagSelectListener {
        void onItemSelect(FlowTagLayout parent, List<Integer> selectedList);
    }

    public void setChooseData(SparseBooleanArray array) {
        mCheckedTagArray.clear();
        for (int index = 0; index < array.size(); index++) {
            mCheckedTagArray.put(index, array.get(index));
        }
        reloadData();
    }

    public void setChooseData(List<Integer> selected) {
        int size = mCheckedTagArray.size();
        if (selected == null || selected.size() == 0) {
            reloadData();
            return;
        }
        for (int index = 0; index < size; index++) {
            mCheckedTagArray.put(index, selected.contains(index));
        }
        removeAllViews();

        for (int i = 0; i < mAdapter.getCount(); i++) {
            final int j = i;
            final View childView = mAdapter.getView(i, null, this);
            if (childView == null) {
                continue;
            }
            addView(childView, new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            if (mCheckedTagArray.size() > i) {
                boolean chooseValue = mCheckedTagArray.get(i);
                childView.setSelected(chooseValue);
            }

            childView.setSelected(selected.contains(i));

            childView.setOnClickListener(v -> {
                if (mTagCheckMode == FLOW_TAG_CHECKED_NONE) {
                    if (mOnTagClickListener != null) {
                        mOnTagClickListener.onItemClick(FlowTagLayout.this, childView, j);
                    }
                } else if (mTagCheckMode == FLOW_TAG_CHECKED_SINGLE) {
                    boolean selectedResult = !mCheckedTagArray.get(j);
                    int selectedIndex = selectedResult ? j : 0;
                    for (int k = 0; k < mAdapter.getCount(); k++) {
                        if (selectedResult) {
                            mCheckedTagArray.put(k, k == j);
                            getChildAt(k).setSelected(k == j);
                        } else {
                            mCheckedTagArray.put(k, k == 0);
                            getChildAt(k).setSelected(k == 0);
                        }
                    }

                    if (mOnTagSelectListener != null) {
                        mOnTagSelectListener.onItemSelect(FlowTagLayout.this, Arrays.asList(selectedIndex));
                    }

                } else if (mTagCheckMode == FLOW_TAG_CHECKED_MULTI) {
                    boolean select = !mCheckedTagArray.get(j);

                    if (j == 0) {
                        for (int k = 0; k < mAdapter.getCount(); k++) {
                            mCheckedTagArray.put(k, k == 0);
                            getChildAt(k).setSelected(k == 0);
                        }
                    } else {
                        if (select) {
                            mCheckedTagArray.put(0, false);
                            mCheckedTagArray.put(j, true);
                            for (int k = 0; k < mAdapter.getCount(); k++) {
                                getChildAt(k).setSelected(mCheckedTagArray.get(k));
                            }
                        } else {
                            mCheckedTagArray.put(j, false);
                            boolean reset = true;
                            for (int k = 1; k < mAdapter.getCount(); k++) {
                                boolean sel = mCheckedTagArray.get(k);
                                getChildAt(k).setSelected(sel);
                                if (sel) {
                                    reset = false;
                                }
                            }
                            getChildAt(0).setSelected(reset);
                        }
                    }

                    //回调
                    if (mOnTagSelectListener != null) {
                        List<Integer> list = new ArrayList<>();
                        for (int k = 0; k < mAdapter.getCount(); k++) {
                            if (mCheckedTagArray.get(k)) {
                                list.add(k);
                            }
                        }
                        mOnTagSelectListener.onItemSelect(FlowTagLayout.this, list);
                    }
                }
            });
        }
    }

    public SparseBooleanArray getCheckedTagArray() {
        return mCheckedTagArray;
    }

    public void removeData() {
        reloadData();
    }
}