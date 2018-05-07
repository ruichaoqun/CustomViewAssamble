package com.example.administrator.customviewassamble.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Xfermode;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.administrator.customviewassamble.Arith;
import com.example.administrator.customviewassamble.R;
import com.example.administrator.customviewassamble.UIUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/5/5.
 */

public class WaveView extends SurfaceViewMould {
    private Paint paint;//画笔
    private final Paint clearScreenPaint = new Paint();
    private final Xfermode srcXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC);
    private final Xfermode clearXfermode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
    private Path path1,path2,path3;

    //绘制曲线时的取点数量
    private static final float SIZE = 100f;

    //每次执行动画时曲线的偏移量
    private static final float SPEED = 0.1f;
    private float mPhase = 0.5f;

    private static List<Float> fixedValues = new ArrayList<>();
    private static List<Float> pointXList = new ArrayList<>();
    private Map<Float,List<PointF>> pointMap = new HashMap<>();
    private Map<Float,List<RectFExtend>> rectMap = new HashMap<>();
    private Path path = new Path();
    private int startColor,endColor;
    private boolean isDataInit = false;

    List<Float> listAbsMInX = new ArrayList<>();
    List<Float> listAbsMaxX = new ArrayList<>();
    List<RectF> rectFS = new ArrayList<>();
    private List<Anchor> pointFS = new ArrayList<>();
    private Context context;
    private PorterDuffXfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);

    public WaveView(Context context) {
        this(context,null);
    }

    public WaveView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public WaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        path = new Path();
        path1 = new Path();
        path2 = new Path();
        path3 = new Path();
        startColor = ContextCompat.getColor(context,R.color.color_8800F4D2);
        endColor = ContextCompat.getColor(context,R.color.color_8810203f);
        for (int i = 0; i <= SIZE; i++) {
            float x = -2 + i*4/SIZE;
            float f = (float) Math.pow(4/(4+Math.pow(x,4)),2.5);
            fixedValues.add(f);
            pointXList.add(x);
        }
    }

    @Override
    protected void doDraw(Canvas canvas) {
        clearScreenPaint.setXfermode(clearXfermode);
        canvas.drawPaint(clearScreenPaint);
        clearScreenPaint.setXfermode(srcXfermode);
        canvas.drawColor(ContextCompat.getColor(context,R.color.color_05101A));
        path.reset();
        path1.reset();
        path2.reset();
        path3.reset();
        if(!isDataInit)
            initData();
        List<PointF> list = pointMap.get(mPhase);
        if(list != null){
//            drawBacLayer(canvas,mPhase+0.3f);
            drawLayer(canvas,mPhase,true);
            //drawLayer(canvas,mPhase+0.3f,false);
        }
        mPhase = Arith.round(mPhase == 0?1.9f:mPhase - SPEED,2);
        Log.w("AAA","mPhase-->"+mPhase);
    }

    private void drawBacLayer(Canvas canvas, float mPhase) {
        mPhase = Arith.round(mPhase%2,2);
        //1.画正弦区域
        List<PointF> list = pointMap.get(mPhase);
        List<PointF> list1 = pointMap.get(Arith.round((mPhase+1)%2,2));
        if(list != null && list1 != null){
            for (int i = 0; i < list.size(); i++) {
                if(i == 0)
                    path.moveTo(list.get(i).x,list.get(i).y);
                else
                    path.lineTo(list.get(i).x,list.get(i).y);
            }
            for (int i = list1.size() -1 ; i >= 0 ; i--) {
                path.lineTo(list1.get(i).x,list1.get(i).y);
            }
        }
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(ContextCompat.getColor(context,R.color.color_05101A));
        canvas.drawPath(path,paint);
    }

    private void initData() {
        for (int i = 0; i < 2 / SPEED; i++) {
            List<PointF> list = new ArrayList<>(20);
            pointFS.clear();
            for (int j = 0; j <= SIZE; j++) {
                float tt = j*width/SIZE;
                PointF pointF = new PointF(tt,getDisplacementY(j,i*SPEED));
                list.add(pointF);
                if(j > 1){
                    //最小点(绝对值)
                    if(Math.abs(list.get(j-1).y - height/2) <= Math.abs(list.get(j - 2).y - height/2) &&
                            Math.abs(list.get(j-1).y - height/2) <= Math.abs(pointF.y - height/2)){
//                        Log.w("AAA",Math.abs(list.get(j-1).y - height/2)  + "    "+Math.abs(list.get(j - 2).y - height/2)
//                        +"    "+Math.abs(pointF.y - height/2));
                        pointFS.add(new Anchor(list.get(j-1),0));
                    }
                    //波峰值
                    if(list.get(j-1).y >= list.get(j-2).y && list.get(j-1).y >= pointF.y){
                        //Log.w("AAA","波峰");
                        pointFS.add(new Anchor(list.get(j-1),1));
                    }
                    //波谷值
                    if(list.get(j-1).y <= list.get(j-2).y && list.get(j-1).y <= pointF.y){
                        //Log.w("AAA","波谷");
                        pointFS.add(new Anchor(list.get(j-1),2));
                    }
                }
            }
            List<RectFExtend> list1 = new ArrayList<>();
            caculateRect(list1);
            rectMap.put(Arith.round(i*SPEED,2),list1);
            pointMap.put(Arith.round(i*SPEED,2),list);
        }
        isDataInit = true;
    }

    private void drawLayer(Canvas canvas,float mPhase,boolean flag) {
        mPhase = Arith.round(mPhase%2,2);
        //1.画正弦区域
        List<PointF> list = pointMap.get(mPhase);
        List<PointF> list1 = pointMap.get(Arith.round((mPhase+1)%2,2));
        if(list != null && list1 != null){
            for (int i = 0; i < list.size(); i++) {
                if(i == 0){
                    path1.moveTo(list.get(i).x,list.get(i).y);
                    path3.moveTo(list.get(i).x,height/2+(list.get(i).y-height/2)/5);
                    path.moveTo(list.get(i).x,list.get(i).y);
                } else {
                    path3.lineTo(list.get(i).x,height/2+(list.get(i).y-height/2)/5);
                    path1.lineTo(list.get(i).x,list.get(i).y);
                    path.lineTo(list.get(i).x,list.get(i).y);
                }
            }
            for (int i = list1.size() -1 ; i >= 0 ; i--) {
                if(i == list1.size() -1){
                    path2.moveTo(list1.get(i).x,list1.get(i).y);
                }else
                    path2.lineTo(list1.get(i).x,list1.get(i).y);
                path.lineTo(list1.get(i).x,list1.get(i).y);
            }
        }
        canvas.saveLayer(0,0,width,height,null,Canvas.ALL_SAVE_FLAG);
        canvas.drawPath(path,paint);
        //canvas.drawBitmap(getBitmap(path),0,0,null);
        //2.绘制过度图形
        paint.setXfermode(xfermode);
        List<RectFExtend> rectList = rectMap.get(mPhase);
        if(rectList != null){
            for (int i = 0; i < rectList.size(); i++) {
                RectFExtend extend = rectList.get(i);
                RectF rectF = extend.rectF;
                LinearGradient gradient;
                if(extend.flag){
                    gradient = new LinearGradient(rectF.left, rectF.top, rectF.left, rectF.bottom,
                            startColor,endColor, Shader.TileMode.REPEAT);
                }else{
                    gradient = new LinearGradient(rectF.left, rectF.top, rectF.left, rectF.bottom,
                            endColor,startColor, Shader.TileMode.REPEAT);
                }
                paint.setShader(gradient);
                canvas.drawRect(rectF,paint);
            }
        }
        paint.setXfermode(null);
        paint.setShader(null);
        paint.reset();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);


        paint.setColor(ContextCompat.getColor(context,R.color.color_10203f));
        canvas.drawPath(path2,paint);

        if(flag){
            paint.setColor(ContextCompat.getColor(context,R.color.color_883B6D7E));
            canvas.drawPath(path3,paint);
        }
        paint.setColor(ContextCompat.getColor(context,R.color.color_00F4D2));
        canvas.drawPath(path1,paint);

        paint.reset();
        canvas.restore();
    }

    private Bitmap getBitmap(Path path) {
        Bitmap bitmap = Bitmap.createBitmap((int)width, (int)height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawPath(path,paint);
        return bitmap;
    }


    private float getDisplacementY(int i,float phase) {
        return  (float) (height/2 - 0.5*(width/4)*fixedValues.get(i)*Math.sin(0.75*Math.PI*pointXList.get(i) + phase*Math.PI));
    }

    private void caculateRect(List<RectFExtend> rectFList) {
        int i = 0;
        while (i < pointFS.size() - 1){
            RectF rectF = new RectF();
            Anchor anchor = pointFS.get(i);
            if(anchor.pointStatus == 0){
                if(i < pointFS.size() - 2){
                    rectF.left = pointFS.get(i).pointF.x;
                    rectF.right = pointFS.get(i+2).pointF.x;
                    rectF.top =  height/2 - Math.abs(pointFS.get(i+1).pointF.y - height/2);
                    rectF.bottom = height - rectF.top;
                    boolean flag = pointFS.get(i+1).pointStatus == 1?true:false;
                    rectFList.add(new RectFExtend(rectF,flag));
                    i += 2;
                }else{
                    if(i == pointFS.size() - 2) {
                        rectF.left = pointFS.get(i).pointF.x;
                        rectF.right = width;
                        rectF.top = height / 2 - Math.abs(pointFS.get(i + 1).pointF.y - height / 2);
                        rectF.bottom = height - rectF.top;
                        boolean flag = pointFS.get(i+1).pointStatus == 1?true:false;
                        rectFList.add(new RectFExtend(rectF,flag));
                        i += 2;
                    }else{
                        i++;
                    }
                }
            }else{
                rectF.left = 0;
                rectF.right = pointFS.get(i+1).pointF.x;
                rectF.top = height / 2 - Math.abs(anchor.pointF.y - height / 2);
                rectF.bottom = height - rectF.top;
                boolean flag = anchor.pointStatus == 1?true:false;
                rectFList.add(new RectFExtend(rectF,flag));
                i ++;
            }
        }
    }

    private List<RectF> getMeasuredRect(float mPhase ){


        //获取波峰和波谷的X值
        for (int i = -5; i <= 4 ; i++) {
            float x1 = (i - mPhase)*4/3;
            listAbsMInX.add(x1);
            float x2 = (i+0.5f - mPhase)*4/3;
            listAbsMaxX.add(x2);
        }
        int i = 0;
        while (i < listAbsMInX.size() - 1){
            float x1 = listAbsMInX.get(i);
            float x2 = listAbsMInX.get(i+1);
            if(x1 >= 2 || x2 <= -2){
            }else{
                float maxX = -10;
                for (int j = 0; j < listAbsMaxX.size(); j++) {
                    if(listAbsMaxX.get(j) > x1 && listAbsMaxX.get(j) < x2){
                        maxX = listAbsMaxX.get(j);
                        break;
                    }
                }
                if(maxX != -10){
                    RectF rectF = new RectF();
                    rectF.left = (x1+2)*width/4;
                    rectF.right = (x2 + 2)*width/4;
                    float y = (float) Math.abs((width/4)*0.5*Math.pow(4/(4+Math.pow(maxX,4)),2.5)*Math.sin(0.75*maxX*Math.PI + mPhase*Math.PI));
                    rectF.top = height/2 - y;
                    rectF.bottom = height/2+y;
                    rectFS.add(rectF);
                }
            }
            i++;
        }
        return rectFS;
    }

    private class Anchor{
        PointF pointF;//点
        int pointStatus;//0:接近X轴，1：波峰 2：波谷

        public Anchor(PointF pointF, int pointStatus) {
            this.pointF = pointF;
            this.pointStatus = pointStatus;
        }
    }

    private class RectFExtend{
        RectF rectF;
        boolean flag;

        public RectFExtend(RectF rectF, boolean flag) {
            this.rectF = rectF;
            this.flag = flag;
        }
    }

}
