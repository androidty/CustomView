package com.ty.customview.customview.view.loadingwaveview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.ty.customview.customview.R;
import com.ty.customview.customview.util.Util;


/**
 * Created by ty on 2017/8/3.
 */

public class LoadingView extends View {


    /**
     * 1.画一个大的圆角矩形（靠左侧空白的竖直长矩形，靠右上角标志型状的空白）
     * 2.画一个长条矩形（从上往下降落，落到圆角矩形的下边上停止，圆角矩形下沉后恢复，波浪开始上升）
     * 3.双波浪是用三角函数的曲线绘制，绘制两条正弦的曲线，设置不同速度让其重叠形成立体水波,canvas.drawLine()  y = Asin(wx+b)+h;
     * 4.双波浪在圆角矩形里慢慢上升上升到90%长条形矩形渐变细，上边下降
     *
     * @param context
     */

    //view的宽高
    private int mViewWidth;
    private int mViewHeight;

    //圆角弧度 单位是dp 默认角度10dp
    private int mRoundRadian = 15;

    private Context mContext;


    //圆角矩形
    private RectF mRoundRectF;
    private float mRoundLeft;
    private float mRoundTop;
    private float mRoundRight;
    private float mRoundBottom;
    //圆角矩形的宽度
    private int mRoundRectWidth;
    //圆角矩形的高度
    private int mRoundRectHeight;
    //竖直的空白矩形
    private RectF mVerticalRect;
    //特殊白色标志用path画
    private Path mMarkPath;
    //特殊标志的两个点的横坐标
    private float xA, xB;
    //凸起点的纵坐标
    private float yC, yD;
    //动态矩形一直在变化的bottom
    private float mDynamicRectfY = 0;
    //动态矩形会往中间靠的left和right 初始值和圆角矩形有关

    private float mDynamicRectfLeft, tempLeft;
    private float mDynamicRectfRight, tempRight;
    private float mDynamicRectfTop = 0;
    private float mCenterPosition;


    //波纹曲线变化的y坐标
    private float mDynamicWaveY = 9;

    //存放原始波纹曲线的数组
    private float[] originalWaveY;
    //存放第一条波纹曲线的重设定值的数组
    private float[] resetWaveY1;
    //存放第二条波纹曲线的重设定值的数组
    private float[] resetWaveY2;
    //速度,两条水波纹要有速度差才有起伏感，接近真实的水
    private int waveSpeed1 = 4;
    private int waveSpeed2 = 8;

    //曲线水平移动的距离
    private int mWaveOffsetX1;
    private int mWaveOffsetX2;
    //波峰
    private int waveA = 10;

    //振幅  周期 根据圆角矩形的宽度决定，一般设置为一个周期对应一个矩形宽度
    private float waveW;

    private Bitmap mBackBitmap;


    private Paint mWhitePaint;

    //圆角矩形画笔
    private Paint roundRectPaint;
    //双波浪画笔
    private Paint wavePaint;


    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    private void init() {
        Log.d("ondraddd", "init: ");
        roundRectPaint = new Paint();
        roundRectPaint.setColor(Color.parseColor("#FF6645"));
//        roundRectPaint.setColor(Color.parseColor("#FF493B"));
        roundRectPaint.setStyle(Paint.Style.FILL);
        roundRectPaint.setAntiAlias(true);

        mWhitePaint = new Paint();
        mWhitePaint.setColor(Color.WHITE);
        mWhitePaint.setStyle(Paint.Style.FILL);
        mWhitePaint.setAntiAlias(true);

        wavePaint = new Paint();
        wavePaint.setColor(Color.parseColor("#FF6645"));
        wavePaint.setStyle(Paint.Style.FILL);
        wavePaint.setAntiAlias(true);
        wavePaint.setDither(true);

        waveSpeed1 = Util.dp2px(mContext, waveSpeed1);
        waveSpeed2 = Util.dp2px(mContext, waveSpeed2);


    }

    //矩形从上到下的动画
    private void topToBottomAnim() {
        mDynamicWaveY = 0;

        flag = true;
        final ValueAnimator animator = ValueAnimator.ofFloat(0, mRoundBottom);
        animator.setDuration(3000);
//        animator.setRepeatMode(ValueAnimator.RESTART);
//        animator.setRepeatCount(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mDynamicRectfY = (float) valueAnimator.getAnimatedValue();
            }
        });
        animator.start();

    }

    //波纹从下到上的动画
    private void waveBottomToTopAnim() {
        final ValueAnimator animator = ValueAnimator.ofFloat(5, mRoundRectHeight * 0.985f);
        Log.d("waveBottomToTopAnim", "waveBottomToTopAnim: ");
        animator.setDuration(8000);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mDynamicWaveY = (float) valueAnimator.getAnimatedValue();
//                Log.d("djdjjdj", "onAnimationUpdate: "+mDynamicWaveY);

            }
        });
        animator.start();
    }

    //动态矩形变细变短动画
    private void gradientAnim() {
        flag = false;

        final ValueAnimator animator1 = ValueAnimator.ofFloat(tempLeft, mCenterPosition);
        animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mDynamicRectfLeft = (float) valueAnimator.getAnimatedValue();
            }
        });
        final ValueAnimator animator2 = ValueAnimator.ofFloat(tempRight, mCenterPosition);
        animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mDynamicRectfRight = (float) valueAnimator.getAnimatedValue();
            }
        });
        final ValueAnimator animator3 = ValueAnimator.ofFloat(0, mRoundTop);
        animator3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mDynamicRectfTop = (float) valueAnimator.getAnimatedValue();

            }
        });

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(3000);
        animatorSet.setInterpolator(new DecelerateInterpolator());

        animatorSet.play(animator1).with(animator2).with(animator3);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                Log.d("onanimation", "onAnimationEnd: " + mDynamicRectfRight + " " + mDynamicRectfLeft);
                mDynamicRectfLeft = tempLeft;
                mDynamicRectfRight = tempRight;
                mDynamicRectfTop = 0;
//                topToBottomAnim();
            }
        });
        animatorSet.start();


    }


    //重绘前重新设定Y值
    private void resetWaveY() {
        //第一条曲线要移动的距离
        int yWillMoveDistance1 = originalWaveY.length - mWaveOffsetX1;
        //重新设定第一条曲线的所有y值
        //把源数组里的数据从移动处开始从重设定数组的0开始复制yWillMoveDistance多位
        System.arraycopy(originalWaveY, mWaveOffsetX1, resetWaveY1, 0, yWillMoveDistance1);
        //衔接起来，把源数组里从0开始从重设定数组的yWillMoveDistance开始复制mWaveOffsetX1多位
        System.arraycopy(originalWaveY, 0, resetWaveY1, yWillMoveDistance1, mWaveOffsetX1);

        //第二条曲线要移动的距离
        int yWillMoveDistance2 = originalWaveY.length - mWaveOffsetX2;
        //重新设定第二条曲线的所有y值
        //把源数组里的数据从移动处开始从重设定数组的0开始复制yWillMoveDistance多位
        System.arraycopy(originalWaveY, mWaveOffsetX2, resetWaveY2, 0, yWillMoveDistance2);
        //衔接起来，把源数组里从0开始从重设定数组的yWillMoveDistance开始复制mWaveOffsetX1多位
        System.arraycopy(originalWaveY, 0, resetWaveY2, yWillMoveDistance2, mWaveOffsetX2);

    }


    float x, y;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d("ondreass", "onSizeChanged: " + w + "  " + h);
        if (w > h) {
            mViewWidth = h;
            mViewHeight = w;
        } else {
            mViewWidth = w;
            mViewHeight = h;
        }
        Log.d("ondreass", "onSizeChanged: " + mViewWidth + "  " + mViewHeight);

        //圆角矩形的重要点
        mRoundLeft = mViewWidth / 4;
        mRoundTop = mViewHeight / 4;
        mRoundRight = (mViewWidth) * 3 / 4;
        mRoundBottom = (mViewHeight) * 3 / 4;

        //竖直动态矩形的左右坐标
        tempLeft = mDynamicRectfLeft = mRoundLeft * 1.5f;
        tempRight = mDynamicRectfRight = mRoundLeft * 1.7f;
        mCenterPosition = (mDynamicRectfLeft + mDynamicRectfRight) / 2;

        mRoundRectWidth = (int) (mRoundRight - mRoundLeft);

        mRoundRectHeight = (int) (mRoundBottom - mRoundTop);


        //圆角矩形
        mRoundRectF = new RectF(mRoundLeft, mRoundTop, mRoundRight, mRoundBottom);
        //竖直矩形
        mVerticalRect = new RectF(mRoundLeft * 1.2f, mRoundTop, mRoundLeft * 1.4f, mRoundBottom);
        //特殊标记的重要点

        xB = mRoundRight * 17 / 18;
        xA = xB * 0.85f;
        yC = mRoundTop * 1.18f;
        yD = mRoundTop * 1.28f;

        //绘制特殊标记的path
        mMarkPath = new Path();
        mMarkPath.moveTo(xA, mRoundTop);
        mMarkPath.lineTo(xA, yD);
        mMarkPath.lineTo((xA + xB) / 2, yC);
        mMarkPath.lineTo(xB, yD);
        mMarkPath.lineTo(xB, mRoundTop);
        mMarkPath.close();
        //初始化波纹的一些数据
        originalWaveY = new float[mRoundRectWidth];
        resetWaveY1 = new float[mRoundRectWidth];
        resetWaveY2 = new float[mRoundRectWidth];
        waveW = (float) (2 * Math.PI / mRoundRectWidth);

        //给y设定初值
        for (int i = 0; i < mRoundRectWidth; i++) {
            originalWaveY[i] = (float) (waveA * Math.sin(waveW * i));
            Log.d("122222", "onSizeChanged: " + originalWaveY[i] + "  " + i);
        }

        topToBottomAnim();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mViewWidth = MeasureSpec.getSize(widthMeasureSpec);
        mViewHeight = MeasureSpec.getSize(heightMeasureSpec);

        if (mViewWidth > mViewHeight) {
            mViewHeight = mViewWidth;
        } else {
            mViewWidth = mViewHeight;
        }


    }

    boolean flag = true;

    private void drawWave(Canvas canvas) {
        //画波纹
        //画波纹前先重设y值
        resetWaveY();
        if (mDynamicWaveY == 0) {
//            mDynamicRectfLeft = tempLeft;
//            mDynamicRectfRight = tempRight;
//            Log.d("topanim", "topToBottomAnim: "+mDynamicRectfLeft+"   "+mDynamicRectfRight);
//            mDynamicRectfTop = 0;
//            mDynamicRectfY =0;
            waveBottomToTopAnim();
        }
        if (mDynamicWaveY >= mRoundRectHeight * 0.8f) {
            Log.d("drawwave", "drawWave: ");
            if (flag) {
                gradientAnim();
            }
        }
        if (mDynamicWaveY == mRoundRectHeight) {
            Log.d("ddddddd", "drawWave: ");
//            topToBottomAnim();
        }
        for (int i = 0; i < mRoundRectWidth; i++) {
            //绘制第一条
            canvas.drawLine(i + mRoundLeft, mRoundBottom - resetWaveY1[i] - mDynamicWaveY, i + mRoundLeft,
                    mRoundBottom, wavePaint);
            canvas.drawLine(i + mRoundLeft, mRoundBottom - resetWaveY2[i] - mDynamicWaveY, i + mRoundLeft,
                    mRoundBottom, wavePaint);
        }
        mWaveOffsetX1 += waveSpeed1;
        mWaveOffsetX2 += waveSpeed2;
        if (mWaveOffsetX1 > mRoundRectWidth) {
            mWaveOffsetX1 = 0;
        }
        if (mWaveOffsetX2 > mRoundRectWidth) {
            mWaveOffsetX2 = 0;
        }

    }

    Canvas canvas;
    Bitmap bitmap;
    Paint paint;

    private Bitmap createBitmap() {
        if (mDynamicRectfY == mRoundBottom) {
            bitmap = Bitmap.createBitmap(mRoundRectWidth * 2, mRoundRectHeight * 2, Bitmap.Config.ARGB_8888);
            canvas = new Canvas(bitmap);
//        canvas.translate(mRoundLeft,mRoundTop);
            drawWave(canvas);

            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP));

            mBackBitmap = getBitmapFromDrawable(mContext.getResources().getDrawable(R.drawable.rectangle));

            mBackBitmap = Bitmap.createScaledBitmap(mBackBitmap, mRoundRectWidth + 1, mRoundRectHeight + 2,
                    false);
            canvas.drawBitmap(mBackBitmap, mRoundLeft - 1, mRoundTop - 2, paint);
            return bitmap;
        } else {
            return null;
        }
    }

    private Bitmap getBitmapFromDrawable(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        try {
            Bitmap bitmap;
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                    Bitmap.Config.ARGB_8888);
            Log.d("bitmapa", "getBitmapFromDrawable: " + drawable.getIntrinsicHeight() + "  " + drawable
                    .getIntrinsicWidth());
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (OutOfMemoryError e) {
            return null;
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        canvas.translate((mViewHeight-mViewWidth)/2,-800);
//        canvas.scale(0.5f,0.5f);
        canvas.drawRoundRect(mRoundRectF, Util.dp2px(mContext, mRoundRadian), Util.dp2px(mContext,
                mRoundRadian), mWhitePaint);

        //画动态矩形

//        drawWave(canvas);
        if (createBitmap() != null && mDynamicRectfY == mRoundBottom) {
            canvas.drawBitmap(createBitmap(), 0, 0, null);
        }
//        canvas.drawRect(mDynamicRectfLeft, mDynamicRectfTop, mDynamicRectfRight,
//                mDynamicRectfY - ((mDynamicWaveY < 9) ? (mDynamicWaveY) : (mDynamicWaveY - 10)),
//                roundRectPaint);
        Log.d("1111111", "onDraw: " + mDynamicRectfLeft + "  " + mDynamicRectfTop + "  " + "  " +
                mDynamicRectfRight + "  " + mDynamicRectfY);

        canvas.drawRect(mVerticalRect, mWhitePaint);
        canvas.drawPath(mMarkPath, mWhitePaint);
        //重绘
        invalidate();
    }
}
