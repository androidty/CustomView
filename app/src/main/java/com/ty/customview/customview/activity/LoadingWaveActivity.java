package com.ty.customview.customview.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ty.customview.customview.R;

/**
 * Created by ty on 2018/3/5.
 */

public class LoadingWaveActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loadingwave);
        getSupportActionBar().setTitle("3D水波纹");
    }
}
