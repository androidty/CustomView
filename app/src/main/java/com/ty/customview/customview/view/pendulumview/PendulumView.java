package com.ty.customview.customview.view.pendulumview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.ty.customview.customview.R;


/**
 * Created by ty on 2017/12/9.
 */

public class PendulumView extends View {
    //线束上面的圆心点横纵坐标
    private float dotX, dotY;
    private float ballRadius;
    //画球的画笔
    private Paint ballPaint;
    //画线的画笔
    private Paint linePaint;

    //小球数量
    private int totalNums;
    //小球数量是否是偶数
    private boolean isEvenNo;
    private int minNo, maxNo;

    private int time = 300;

    private float lineLength;

    private float firstBallX, firstBallY, lastBallX, lastBallY;

    //刚进去时候 判断是否要画first或者last小球的初始位置
    private boolean flag;
    private float amplitude;
    private float mRadians;
    //初始角度是90度
    private float initialRadians = (float) (Math.PI / 2);


    public PendulumView(Context context) {
        this(context, null);
    }

    public PendulumView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PendulumView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PendulumView);
        totalNums = typedArray.getInteger(R.styleable.PendulumView_totalNum, 5);
        if (totalNums < 3) {
            totalNums = 5;
        }
        int ballColor = typedArray.getColor(R.styleable.PendulumView_ballColor, Color.BLACK);
        int lineColor = typedArray.getColor(R.styleable.PendulumView_lineColor, Color.BLACK);
        ballRadius = typedArray.getFloat(R.styleable.PendulumView_ballRadius, 22f);
        amplitude = typedArray.getFloat(R.styleable.PendulumView_amplitude, 45);
        typedArray.recycle();

        setMinMaxNo();

        ballPaint = new Paint();
        ballPaint.setAntiAlias(true);
        ballPaint.setDither(true);
        ballPaint.setStyle(Paint.Style.FILL);
        ballPaint.setColor(ballColor);
        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setDither(true);
        linePaint.setStyle(Paint.Style.FILL);
        linePaint.setColor(lineColor);
        linePaint.setStrokeWidth(3);

        mRadians = (float) (amplitude / 180 * Math.PI);
    }

    private void setMinMaxNo() {
        if (totalNums % 2 == 0) {
            //todo
            isEvenNo = true;
            maxNo = totalNums / 2;
            minNo = -totalNums / 2;
        } else {
            isEvenNo = false;
            maxNo = (int) Math.floor(totalNums / 2);
            minNo = -maxNo;
        }
    }

    /**
     * 摆动角度越来越小
     */
    private void radiansGradient() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat((float) (amplitude / 180 * Math.PI), 0);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mRadians = (float) valueAnimator.getAnimatedValue();
            }
        });

        valueAnimator.setDuration(1000 * 60 * 10);
        valueAnimator.start();
    }


    /**
     * 第一个小球摆动的动画
     */
    private void firstBallAnim() {
        if (mRadians == 0) {
            return;
        }
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(initialRadians, initialRadians + mRadians);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float radian = (float) valueAnimator.getAnimatedValue();
                firstBallX = (float) (lineLength * Math.cos(radian)) + dotX;
                firstBallY = (float) (lineLength * Math.sin(radian)) + dotY;
            }
        });

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(time);
        animatorSet.setInterpolator(new DecelerateInterpolator());
//        valueAnimator.start();
        valueAnimator.setRepeatCount(1);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        animatorSet.play(valueAnimator);

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                flag = true;
                lastBallAnim();
            }
        });
        animatorSet.start();
    }


    /**
     * 最后一个小球摆动的动画
     */
    private void lastBallAnim() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(initialRadians, initialRadians - mRadians);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float radian = (float) valueAnimator.getAnimatedValue();

                lastBallX = (float) (lineLength * Math.cos(radian)) + dotX;
                lastBallY = (float) (lineLength * Math.sin(radian)) + dotY;

            }
        });
        valueAnimator.setDuration(time);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
//        valueAnimator.start();
        valueAnimator.setRepeatCount(1);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(valueAnimator);
        animatorSet.start();
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                flag = false;
                firstBallAnim();
            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d("firstX", "onSizeChanged: ");
        dotX = w / 2;
        dotY = h / 3;
        lineLength = h / 3;
//        ballRadius = 22f;
        //默认第一个球开始动
        firstBallAnim();
        //角度越来越小
        radiansGradient();

//        lastBallAnim();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        for (int i = minNo; i <= maxNo; i++) {
            if (isEvenNo) {
                if (i == 0) continue;
                if (i == minNo && !flag) {
                    canvas.drawLine(dotX + (2 * i + 1) * ballRadius, dotY, firstBallX + (2 * i + 1)
                            * ballRadius, firstBallY, linePaint);
                    canvas.drawCircle(firstBallX + (2 * i + 1)
                            * ballRadius, firstBallY, ballRadius, ballPaint);
                } else if (i == maxNo && flag) {
                    canvas.drawLine(dotX + (2 * i - 1) * ballRadius, dotY, lastBallX + (2 * i - 1) *
                                    ballRadius,
                            lastBallY, linePaint);
                    canvas.drawCircle(lastBallX + (2 * i - 1) * ballRadius, lastBallY, ballRadius,
                            ballPaint);
                } else {
                    if (i < 0) {//(2i-1)r
                        canvas.drawLine(dotX + (2 * i + 1) * ballRadius, dotY, dotX + (2 * i + 1) *
                                        ballRadius, 2 * dotY,
                                linePaint);
                        canvas.drawCircle(dotX + (2 * i + 1) * ballRadius, 2 * dotY, ballRadius, ballPaint);

                    } else {//(2i-1)r
                        canvas.drawLine(dotX + (2 * i - 1) * ballRadius, dotY, dotX + (2 * i - 1) *
                                ballRadius, 2 * dotY, linePaint);
                        canvas.drawCircle(dotX + (2 * i - 1) * ballRadius, 2 * dotY, ballRadius, ballPaint);
                    }
                }
                if (i > 0) {
                    canvas.drawCircle(dotX + (2 * i - 1) * ballRadius, dotY, ballRadius / 2, linePaint);
                } else {
                    canvas.drawCircle(dotX + (2 * i + 1) * ballRadius, dotY, ballRadius / 2, linePaint);
                }
            } else {
                if (i == minNo && !flag) {
                    canvas.drawLine(dotX + i * 2 * ballRadius, dotY, firstBallX + i * 2 * ballRadius,
                            firstBallY,
                            linePaint);
                    canvas.drawCircle(firstBallX + i * 2 * ballRadius, firstBallY, ballRadius, ballPaint);
                } else if (i == maxNo && flag) {
                    canvas.drawLine(dotX + i * 2 * ballRadius, dotY, lastBallX + i * 2 * ballRadius,
                            lastBallY,
                            linePaint);
                    canvas.drawCircle(lastBallX + i * 2 * ballRadius, lastBallY, ballRadius, ballPaint);
                } else {
                    canvas.drawLine(dotX + i * 2 * ballRadius, dotY, dotX + i * 2 * ballRadius, 2 * dotY,
                            linePaint);
                    canvas.drawCircle(dotX + i * 2 * ballRadius, 2 * dotY, ballRadius, ballPaint);
                }
                canvas.drawCircle(dotX + i * 2 * ballRadius, dotY, ballRadius / 2, linePaint);
            }
        }
        invalidate();
    }


}
