package com.ty.customview.customview.view.bezierview;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by ty on 2018/3/9.
 */

public class BezierView extends View {

    private Paint mPointPaint;
    private Paint mLinePaint, mControlLinePaint;
    private Paint mCurvePaint;

    private PointF startDot, endDot;
    private PointF controlDot1, controlDot2;
    private PointF transitionDot1, transitionDot2, transitionDot3;
    private PointF pathDot1;
    private float mWidth;
    private float mHeight;

    private int mMeasureWidth;
    private int mWidthMode;
    private int mMeasureHeight;
    private int mHeightMode;

    private float dotRadius = 15f;
    private Path mPath;


    public BezierView(Context context) {
        this(context, null);
    }

    public BezierView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BezierView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        mHeightMode = MeasureSpec.getMode(heightMeasureSpec);
        mMeasureWidth = MeasureSpec.getSize(widthMeasureSpec);
        mMeasureHeight = MeasureSpec.getSize(heightMeasureSpec);
        if (mWidthMode == MeasureSpec.AT_MOST && mHeightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(200, 200);
        } else if (mWidthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(200, mMeasureHeight);
        } else if (mHeightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(mMeasureWidth, 200);
        } else {
            setMeasuredDimension(mMeasureWidth, mMeasureHeight);
        }
//        mWidth = getWidth();
//        mHeight = getHeight();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        initDot();
    }

    private void initDot() {
        startDot = new PointF(mWidth / 8, mHeight / 5 * 3);
        endDot = new PointF(mWidth / 8 * 7, mHeight / 5 * 3);
        controlDot1 = new PointF(mWidth / 3, mHeight / 5);

        transitionDot1 = new PointF(startDot.x, startDot.y);
        transitionDot2 = new PointF(controlDot1.x, controlDot1.y);
        transitionDot3 = new PointF();

        pathDot1 = new PointF(startDot.x, startDot.y);

        mPath = new Path();
        mPath.moveTo(startDot.x, startDot.y);

        drawWithAnimation();
        anim();

    }

    private void init() {
        mPointPaint = new Paint();
        mPointPaint.setColor(Color.BLUE);
        mPointPaint.setAntiAlias(true);
        mPointPaint.setDither(true);
        mPointPaint.setStyle(Paint.Style.FILL);

        mCurvePaint = new Paint();
        mCurvePaint.setColor(Color.RED);
        mCurvePaint.setDither(true);
        mCurvePaint.setAntiAlias(true);
        mCurvePaint.setStyle(Paint.Style.STROKE);
        mCurvePaint.setStrokeWidth(8f);

        mLinePaint = new Paint();
        mLinePaint.setDither(true);
        mLinePaint.setAntiAlias(true);
        mLinePaint.setColor(Color.GRAY);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(10f);

        mControlLinePaint = new Paint();
        mControlLinePaint.setStyle(Paint.Style.STROKE);
        mControlLinePaint.setDither(true);
        mControlLinePaint.setAntiAlias(true);
        mControlLinePaint.setColor(Color.GREEN);
        mControlLinePaint.setStrokeWidth(5f);

    }


    private void drawWithAnimation() {
        ValueAnimator valueStartX = ValueAnimator.ofFloat(startDot.x, controlDot1.x);
        valueStartX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                transitionDot1.x = (float) valueAnimator.getAnimatedValue();
            }
        });
        ValueAnimator valueStartY = ValueAnimator.ofFloat(startDot.y, controlDot1.y);
        valueStartY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                transitionDot1.y = (float) valueAnimator.getAnimatedValue();
            }
        });
        Log.d("drawwith", "drawWithAnimation: " + controlDot1.toString() + " " + endDot.toString());

        ValueAnimator valueEndX = ValueAnimator.ofFloat(controlDot1.x, endDot.x);
        valueEndX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                transitionDot2.x = (float) valueAnimator.getAnimatedValue();
            }
        });
        ValueAnimator valueEndY = ValueAnimator.ofFloat(controlDot1.y, endDot.y);
        valueEndY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                transitionDot2.y = (float) valueAnimator.getAnimatedValue();
            }
        });


        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(5000);
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.play(valueEndX).with(valueEndY).with(valueStartX).with(valueStartY);
        animatorSet.start();
    }


    private void anim() {
        ValueAnimator valuePathX = ValueAnimator.ofFloat(0, 1);
        valuePathX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                pathDot1.x = (float) valueAnimator.getAnimatedValue() * (transitionDot2.x -
                        transitionDot1.x) * 2
                        + startDot.x;
            }
        });
        ValueAnimator valuePathY = ValueAnimator.ofFloat(0, 1);
        valuePathY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                if (transitionDot1.y > transitionDot2.y) {
                    pathDot1.y = (1-(float) valueAnimator.getAnimatedValue())*
                            (transitionDot1.y-transitionDot2.y)+transitionDot2.y;
                } else if (transitionDot1.y < transitionDot2.y) {
                    pathDot1.y = (float) valueAnimator.getAnimatedValue() * (transitionDot2.y -
                            transitionDot1.y)
                            + transitionDot1.y;
                } else {
                    pathDot1.y = transitionDot1.y;
                }
            }
        });
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(5000);
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.play(valuePathX).with(valuePathY);
        animatorSet.start();

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawLine(canvas);
        drawDot(canvas);
        drawControlLine(canvas);
        invalidate();
    }

    private void drawControlLine(Canvas canvas) {
        canvas.drawLine(transitionDot1.x, transitionDot1.y, transitionDot2.x, transitionDot2.y,
                mControlLinePaint);
        canvas.drawCircle(transitionDot1.x, transitionDot1.y, 20, mPointPaint);
        canvas.drawCircle(transitionDot2.x, transitionDot2.y, 20, mPointPaint);
        mPath.lineTo(pathDot1.x, pathDot1.y);
        canvas.drawCircle(pathDot1.x, pathDot1.y, 20, mCurvePaint);
//        anim();
        canvas.drawPath(mPath, mCurvePaint);
    }

    private void drawLine(Canvas canvas) {
        canvas.drawLine(startDot.x, startDot.y, controlDot1.x, controlDot1.y, mLinePaint);
        canvas.drawLine(endDot.x, endDot.y, controlDot1.x, controlDot1.y, mLinePaint);
    }

    private void drawDot(Canvas canvas) {
        canvas.drawCircle(startDot.x, startDot.y, dotRadius, mPointPaint);
        canvas.drawCircle(endDot.x, endDot.y, dotRadius, mPointPaint);
        canvas.drawCircle(controlDot1.x, controlDot1.y, dotRadius, mPointPaint);
    }
}
