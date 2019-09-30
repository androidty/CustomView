package com.ty.customview.customview.data;

import com.ty.customview.customview.R;
import com.ty.customview.customview.activity.AnimationNumActivity;
import com.ty.customview.customview.activity.BezierActivity;
import com.ty.customview.customview.activity.BowActivity;
import com.ty.customview.customview.activity.DisplayViewActivity;
import com.ty.customview.customview.activity.LoadingWaveActivity;
import com.ty.customview.customview.activity.PendulumActivity;
import com.ty.customview.customview.activity.RingProgressActivity;
import com.ty.customview.customview.entity.ViewData;
import com.ty.customview.customview.view.displayview.DisplayView;

/**
 * Created by ty on 2018/3/5.
 */

public class Constants {

    public static ViewData.Img[] sImgs = {
            new ViewData.Img(R.mipmap.tywave, true),
            new ViewData.Img(R.mipmap.pmldb, true),
            new ViewData.Img(R.mipmap.shadow, false),
            new ViewData.Img(R.mipmap.ic_launcher,false),
            new ViewData.Img(R.mipmap.ic_launcher,false),
            new ViewData.Img(R.mipmap.ic_launcher,false),
            new ViewData.Img(R.mipmap.ic_launcher,false)
    };

    public static Class[] sClasses = {
            LoadingWaveActivity.class,
            PendulumActivity.class,
            AnimationNumActivity.class,
            BowActivity.class,
            BezierActivity.class,
            RingProgressActivity.class,
            DisplayViewActivity.class
    };

}
