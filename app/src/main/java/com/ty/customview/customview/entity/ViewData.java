package com.ty.customview.customview.entity;

/**
 * Created by ty on 2018/3/5.
 */

public class ViewData {
    private String title;
    private String desc;

    private Img img;

    public ViewData() {

    }


    public ViewData(String title, String desc, Img img) {
        this.title = title;
        this.desc = desc;
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Img getImg() {
        return img;
    }

    public void setImg(Img img) {
        this.img = img;
    }

    public static class Img{
        private String imgPath;
        private int imgRes;
        boolean isGif;

        public Img() {
        }

        public Img(int imgRes, boolean isGif) {
            this.imgRes = imgRes;
            this.isGif = isGif;
        }

        public String getImgPath() {
            return imgPath;
        }

        public void setImgPath(String imgPath) {
            this.imgPath = imgPath;
        }

        public int getImgRes() {
            return imgRes;
        }

        public void setImgRes(int imgRes) {
            this.imgRes = imgRes;
        }

        public boolean isGif() {
            return isGif;
        }

        public void setGif(boolean gif) {
            isGif = gif;
        }
    }


}
