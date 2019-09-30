package com.ty.customview.customview.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ty.customview.customview.R;
import com.ty.customview.customview.view.displayview.DisplayView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DisplayViewActivity extends AppCompatActivity {

    @BindView(R.id.displayView)
    DisplayView displayView;
    @BindView(R.id.displayView1)
    DisplayView displayView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_view);
        ButterKnife.bind(this);

        displayView.init(getString(R.string.statement),"•");
        displayView1.init(getString(R.string.statement1),"\\*");
    }
}
