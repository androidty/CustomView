package com.ty.customview.customview.view.ringprogressview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.ty.customview.customview.R;
import com.ty.customview.customview.util.Util;

/**
 * Created by ty on 2018/3/12.
 */

public class RingProgressView extends View {
    private RectF mRectF;
    private Paint ringBackPaint;
    private Paint ringProgressPaint;

    private float mWidth, mHeight;
    private float mStrokeWidth = Util.dp2px(getContext(), 10);

    private float mProgress = 0.75f;
    private float dynamicProgress = 0f;

    public RingProgressView(Context context) {
        this(context, null);
    }

    public RingProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RingProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        ringBackPaint = new Paint();
        ringBackPaint.setAntiAlias(true);
        ringBackPaint.setDither(true);
        ringBackPaint.setColor(getResources().getColor(R.color.ringBackColor));
        ringBackPaint.setStyle(Paint.Style.STROKE);
        ringBackPaint.setStrokeWidth(mStrokeWidth);

        ringProgressPaint = new Paint();
        ringProgressPaint.setAntiAlias(true);
        ringProgressPaint.setDither(true);
        ringProgressPaint.setColor(getResources().getColor(R.color.ringProgressColor));
        ringProgressPaint.setStyle(Paint.Style.STROKE);
        ringProgressPaint.setStrokeWidth(mStrokeWidth);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getWidth();
        mHeight = getHeight();
        if (mWidth != mHeight) {
            mWidth = mHeight = Math.min(mWidth, mHeight);
        }
        mRectF = new RectF(mStrokeWidth / 2, mStrokeWidth / 2, mWidth - mStrokeWidth / 2, mHeight -
                mStrokeWidth / 2);
        startAnimation();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawRingBack(canvas);
        drawProgress(canvas);
//        invalidate();
    }


    private void startAnimation() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, mProgress * 360);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                dynamicProgress = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.setDuration(2000);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.start();
    }

    private void drawRingBack(Canvas canvas) {
        canvas.drawArc(mRectF, 0, 360, false, ringBackPaint);
    }

    private void drawProgress(Canvas canvas) {
        canvas.drawArc(mRectF, -90, dynamicProgress, false, ringProgressPaint);
    }
}
