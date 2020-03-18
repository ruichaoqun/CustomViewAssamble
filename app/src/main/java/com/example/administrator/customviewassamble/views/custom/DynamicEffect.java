package com.example.administrator.customviewassamble.views.custom;

import android.util.Pair;

/**
 * @author Rui Chaoqun
 * @date :2020/3/18 10:04
 * description:
 */
public interface DynamicEffect {
    public static final int o = -1;
    public static final double p = 6.283185307179586d;

    void X_();

    int getRate(VisualizerInterface visualizerInterface);

    void onFftDataCapture(byte[] fft, int samplingRate);

    void a(boolean z);

    int getCaptureSizeRange(VisualizerInterface dVar);

    void onWaveFormDataCapture(byte[] waveform, int samplingRate);

    boolean isFft();

    boolean isWaveform();

    Pair<Integer, Integer> d();

    void setColor(int i2);

}
