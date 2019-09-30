package com.ty.customview.customview.view.bowview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.ty.customview.customview.R;

/**
 * Created by ty on 2018/3/9.
 */

public class BowView extends View {

    private Paint mPaint;
    private Path mPath;
    private float mWidth, mHeight;

    private int mMeasureWidth;
    private int mWidthMode;
    private int mMeasureHeight;
    private int mHeightMode;

    private int bowColor;

    public BowView(Context context) {
        this(context, null);
    }

    public BowView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BowView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.BowView);
        bowColor = typedArray.getColor(R.styleable.BowView_bowColor, Color.parseColor("#dabd79"));
        typedArray.recycle();
        init();
    }

    public void setBowColor(int bowColor) {
        this.bowColor = bowColor;
        mPaint.setColor(bowColor);
    }
    public void setBowColor(String bowColor){
        setBowColor(Color.parseColor(bowColor));
    }


    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(bowColor);
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);

        mPath = new Path();
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
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        mPath.moveTo(0, 0);
        mPath.quadTo(mWidth / 2,  mHeight/2, mWidth, 0);
        mPath.lineTo(0, 0);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPath, mPaint);
//        canvas.drawCircle(100,0,25,mShadowPaint);
    }
}
