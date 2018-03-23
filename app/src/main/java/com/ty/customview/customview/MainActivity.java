package com.ty.customview.customview;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.ty.customview.customview.adapter.MainRvAdapter;
import com.ty.customview.customview.data.Constants;
import com.ty.customview.customview.entity.ViewData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {


    @BindView(R.id.main_recyclerview)
    RecyclerView mMainRecyclerview;
    private MainRvAdapter mMainRvAdapter;

    private List<ViewData> mViewDatas = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
        initDatas();
    }

    private void init() {

        mMainRvAdapter = new MainRvAdapter(R.layout.item_main_rv, mViewDatas);
        mMainRecyclerview.setHasFixedSize(true);
        mMainRecyclerview.setLayoutManager(new GridLayoutManager(this, 2));
        mMainRecyclerview.setAdapter(mMainRvAdapter);
        mMainRecyclerview.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(MainActivity.this, Constants.sClasses[position]));
//                RestartAPPTool.restartAPP(MainActivity.this,2000);
            }
        });
    }

    private void initDatas() {
        String[] titles = getResources().getStringArray(R.array.titles);
        String[] descs = getResources().getStringArray(R.array.descs);
        for (int i = 0; i < Constants.sImgs.length; i++) {
            ViewData viewData = new ViewData(titles[i], descs[i], Constants.sImgs[i]);
            mMainRvAdapter.addData(viewData);
        }
    }

    private void share(String content, Uri uri){
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        if(uri!=null){
            //uri 是图片的地址
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
            shareIntent.setType("image/*");
            //当用户选择短信时使用sms_body取得文字
            shareIntent.putExtra("sms_body", content);
        }else{
            shareIntent.setType("text/plain");
        }
        shareIntent.putExtra(Intent.EXTRA_TEXT, content);
        //自定义选择框的标题
        //startActivity(Intent.createChooser(shareIntent, "邀请好友"));
        //系统默认标题
        startActivity(shareIntent);
    }
}
