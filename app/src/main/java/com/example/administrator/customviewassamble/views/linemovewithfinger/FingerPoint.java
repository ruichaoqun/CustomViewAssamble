package com.example.administrator.customviewassamble.views.linemovewithfinger;

import android.graphics.Point;
import android.view.VelocityTracker;

public class FingerPoint extends Point {
    public int xVelocity;          //x加速度
    public int yVelocity;          //y加速度
    public FingerPoint(int x, int y) {
        super(x, y);
    }
}
