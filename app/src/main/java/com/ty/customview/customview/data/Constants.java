package com.ty.customview.customview.data;

import com.ty.customview.customview.R;
import com.ty.customview.customview.activity.AnimationNumActivity;
import com.ty.customview.customview.activity.LoadingWaveActivity;
import com.ty.customview.customview.activity.PendulumActivity;
import com.ty.customview.customview.entity.ViewData;

/**
 * Created by ty on 2018/3/5.
 */

public class Constants {

    public static ViewData.Img[] sImgs = {
            new ViewData.Img(R.mipmap.tywave, true),
            new ViewData.Img(R.mipmap.pmldb, true),
            new ViewData.Img(R.mipmap.shadow, false)
    };

    public static Class[] sClasses = {
            LoadingWaveActivity.class,
            PendulumActivity.class,
            AnimationNumActivity.class
    };

}
