package com.example.creact.imdemo.app.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.example.creact.imdemo.R;

/**
 * Created by Administrator on 2016/1/23.
 */
public class WaveLoadingView extends View {
    private Paint mWavePaint;
    private Paint mCirclePaint;
    private static final float mControlPoint = 0.55228475f;//4*(cos45-1)/3
    private Path circlePath;
    private Path foregroundWavePath1;
    private Path foregroundWavePath2;
    private float centerX, centerY;

    private float T;
    private float A;
    /**
     * progress=0,offset = height+A,progress = 100,offset = -A
     * offset = progress/100*(height+2*A)
     */
    private float offset;
    private float vx = 1.5f;
    private float currentX = 0;
    private int width, height;
    private float radius;

    private int mProgress;
    private int mPadding;

    public WaveLoadingView(Context context) {
        this(context, null);
    }

    public WaveLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public void setProgress(int progress) {
        this.mProgress = progress;
        if (progress < 0 || progress > 100) {
            throw new IllegalArgumentException("progress should between 0 and 100");
        }
//        offset = Float.valueOf(progress) / 100.0f * (height + 2 * A);
//        invalidate();
    }

    public int getProgress() {
        return mProgress;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int suggestWidth = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int suggestHeight = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

//        boolean isHeightExactly = false, isWidthExactly = false;
//        if (widthMode == MeasureSpec.EXACTLY) {
//            width = suggestWidth;
//            isWidthExactly = true;
//        }
//        if (heightMode == MeasureSpec.EXACTLY) {
//            height = suggestHeight;
//            isHeightExactly = true;
//        }
//        if (isWidthExactly && !isHeightExactly) {
//            T = width;
//            radius = T / 2;
//            height = width;
//        } else if (isHeightExactly && !isWidthExactly) {
//            //// FIXME: 2016/1/23 height:math_parent
//            width = height;
//            T = width;
//            radius = T / 2;
//        } else if (isHeightExactly && isWidthExactly) {
//            if (width < height) {
//                width = height;
//                T = width;
//                radius = T / 2;
//            } else {
//                height = width;
//                T = height;
//                radius = T / 2;
//            }
//        }
        if (offset == 0)
            offset = (T + mPadding * 2) / 2;//default
        setMeasuredDimension((int) T + mPadding * 2, (int) T + mPadding * 2);

    }

    private void init(Context context, AttributeSet attrs) {
        Resources resources = getResources();
        mWavePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mWavePaint.setStrokeCap(Paint.Cap.ROUND);
        mWavePaint.setStrokeJoin(Paint.Join.ROUND);
        mWavePaint.setColor(resources.getColor(R.color.color_water_light_blue));
        mWavePaint.setStyle(Paint.Style.FILL);
        mWavePaint.setStrokeWidth(4);

        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setStrokeCap(Paint.Cap.ROUND);
        mCirclePaint.setStrokeJoin(Paint.Join.ROUND);
        mCirclePaint.setColor(resources.getColor(R.color.color_water_light_blue));
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStrokeWidth(4);

        //circle
        circlePath = new Path();

        //余弦曲线
        foregroundWavePath1 = new Path();

        foregroundWavePath2 = new Path();

        T = resources.getDimensionPixelSize(R.dimen.wave_period);
        radius = T / 2;
        A = radius / 3;
        mPadding = resources.getDimensionPixelSize(R.dimen.wave_loading_padding);
    }

    private boolean isInitial = false;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isInitial) {
            centerX = getMeasuredWidth() / 2;
            centerY = getMeasuredHeight() / 2;
            isInitial = true;
            currentX = centerX - radius;
        }
        //circle
        circlePath.moveTo(centerX, centerY + radius);
        circlePath.cubicTo(centerX + radius * mControlPoint, centerY + radius, centerX + radius, centerY + mControlPoint * radius, centerX + radius, centerY);
        circlePath.cubicTo(centerX + radius, centerY - radius * mControlPoint, centerX + radius * mControlPoint, centerY - radius, centerX, centerY - radius);
        circlePath.cubicTo(centerX - radius * mControlPoint, centerY - radius, centerX - radius, centerY - radius * mControlPoint, centerX - radius, centerY);
        circlePath.cubicTo(centerX - radius, centerY + radius * mControlPoint, centerX - radius * mControlPoint, centerY + radius, centerX, centerY + radius);
        canvas.save();
        canvas.clipPath(circlePath);


        //wave
        foregroundWavePath1.moveTo(currentX, offset);
        foregroundWavePath1.quadTo(currentX + T / 4, offset - A * 1.5f, currentX + T / 2, offset);
        foregroundWavePath1.quadTo(currentX + 3 * T / 4, offset + A * 1.5f, currentX + T, offset);
        foregroundWavePath1.lineTo(currentX + T, centerY + radius);
        foregroundWavePath1.lineTo(currentX, centerY + radius);
        foregroundWavePath1.lineTo(currentX, offset);

        foregroundWavePath2.moveTo(currentX - T, offset);
        foregroundWavePath2.quadTo(currentX - 3 * T / 4, offset - A * 1.5f, currentX - T / 2, offset);
        foregroundWavePath2.quadTo(currentX - T / 4, offset + A * 1.5f, currentX, offset);
        foregroundWavePath2.lineTo(currentX, centerY + radius);
        foregroundWavePath2.lineTo(currentX - T, centerY + radius);
        foregroundWavePath2.lineTo(currentX - T, offset);

        canvas.drawPath(foregroundWavePath1, mWavePaint);
        canvas.drawPath(foregroundWavePath2, mWavePaint);
        foregroundWavePath1.reset();
        foregroundWavePath2.reset();
        currentX += vx;
        if (currentX >= centerX + radius) {
            currentX = centerX - radius;
        }
        canvas.restore();
        canvas.drawPath(circlePath, mCirclePaint);
        circlePath.reset();
        invalidate();
    }
}
