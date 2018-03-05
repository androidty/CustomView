package com.ty.customview.customview.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ty.customview.customview.R;
import com.ty.customview.customview.view.animationnumview.AnimationNumView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ty on 2018/3/5.
 */

public class AnimationNumActivity extends AppCompatActivity {
    @BindView(R.id.animNumTextView1)
    AnimationNumView mAnimNumTextView1;
    @BindView(R.id.animNumTextView2)
    AnimationNumView mAnimNumTextView2;
    @BindView(R.id.animNumTextView3)
    AnimationNumView mAnimNumTextView3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animationnum);
        ButterKnife.bind(this);
        mAnimNumTextView1.setAnimationText("122312148907.9844",3);
        mAnimNumTextView1.setAnimationText("1222313907.9842",4);
        mAnimNumTextView1.setStaticText("13231214907.9844");
    }

    @OnClick({R.id.animNumTextView1, R.id.animNumTextView2, R.id.animNumTextView3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.animNumTextView1:
                break;
            case R.id.animNumTextView2:
                break;
            case R.id.animNumTextView3:
                break;
        }
    }
}
