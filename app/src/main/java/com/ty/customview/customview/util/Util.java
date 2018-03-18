package com.ty.customview.customview.util;

import android.content.Context;
import android.util.Log;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
     *
     * @param context
     * @param px
     * @return
     */
    public static int px2dp(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);

    }


    public static void getChar(String s) {
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            if (!map.containsKey(s.charAt(i))) {
                map.put(s.charAt(i), 1);
            } else {
                int x = map.get(s.charAt(i)) + 1;
                map.put(s.charAt(i), x);
            }
        }
        char c = 0;
        int min = 0;
        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            char key = (char) entry.getKey();
            int val = (int) entry.getValue();
            if (val > min) {
                min = val;
                c = key;
            }
        }
        Log.d("ty123", " 出现最多次数的字符是 " + c + "  一共出现了 " + min + " 次");

    }
}
