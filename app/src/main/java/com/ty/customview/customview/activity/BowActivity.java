package com.ty.customview.customview.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ty.customview.customview.R;
import com.ty.customview.customview.view.bowview.BowView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ty on 2018/3/9.
 */

public class BowActivity extends AppCompatActivity {
    @BindView(R.id.bowView)
    BowView mBowView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bow);
        ButterKnife.bind(this);
        mBowView.setBowColor(getResources().getColor(R.color.animTvColor1));
    }
}
