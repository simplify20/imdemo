/**
 * Copyright 2014 Travo, Inc. All rights reserved.
 * TagTourLinearLayout.java
 */
package com.example.creact.imdemo.app.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.creact.imdemo.R;

import java.util.ArrayList;
import java.util.List;

public class TagLayout extends ViewGroup {
    private static final String TAG = "TagLayout";

    private static int DEFAULT_VER_MARGIN = 5;
    private static int DEFAULT_HOR_MARGIN = 5;
    private static int DEFAULT_TEXT_SIZE = 15;
    private static int DEFAULT_TEXT_PADDING = 5;
    private static int DEFAULT_TEXT_BACKGROUND = R.drawable.tag_layout_background;
    private static int DEFAULT_TEXT_COLOR = Color.BLACK;
    /**
     * data set
     */
    private List<String> mTags;
    private int mVerticalMargin;
    private int mHorMargin;
    private int mTextSize;
    private Rect mTextPadding;
    private Drawable mTextBackground;
    private int mTextColor;

    private SparseArray<List<TextView>> mTextViews = new SparseArray<>();

    private OnItemClickListener mOnItemClickListener;

    /**
     * click callback
     */
    public interface OnItemClickListener {
        void onItemClicked(int position, TextView textView);
    }

    public TagLayout(Context context) {
        this(context, null);
    }

    public TagLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    public List<String> getTags() {
        return mTags;
    }

    public void setTags(List<String> tags) {
        this.mTags = tags;
        this.removeAllViews();
        initViews();
    }

    public OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }


    private void initViews() {
        for (int i = 0; i < mTags.size(); i++) {
            final TextView tagView = new TextView(getContext());
            tagView.setText(mTags.get(i));
            tagView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
            tagView.setPadding(mTextPadding.left, mTextPadding.top, mTextPadding.right, mTextPadding.bottom);
            tagView.setBackground(mTextBackground);
            tagView.setTextColor(mTextColor);
            MarginLayoutParams layoutParams = new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            tagView.setLayoutParams(layoutParams);
            final int pos = i;
            tagView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClicked(pos, tagView);
                }
            });
            addView(tagView);
        }
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {

        TypedArray array = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.TagLayout,
                defStyleAttr,
                defStyleAttr
        );
        mTextColor = array.getColor(R.styleable.TagLayout_tag_text_color, DEFAULT_TEXT_COLOR);
        mTextSize = array.getDimensionPixelSize(R.styleable.TagLayout_tag_text_size, DEFAULT_TEXT_SIZE);
        int padding = array.getDimensionPixelSize(R.styleable.TagLayout_tag_padding, DEFAULT_TEXT_PADDING);
        mTextPadding = new Rect(padding, padding, padding, padding);
        mTextBackground = array.getDrawable(R.styleable.TagLayout_tag_background);
        if (mTextBackground == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                mTextBackground = getResources().getDrawable(DEFAULT_TEXT_BACKGROUND, context.getTheme());
            else
                mTextBackground = getResources().getDrawable(DEFAULT_TEXT_BACKGROUND);
        }
        mHorMargin = array.getDimensionPixelSize(R.styleable.TagLayout_tag_hor_margin, DEFAULT_HOR_MARGIN);
        mVerticalMargin = array.getDimensionPixelSize(R.styleable.TagLayout_tag_ver_margin, DEFAULT_VER_MARGIN);

        array.recycle();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int lineCount = mTextViews.size();
        int top;
        int viewHeight = 0;
        for (int i = 1; i <= lineCount; i++) {
            List<TextView> textViews = mTextViews.get(i);
            if (textViews == null || textViews.size() == 0)
                return;
            int left = getPaddingLeft();
            if (viewHeight == 0)
                viewHeight = textViews.get(0).getMeasuredHeight();
            top = i == 1 ? getPaddingTop() : (i - 1) * (viewHeight + mVerticalMargin) + getPaddingTop();
            for (int j = 0, size = textViews.size(); j < size; j++) {
                TextView textView = textViews.get(j);
                textView.layout(left, top, left + textView.getMeasuredWidth(), top + textView.getMeasuredHeight());
//                    Log.i(TAG, "l:" + left + " t:" + top + " r:" + (left + textView.getMeasuredWidth()) + " b:" + (top + textView.getMeasuredHeight()));
                left += (textView.getMeasuredWidth() + mHorMargin);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //clear all views
        mTextViews.clear();
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);

        TextView firstTextView = (TextView) getChildAt(0);
        if (firstTextView == null) {
            setMeasuredDimension(width, height);
            return;
        }
        //measure children
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        List<TextView> textViews = new ArrayList<>();
        int totalHeight, size = getChildCount(), lineCount = 1, sumWidthOfLine = 0;
        for (int i = 0; i < size; i++) {
            TextView textView = (TextView) getChildAt(i);
            int textViewWidth = textView.getMeasuredWidth();
            sumWidthOfLine += textViewWidth;
            if (sumWidthOfLine < width) {
                textViews.add(textView);
                sumWidthOfLine += mHorMargin;
            } else {
                mTextViews.put(lineCount, new ArrayList(textViews));
                lineCount++;
                sumWidthOfLine = textViewWidth + mHorMargin;
                textViews.clear();
                textViews.add(textView);
            }
        }
        if (lineCount > mTextViews.size()) {
            mTextViews.put(lineCount, new ArrayList(textViews));
        }
        totalHeight = firstTextView.getMeasuredHeight() * lineCount + mVerticalMargin * (lineCount - 1) + getPaddingBottom() + getPaddingTop();
        setMeasuredDimension(width, heightMode == MeasureSpec.EXACTLY ? height : totalHeight);
    }
}

