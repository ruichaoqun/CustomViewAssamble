package com.example.administrator.customviewassamble.views.custom;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;

/**
 * @author Rui Chaoqun
 * @date :2020/3/18 9:37
 * description:
 */
public class VisualizerLayout extends FrameLayout {

    private View mEffectView;

    private DynamicEffect mDynamicEffect;
    
    private VisualizerInterface mVisualizerInterface;

    private VisualizerInterface.DataCaptureListener mDataCaptureListener;

    private int mColor;

    private int sessionId;

    private ObjectAnimator mAnimator;

    private long lastAnimateTime;



    public VisualizerLayout(@NonNull Context context) {
        this(context,null);
    }

    public VisualizerLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public VisualizerLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mColor = -1;
        sessionId = -1;
    }

    public void addDynamicView(View view,int width,int height){
        if(mEffectView != view){
            if(mEffectView != null && mEffectView.getParent() == this){
                removeView(this.mEffectView);
            }
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(width, height);
            layoutParams.gravity = Gravity.CENTER;
            addView(view, layoutParams);
            this.mEffectView = view;
        }
    }

    public View getEffectView(){
        return  mEffectView;
    }

    /**
     * 设置View
     * @param view
     * @return
     */
    public int addDynamicView(DynamicEffect view) {
        int i2 = 0;
        if (this.mDynamicEffect != view) {
            //设置新的动效动效
            boolean z = mVisualizerInterface != null && mVisualizerInterface.getEnabled();
            b();
            VisualizerInterface dVar3 = null;
            //清空回调
            if (mVisualizerInterface != null) {
                mVisualizerInterface.setDataCaptureListener( null, 0, false, false);
            }
            //移除动效view
            if (mDynamicEffect != null) {
                removeView((View) mDynamicEffect);
            }
            if (this.mEffectView != null) {
                Pair<Integer, Integer> pair = view.d();
                if (((Integer) pair.first).intValue() > 0 && ((Integer) pair.second).intValue() > 0) {
                    ViewGroup.LayoutParams layoutParams = this.mEffectView.getLayoutParams();
                    layoutParams.width = ((Integer) pair.first).intValue();
                    layoutParams.height = ((Integer) pair.second).intValue();
                }
            }
            addView((View) view, 0, new FrameLayout.LayoutParams(-1, -1));
            if (mVisualizerInterface != null) {
                if (mDynamicEffect == null || mDynamicEffect.b(mVisualizerInterface) != view.b(this.mVisualizerInterface)) {
                    this.mVisualizerInterface.release();
                    try {
                        dVar3 = this.mVisualizerInterface.getNewVisualizer();
                        dVar3.setCaptureSize(view.b(dVar3));
                    } catch (Throwable th) {
                        th.printStackTrace();
                        i2 = (!(th instanceof RuntimeException) || !th.getMessage().contains("-3")) ? -1 : -3;
                    }
                    this.mVisualizerInterface = dVar3;
                }
                    mVisualizerInterface.setDataCaptureListener(this.mDataCaptureListener, view.getRate(mVisualizerInterface), view.isWaveform(), view.isFft());
            }
            this.mDynamicEffect = view;
            this.mDynamicEffect.setColor(this.mColor);
            if (z) {
                a();
            }
        }
        return i2;
    }

    public int setVisualizer(int sessionId) {
        if (this.mVisualizerInterface == null || this.sessionId != sessionId) {
            if (mVisualizerInterface != null) {
                mVisualizerInterface.release();
                this.mVisualizerInterface = null;
            }
            try {
                this.mVisualizerInterface = new VisualizerEntity(sessionId);
                if (this.mVisualizerInterface.getEnabled()) {
                    this.mVisualizerInterface.setEnabled(false);
                }
                this.sessionId = sessionId;
                initVisualListener();
            } catch (Throwable th) {
                th.printStackTrace();
                return (!(th instanceof RuntimeException) || !th.getMessage().contains("-3")) ? -1 : -3;
            }
        }
        return 0;
    }

    public void setVisualizer(VisualizerInterface v) {
        if (mVisualizerInterface != v) {
            if (mVisualizerInterface != null) {
                mVisualizerInterface.release();
                this.mVisualizerInterface = null;
            }
            this.mVisualizerInterface = v;
            initVisualListener();
        }
    }

    /**
     * 对Visualizer设置监听
     */
    private void initVisualListener() {
        if (this.mDataCaptureListener == null) {
            this.mDataCaptureListener = new VisualizerInterface.DataCaptureListener() {
                @Override
                public void onWaveFormDataCapture(byte[] waveform, int samplingRate) {
                    if (mDynamicEffect != null) {
                        mDynamicEffect.onWaveFormDataCapture(waveform, samplingRate);
                    }
                }

                @Override
                public void onFftDataCapture(byte[] fft, int samplingRate) {
                    if (mDynamicEffect != null) {
                        mDynamicEffect.onFftDataCapture(fft, samplingRate);
                    }
                }
            };
        }
        if (mDynamicEffect != null) {
            mVisualizerInterface.setCaptureSize(mDynamicEffect.b(mVisualizerInterface));
            mVisualizerInterface.setDataCaptureListener(this.mDataCaptureListener, this.mDynamicEffect.getRate(mVisualizerInterface), this.mDynamicEffect.isWaveform(), this.mDynamicEffect.isFft());
        }
    }

    public void a() {
        if (mVisualizerInterface != null) {
            mVisualizerInterface.setEnabled(true);
        }
        if (mDynamicEffect != null) {
            mDynamicEffect.X_();
        }
    }

    public void b() {
        if (mVisualizerInterface != null) {
            mVisualizerInterface.setEnabled(false);
        }
        if (mDynamicEffect != null) {
            mDynamicEffect.a(true);
        }
    }

    public void c() {
        if (mVisualizerInterface != null) {
            mVisualizerInterface.e();
        }
        if (mDynamicEffect != null) {
            mDynamicEffect.a(false);
        }
    }

    public void satrtAnimate(boolean isContinue) {
        if (mEffectView != null) {
            if (this.mAnimator == null) {
                this.mAnimator = ObjectAnimator.ofFloat(mEffectView, ROTATION, new float[]{0.0f, 360.0f}).setDuration(25000);
                this.mAnimator.setRepeatCount(ObjectAnimator.INFINITE);
                this.mAnimator.setInterpolator(new LinearInterpolator());
            }
            if (!this.mAnimator.isRunning()) {
                this.mAnimator.setCurrentPlayTime(isContinue ? this.lastAnimateTime : 0);
                this.mAnimator.start();
            }
        }
    }

    public void stopAnimate(boolean resetTime) {
        if (mAnimator != null) {
            this.lastAnimateTime = mAnimator.getCurrentPlayTime();
            this.mAnimator.cancel();
            if (resetTime) {
                this.mAnimator.setCurrentPlayTime(0);
            }
        }
    }

    @Override
    public void onDetachedFromWindow() {
        if (mVisualizerInterface != null) {
            mVisualizerInterface.release();
            this.mVisualizerInterface = null;
        }
        super.onDetachedFromWindow();
    }

}
