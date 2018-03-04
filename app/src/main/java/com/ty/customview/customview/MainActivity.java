package com.ty.customview.customview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ty.customview.customview.animationnumview.AnimationNumView;


public class MainActivity extends AppCompatActivity {

    private AnimationNumView animationNumView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        animationNumView = findViewById(R.id.animNumTextView);
        animationNumView.setAnimationText("12345487164.26",1);
        animationNumView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animationNumView.setAnimationText("12345487164.23",3);
            }
        });
    }
}
