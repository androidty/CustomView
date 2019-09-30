package com.ty.customview.customview.view.displayview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author ty
 * @date 2019/9/29.
 * GitHub：
 * email：
 * description：
 */
public class DisplayView extends LinearLayout {
    private String[] contents;
    private String spiltChar;
    private LayoutInflater mLayoutInflater;
    private Context mContext;


    public DisplayView(Context context) {
        this(context, null);
    }

    public DisplayView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DisplayView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setOrientation(VERTICAL);
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mContext = context;
    }

    public void init(String content, String spiltChar) {
        try {
            this.spiltChar = spiltChar;
            contents = content.split(spiltChar);
            for (String s : contents) {
                if (!TextUtils.isEmpty(s)) {
                    addItem(s);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addItem(String s) {
        LinearLayout linearLayout = new LinearLayout(mContext);
        linearLayout.setOrientation(HORIZONTAL);
        TextView textViewSpilt = new TextView(mContext);
        textViewSpilt.setText(spiltChar.startsWith("\\")?spiltChar.substring(1):spiltChar);
        TextView textViewContent = new TextView(mContext);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = 20;
        textViewContent.setLineSpacing(8,1);
        textViewContent.setLayoutParams(params);
        textViewContent.setText(s);
        linearLayout.addView(textViewSpilt);
        linearLayout.addView(textViewContent);
        LinearLayout.LayoutParams paramsl = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsl.topMargin = 20;
        linearLayout.setLayoutParams(paramsl);
        this.addView(linearLayout);
    }
}
