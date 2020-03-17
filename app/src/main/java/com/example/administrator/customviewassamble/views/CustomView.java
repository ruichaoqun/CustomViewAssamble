package com.example.administrator.customviewassamble.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.SystemClock;
import android.util.Pair;
import android.view.View;

import com.example.administrator.customviewassamble.UIUtils;

import java.util.Arrays;

/**
 * @author Rui Chaoqun
 * @date :2020/3/17 11:14
 * description:
 */
public abstract class CustomView extends View {

    /* renamed from: i reason: collision with root package name */
    private static final int f9703i = 1300;
    private static final int j = 4800;
    private static final int k = 55;
    private static final int l = 45;
    private static final float m = 0.2f;
//    private static final int n = al.a(83.0f);
    private double A;
    private float B;
    private long C;
    private Handler D;
    private Handler mUpdateHandler;
//    private e F;
//    private com.netease.cloudmusic.module.g.d G;
//    /* access modifiers changed from: private */
//    public a H = new a();
//    private com.netease.cloudmusic.module.g.a I;

    /* renamed from: a reason: collision with root package name */
    protected int f9704a = 4;

    /* renamed from: b reason: collision with root package name */
    protected int mCircleColor = -1;

    /* renamed from: c reason: collision with root package name */
    protected float[] f9706c = new float[3];

    /* renamed from: d reason: collision with root package name */
    protected int mCircleRadius;

    /* renamed from: e reason: collision with root package name */
    protected boolean f9708e = false;

    protected Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    protected Paint mPathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    /* renamed from: h reason: collision with root package name */
    protected Path mPath;
    private int[] q = a(this.mCircleColor);
    private int r;
    private int s;
    private int t;
    private int u;
    private int v;
    private double[] w;
    private float[] x;
    private float[] y;
    private float[] z;

//    /* compiled from: ProGuard */
//    private static class a implements b {
//
//        /* renamed from: a reason: collision with root package name */
//        boolean f9714a;
//
//        /* renamed from: b reason: collision with root package name */
//        float[] f9715b;
//
//        public void a() {
//        }
//
//        private a() {
//        }
//    }

    public void X_() {
    }

    public void a(boolean z2) {
    }

    /* access modifiers changed from: protected */
    public boolean a(Canvas canvas) {
        return false;
    }

    /* access modifiers changed from: protected */
    public abstract int[] a(int i2);

    public void b(Object obj, int i2) {
    }

    public boolean b() {
        return true;
    }

    public boolean c() {
        return false;
    }

    public CustomView(Context context) {
        super(context);
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setColor(this.q[0]);
        this.mPathPaint.setStyle(Paint.Style.STROKE);
        HandlerThread handlerThread = new HandlerThread("CurveRender");
        handlerThread.start();
//        this.D = new Handler(handlerThread.getLooper()) {
//            @Override
//            public void handleMessage(Message message) {
//                C0119a aVar = (C0119a) message.obj;
//                CustomView.this.a(aVar.f9702b, message.arg1, message.arg2);
//                CustomView.this.H.a(aVar);
//            }
//        };
//        this.mUpdateHandler = new Handler() {
//            @Override
//            public void handleMessage(Message message) {
//                CustomView.this.e();
//            }
//        };
//        Pair a2 = c.a(4);
//        this.F = (e) a2.first;
//        this.G = (com.netease.cloudmusic.module.g.d) a2.second;
//        this.G.a((f) this);

        this.y = new float[55];
        this.z = new float[55];
        this.A = 0.11423973285781065d;
    }

//    public Pair<Integer, Integer> d() {
//        int a2 = al.a(225.0f);
//        return new Pair<>(Integer.valueOf(a2), Integer.valueOf(a2));
//    }
//
//    public int b(com.netease.cloudmusic.module.ag.d.d dVar) {
//        return dVar.c()[1];
//    }
//
//    public int a(com.netease.cloudmusic.module.ag.d.d dVar) {
//        int d2 = (int) (((double) dVar.d()) * 0.85d);
//        this.B = 1000000.0f / ((float) d2);
//        return d2;
//    }

    public void setColor(int color) {
        if (this.mCircleColor != color) {
            this.mCircleColor = color;
            this.q = a(color);
            this.mPaint.setColor(this.q[0]);
            invalidate();
        }
    }

//    public void a(Object obj, int i2) {
//        int a2 = n.a(obj);
//        if (a2 > 0) {
//            if (!(this.r == a2 && this.s == i2)) {
//                this.r = a2;
//                this.s = i2;
//                float f2 = (((float) i2) / 1000.0f) / ((float) this.r);
//                this.t = (int) Math.ceil((double) (1300.0f / f2));
//                int min = (Math.min((int) (4800.0f / f2), (a2 / 2) - 1) - this.t) + 1;
//                this.u = min >= 55 ? min / 55 : -((int) Math.ceil((double) (55.0f / ((float) min))));
//            }
//            if (this.w == null) {
//                this.w = new double[55];
//                this.x = new float[55];
//                this.y = new float[55];
//                this.z = new float[55];
//                this.A = 0.11423973285781065d;
//            }
//            if (this.mCircleRadius > 0) {
//                Handler handler = this.D;
//                handler.sendMessage(handler.obtainMessage(1, this.t, this.u, this.H.a(obj, a2)));
//            }
//        }
//    }

//    public void a(Object obj, int i2, int i3) {
//        int i4;
//        com.netease.cloudmusic.module.g.a a2 = this.F.a();
//        a aVar = (a) a2.a();
//        if (aVar == null) {
//            aVar = new a();
//            a2.a(aVar);
//        }
//        aVar.f9714a = n.c(obj);
//        if (aVar.f9715b == null) {
//            aVar.f9715b = new float[this.x.length];
//        }
//        if (!aVar.f9714a) {
//            double d2 = 0.0d;
//            for (int i5 = 0; i5 < this.x.length; i5++) {
//                if (i3 > 0) {
//                    i4 = i5 * i3;
//                } else {
//                    i4 = i5 / (-i3);
//                }
//                double a3 = n.a(obj, (i4 + i2) * 2);
//                this.w[i5] = a3;
//                if (a3 > d2) {
//                    d2 = a3;
//                }
//            }
//            double d3 = d2 * 0.6d;
//            int i6 = 0;
//            while (i6 < this.x.length) {
//                double[] dArr = this.w;
//                double d4 = dArr[i6] > d3 ? dArr[i6] : 0.0d;
//                float[] fArr = this.x;
//                double d5 = d4 / 45.0d;
//                int i7 = this.v;
//                fArr[i6] = (float) Math.min(d5 * ((double) i7), (double) i7);
//                i6++;
//            }
//            for (int i8 = 0; i8 < aVar.f9715b.length; i8++) {
//                aVar.f9715b[i8] = n.a(this.x, i8, 5);
//            }
//        } else {
//            Arrays.fill(aVar.f9715b, 0.0f);
//        }
//        this.F.a(a2);
//    }

    public void a() {
        this.mUpdateHandler.sendEmptyMessage(1);
    }

    /* access modifiers changed from: private */
//    public void e() {
//        com.netease.cloudmusic.module.g.a a2 = this.G.a();
//        a aVar = (a) a2.a();
//        this.f9708e = aVar.f9714a;
//        if (!this.f9708e) {
//            float[] fArr = this.y;
//            System.arraycopy(fArr, 0, this.z, 0, fArr.length);
//        }
//        this.y = aVar.f9715b;
//        com.netease.cloudmusic.module.g.a aVar2 = this.I;
//        if (aVar2 != null) {
//            this.G.a(aVar2);
//        }
//        this.I = a2;
//        this.C = SystemClock.uptimeMillis();
//        invalidate();
//    }

    @Override
    public void onDraw(Canvas canvas) {
        int width = getWidth() / 2;
        int height = getHeight() / 2;
        boolean z2 = false;
        if (this.mCircleRadius == 0) {
            View artView = null;
            this.mCircleRadius = (artView != null ? Math.max(artView.getWidth(), artView.getHeight()) / 2 : 0) + UIUtils.dip2px(getContext(),15.0f);
//            this.v = Math.min((Math.min(width, height) + UIUtils.dip2px(getContext(),30.0f)) - this.mCircleRadius, n);
        }
        int save = canvas.save();
        canvas.translate((float) width, (float) height);
        if (this.f9708e) {
            canvas.drawCircle(0.0f, 0.0f, (float) this.mCircleRadius, this.mPaint);
            a(canvas);
        } else {
            if (this.mPath == null) {
                this.mPath = new Path();
                this.mPathPaint.setPathEffect(new CornerPathEffect((float) this.mCircleRadius));
            }
            float uptimeMillis = (float) (SystemClock.uptimeMillis() - this.C);
            float f2 = this.B;
            float f3 = uptimeMillis >= f2 ? 1.0f : uptimeMillis / f2;
            boolean z3 = f3 < 1.0f;
            for (int i2 = 0; i2 < this.f9704a; i2++) {
                this.mPath.reset();
                int i3 = 0;
                float[] fArr = this.y;
                for (; i3 < fArr.length; i3++) {
                    float[] fArr2 = this.z;
                    float f5 = mCircleRadius + ((fArr2[i3] + ((fArr[i3] - fArr2[i3]) * f3)) * (1.0f - (((float) i2) * 0.2f)));
                    double d2 = this.A * ((double) i3);
                    double d3 = (double) f5;
                    float cos = (float) (Math.cos(d2) * d3);
                    float sin = (float) (d3 * Math.sin(d2));
                    if (i3 == 0) {
                        this.mPath.moveTo(cos, sin);
                    } else {
                        this.mPath.lineTo(cos, sin);
                    }
                }

                this.mPath.close();
                this.mPathPaint.setColor(this.q[i2]);
                if (i2 == 0) {
                    if (a(canvas)) {
                        z3 = true;
                    }
                } else if (i2 == this.f9704a - 1) {
                    canvas.rotate(2.0f);
                }
                canvas.drawPath(this.mPath, this.mPathPaint);
            }
            z2 = z3;
        }
        canvas.restoreToCount(save);
        if (z2) {
            postInvalidateOnAnimation();
        }
    }

    @Override
    public void onDetachedFromWindow() {
        this.D.getLooper().quit();
        this.mUpdateHandler.removeCallbacksAndMessages(null);
        super.onDetachedFromWindow();
    }

}
