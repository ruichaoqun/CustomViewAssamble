package com.example.administrator.customviewassamble.views.linemovewithfinger;


import android.graphics.Point;
import androidx.annotation.ColorInt;

public class LinePoint extends Point {
    public int xSpeed;         //x轴速度
    public int ySpeed;         //y轴速度
    public int xAccelerated;   //x加速度
    public int yAccelerated;   //Y加速度
    public @ColorInt int color;          //颜色
    public boolean isCatching = false;          //是否正在捕获中
    public boolean isCatched = false;           //是否被捕获
    public boolean isInderectCatched = false;

    public LinePoint(int x, int y) {
        super(x, y);
    }

    public double getDistance(Point p){
        double r = Math.sqrt((p.x-x)*(p.x-x)+(p.y-y)*(p.y-y));
        return r;
    }
}
