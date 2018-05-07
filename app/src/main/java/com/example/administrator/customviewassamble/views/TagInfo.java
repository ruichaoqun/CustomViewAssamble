package com.example.administrator.customviewassamble.views;

/**
 * Created by Administrator on 2018/5/5.
 */

public class TagInfo {
    private String tag;
    private int res;
    private int isSelect = 0;

    public TagInfo(String tag, int res) {
        this.tag = tag;
        this.res = res;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }

    public int getIsSelect() {
        return isSelect;
    }

    public void setIsSelect(int isSelect) {
        this.isSelect = isSelect;
    }
}
