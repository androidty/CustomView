package com.ty.customview.customview.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cunoraz.gifview.library.GifView;
import com.ty.customview.customview.R;
import com.ty.customview.customview.entity.ViewData;

import java.util.List;

/**
 * Created by ty on 2018/3/5.
 */

public class MainRvAdapter extends BaseQuickAdapter<ViewData, BaseViewHolder> {
    public MainRvAdapter(int layoutResId, @Nullable List<ViewData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ViewData item) {
        helper.setText(R.id.item_main_title_tv, item.getTitle()).setText(R.id.item_main_desc_tv, item.getDesc
                ());
        if (item.getImg().isGif()) {
            GifView gifView = helper.getView(R.id.item_main_gif_img);
            gifView.setVisibility(View.VISIBLE);
            gifView.setGifResource(item.getImg().getImgRes());
        } else {
            helper.setVisible(R.id.item_main_img_iv, true).setVisible(R.id.item_main_gif_img, false)
                    .setImageResource(R.id.item_main_img_iv, item.getImg().getImgRes());
        }
    }
}
