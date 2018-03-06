package com.ty.customview.customview.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.ty.customview.customview.R;
import com.ty.customview.customview.view.animationnumview.AnimationNumView;

import java.lang.reflect.Method;

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
    @BindView(R.id.animNumTextView4)
    AnimationNumView mAnimNumTextView4;
    @BindView(R.id.animnum_et)
    EditText mAnimnumEt;
    @BindView(R.id.reset_btn)
    Button mResetBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animationnum);
        ButterKnife.bind(this);

    }



    @OnClick(R.id.reset_btn)
    public void onViewClicked() {
        hideSoftKeyboard();
        String s = mAnimnumEt.getText().toString();
        if(mAnimnumEt.getText().toString().equals("")){
            s="0";
        }
        mAnimNumTextView1.setStaticText(s, 2);
        mAnimNumTextView2.setAnimationText(s, 0);
        mAnimNumTextView3.setAnimationText(s, 3);
        mAnimNumTextView4.setAnimationText(s, 4);

      
    }



    /**
     * Shows the soft input for its input text, by  reflection mechanism
     */
    public  void showSoftKeyboard() {
        Object o = null;
        try{
            Class<?> threadClazz = Class.forName("android.view.inputmethod.InputMethodManager");

            Method method = threadClazz.getDeclaredMethod("peekInstance" );//return sInstance
            Method[] methods = threadClazz.getDeclaredMethods();

            method.setAccessible( true);
            o = method.invoke( null);
            if(o == null ){
                return;
            }
            InputMethodManager inputMethodManager = (InputMethodManager)o;
            if (inputMethodManager != null) {
                mAnimnumEt.requestFocus();
                inputMethodManager.showSoftInput( mAnimnumEt, 0);
            }
        } catch (Exception e){
        }
    }

    /**
     * Hides the soft input if it is active for the input text, by  reflection mechanism
     */
    public void hideSoftKeyboard(){
        Object o = null;
        try{
            Class<?> threadClazz = Class.forName("android.view.inputmethod.InputMethodManager");

            Method method = threadClazz.getDeclaredMethod("peekInstance");//return sInstance
            Method[] methods = threadClazz.getDeclaredMethods();

            method.setAccessible( true);
            o = method.invoke( null);
            if(o == null ){
                return;
            }
            InputMethodManager inputMethodManager = (InputMethodManager)o;
            if (inputMethodManager != null && inputMethodManager.isActive(mAnimnumEt )) {
                inputMethodManager.hideSoftInputFromWindow(mAnimnumEt .getWindowToken(), 0);
            }
        } catch (Exception e){
        }
    }
}
