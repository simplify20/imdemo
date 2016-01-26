package com.example.creact.imdemo.app.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;

import com.example.creact.imdemo.R;

/**
 * 根据简谐运动的质点运动方程，质点x在t时刻的位置f(x) = A*cos(w(t-x/v))
 * Created by Administrator on 2016/1/23.
 */
public class WaveLoadingView2 extends View {
    private static final String TAG = "WaveLoadingView2";
    private static final float mControlPoint = 0.55228475f;//4*(cos45-1)/3
    private static final float SAMPLE_RATE = 1.0f;
    private Paint mWavePaint;
    private Paint mWaveBackPaint;
    private Paint mCirclePaint;
    private Path circlePath;
    private Path cosWavePath;
    private float centerX, centerY;
    private int sampleCount;

    private SparseArray<PointF> points;


    private float waveLength;
    private float T;
    private float A;
    private double w;

    private float offset;
    private float vx = -2.0f;
    private float radius;

    private int mProgress;


    public WaveLoadingView2(Context context) {
        this(context, null);
    }

    public WaveLoadingView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public void setProgress(int progress) {
        this.mProgress = progress;
        if (progress < 0 || progress > 100) {
            throw new IllegalArgumentException("progress should between 0 and 100");
        }
        offset = Float.valueOf(100 - progress) / 100 * getMeasuredHeight();
    }

    public int getProgress() {
        return mProgress;
    }


    public double getPhase() {
        return phase;
    }

    public void setPhase(int angle) {
        this.phase = angle * Math.PI / 180;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int suggestWidth = MeasureSpec.getSize(widthMeasureSpec);
        int suggestHeight = MeasureSpec.getSize(heightMeasureSpec);
        int size = Math.min(suggestHeight, suggestWidth);
        radius = size / 2;
        waveLength = 4 * radius;
        A = radius / 3;
        setMeasuredDimension((int) (radius * 2 + A * 2), (int) (radius * 2 + A * 2));
        centerX = getMeasuredWidth() / 2;
        centerY = getMeasuredHeight() / 2;
        Log.d(TAG, "mWidth:" + centerX * 2 + "mHeight:" + centerY * 2);
        if (offset == 0)
            offset = centerY;//default
        hasCircleInitial = false;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (points != null) {
            points.clear();
            points = null;
        }
    }


    private float t = 0;
    private PointF lastPoint = null;
    private double phase = 30 * Math.PI / 180;
    private boolean hasCircleInitial = false;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //circle
        if (!hasCircleInitial)
            initCirclePath();
        canvas.save();
        canvas.clipPath(circlePath);
        drawWaves(canvas);
        t += 1;
        if (t >= Float.MAX_VALUE) {
            t = 0;
        }
        canvas.restore();
        canvas.drawPath(circlePath, mCirclePaint);
        invalidate();
    }

    private void initCirclePath() {
        T = Math.abs(waveLength / vx);
        w = 2 * Math.PI / T;
        sampleCount = (int) (waveLength / SAMPLE_RATE);
        if (points != null) {
            points.clear();
        } else
            points = new SparseArray<>(sampleCount);
        for (int i = 0; i < sampleCount; i++) {
            points.put(i, new PointF());
        }
        circlePath.moveTo(centerX, centerY + radius);
        circlePath.cubicTo(centerX + radius * mControlPoint, centerY + radius, centerX + radius, centerY + mControlPoint * radius, centerX + radius, centerY);
        circlePath.cubicTo(centerX + radius, centerY - radius * mControlPoint, centerX + radius * mControlPoint, centerY - radius, centerX, centerY - radius);
        circlePath.cubicTo(centerX - radius * mControlPoint, centerY - radius, centerX - radius, centerY - radius * mControlPoint, centerX - radius, centerY);
        circlePath.cubicTo(centerX - radius, centerY + radius * mControlPoint, centerX - radius * mControlPoint, centerY + radius, centerX, centerY + radius);
        hasCircleInitial = true;
    }

    private void drawWaves(Canvas canvas) {
        initWavePath(true);
        canvas.drawPath(cosWavePath, mWaveBackPaint);
        cosWavePath.reset();
        lastPoint = null;
        initWavePath(false);
        canvas.drawPath(cosWavePath, mWavePaint);
        cosWavePath.reset();
        lastPoint = null;
    }

    private void init(Context context, AttributeSet attrs) {
        Resources resources = getResources();
        mWavePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mWavePaint.setStrokeCap(Paint.Cap.ROUND);
        mWavePaint.setStrokeJoin(Paint.Join.ROUND);
        mWavePaint.setColor(resources.getColor(R.color.color_water_light_blue));
        mWavePaint.setStyle(Paint.Style.FILL);
        mWavePaint.setStrokeWidth(4);

        mWaveBackPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mWaveBackPaint.setStrokeCap(Paint.Cap.ROUND);
        mWaveBackPaint.setStrokeJoin(Paint.Join.ROUND);
        mWaveBackPaint.setColor(resources.getColor(R.color.color_water_blue));
        mWaveBackPaint.setStyle(Paint.Style.FILL);
        mWaveBackPaint.setStrokeWidth(4);

        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setStrokeCap(Paint.Cap.ROUND);
        mCirclePaint.setStrokeJoin(Paint.Join.ROUND);
        mCirclePaint.setColor(resources.getColor(R.color.color_water_light_blue));
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStrokeWidth(4);

        //circle
        circlePath = new Path();

        //余弦曲线
        cosWavePath = new Path();


        waveLength = resources.getDimensionPixelSize(R.dimen.wave_period);
        radius = waveLength / 3;
        A = radius / 3;

    }

    private void initWavePath(boolean isBack) {
        //sin wave
        double ph = isBack ? phase : 0;
        for (int i = 0; i < sampleCount; i++) {
            PointF pointF = points.get(i);
            pointF.set(i * SAMPLE_RATE, (float) (offset + A * Math.cos(ph + w * (t - i * SAMPLE_RATE / vx))));
            if (lastPoint == null) {
                cosWavePath.moveTo(pointF.x, pointF.y);
            } else {
                cosWavePath.quadTo((lastPoint.x + pointF.x) / 2, (lastPoint.y + pointF.y) / 2, pointF.x, pointF.y);
            }
            lastPoint = pointF;
        }

        PointF first = points.get(0);
        PointF last = points.get(sampleCount - 1);
        cosWavePath.lineTo(last.x, centerY + radius + A);
        cosWavePath.lineTo(first.x, centerY + radius + A);
        cosWavePath.lineTo(first.x, first.y);
    }

}
