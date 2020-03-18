package com.example.administrator.customviewassamble.views.custom;

/**
 * @author Rui Chaoqun
 * @date :2020/3/18 9:16
 * description:
 */
public interface VisualizerInterface {

    public interface DataCaptureListener {
        void onWaveFormDataCapture(byte[] waveform, int samplingRate);

        void onFftDataCapture(byte[] fft, int samplingRate);
    }

    int setCaptureSize(int size);

    int setDataCaptureListener(DataCaptureListener mlistener, int rate, boolean waveform, boolean fft);

    int setEnabled(boolean enabled);

    void release();

    boolean getEnabled();

    int[] getCaptureSizeRange();

    int getMaxCaptureRate();

    VisualizerInterface getNewVisualizer();

    void e();
}
