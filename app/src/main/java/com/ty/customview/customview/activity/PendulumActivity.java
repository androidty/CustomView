package com.ty.customview.customview.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ty.customview.customview.R;
import com.ty.customview.customview.view.pendulumview.PendulumView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ty on 2018/3/6.
 */

public class PendulumActivity extends AppCompatActivity {
    @BindView(R.id.pendulumview)
    PendulumView mPendulumview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pendulum);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle("牛顿单摆");
    }
}
