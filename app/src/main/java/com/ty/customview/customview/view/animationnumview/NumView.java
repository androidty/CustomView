package com.ty.customview.customview.view.animationnumview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import java.text.DecimalFormat;

/**
 * Created by ty on 2018/3/14.
 */

public class NumView extends View {
    private final String KEEP_INTEGER = "#,###";
    private DecimalFormat df = new DecimalFormat(KEEP_INTEGER);
    private Paint mPaint;
    private int num = 123;
    private int dynamicInt = 0;

    public NumView(Context context) {
        this(context, null);
    }

    public NumView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(14);
        mPaint.setTextSize(14);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
    }

    private void startAnim() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, num);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                dynamicInt = (int) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.setDuration(2000);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        startAnim();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("ainmdd", "onDraw: " + df.format(dynamicInt).toString());
//        canvas.drawText(dynamicInt+"", 0, 0, mPaint);
        String testString = "测试：gafaeh:1234";
        mPaint.setStrokeWidth(15);
        mPaint.setTextSize(88);
        mPaint.setColor(Color.BLACK);
        mPaint.setTextAlign(Paint.Align.CENTER);
        Rect bounds = new Rect();
        mPaint.getTextBounds(num+"", 0, 3, bounds);
        Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
        int baseline = (getMeasuredHeight() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
        canvas.drawText(dynamicInt+"",getMeasuredWidth() / 2 - bounds.width() / 2, baseline, mPaint);
    }
}
