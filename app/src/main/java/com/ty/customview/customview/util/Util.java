package com.ty.customview.customview.util;

import android.content.Context;

/**
 * Created by 38917 on 2017/8/3.
 */

public class Util {
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)     
     */

    public static int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp 
     * @param context
     * @param px
     * @return
     */
    public static int px2dp(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
        
    }


}
