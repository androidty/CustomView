package com.ty.customview.customview.animationnumview;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import java.text.DecimalFormat;

/**
 * Created by ty on 2018/2/2.
 */

public class AnimationNumView extends android.support.v7.widget.AppCompatTextView {

    //默认保留整数位
    private int keepFigures = 0;
    private final String KEEP_INTEGER = "#,###";
    private final String KEEP_ONE_FIGURE = "#,###.0";
    private final String KEEP_TWO_FIGURE = "#,###.00";
    private final String KEEP_THREE_FIGURE = "#,###.000";

    private String KEEPFIGURE = KEEP_INTEGER;

    private boolean openAnimation = false;


    public AnimationNumView(Context context) {
        this(context, null);
    }

    public AnimationNumView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnimationNumView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    static class DoubleEValuetor implements TypeEvaluator<Double> {
        @Override
        public Double evaluate(float v, Double aDouble, Double t1) {
            double x = v * t1;
            return x;
        }
    }

    double res;


    public void setAnimationText(String text) {
        if (!isNum(text)) {
            this.setText("请设置纯数字文本");
            return;
        }
        res = Double.parseDouble(text);
        openAnimation = true;
        ValueAnimator animator = ValueAnimator
                .ofObject(new DoubleEValuetor(), 0.1, res);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                res = ((Double) animation.getAnimatedValue());
                invalidate();
            }

        });
        animator.setDuration(3000);
        animator.start();
    }


    public void setAnimationText(String text, int keepFigures) {
        this.setKeepFigures(keepFigures);
        setAnimationText(text);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (openAnimation) {
            this.setText(formatTosepara(res));
        }
    }

    public String formatTosepara(Object data) {
        DecimalFormat df;
        switch (getKeepFigures()) {
            case 0:
                KEEPFIGURE = KEEP_INTEGER;
                break;
            case 1:
                KEEPFIGURE = KEEP_ONE_FIGURE;
                break;
            case 2:
                KEEPFIGURE = KEEP_TWO_FIGURE;
                break;
            case 3:
                KEEPFIGURE = KEEP_THREE_FIGURE;
                break;
        }
        df = new DecimalFormat(KEEPFIGURE);
        return df.format(data);
    }

    public void setStaticText(String text) {
        if (!isNum(text)) {
            this.setText("请设置纯数字文本");
        } else {
            this.setText(formatTosepara(Double.parseDouble(text)));
        }
    }

    public void setStaticText(String text, int keepFigures) {
        this.setKeepFigures(keepFigures);
        this.setStaticText(text);
    }


    private void setKeepFigures(int keepFigures) {
        this.keepFigures = keepFigures;
    }

    private int getKeepFigures() {
        return keepFigures;
    }


    private boolean isNum(String str) {
        String reg = "^[0-9]+(.[0-9]+)?$";
        return str.matches(reg);
    }
}
