package com.example.administrator.customviewassamble.views.custom;

import android.media.audiofx.Visualizer;

/**
 * @author Rui Chaoqun
 * @date :2020/3/18 9:16
 * description:
 */
public class VisualizerEntity implements VisualizerInterface {
    private int mAudioSessionId;

    private Visualizer mVisualizer;

    private Visualizer.OnDataCaptureListener mCaptureListener;

    private VisualizerInterface.DataCaptureListener mDataCaptureListener;


    public VisualizerEntity(int sessionId) {
        this.mAudioSessionId = sessionId;
        this.mVisualizer = new Visualizer(sessionId);
    }

    @Override
    public void release() {
        this.mVisualizer.release();
    }

    @Override
    public int setEnabled(boolean enabled) {
        return this.mVisualizer.setEnabled(enabled);
    }

    @Override
    public boolean getEnabled() {
        return this.mVisualizer.getEnabled();
    }

    @Override
    public int[] getCaptureSizeRange() {
        return Visualizer.getCaptureSizeRange();
    }

    @Override
    public int getMaxCaptureRate() {
        return Visualizer.getMaxCaptureRate();
    }

    @Override
    public int setCaptureSize(int size) {
        return this.mVisualizer.setCaptureSize(size);
    }

    @Override
    public int setDataCaptureListener(VisualizerInterface.DataCaptureListener listener, int rate, boolean waveform, boolean fft) {
        this.mDataCaptureListener = listener;
        if (listener == null) {
            return this.mVisualizer.setDataCaptureListener(null, rate, waveform, fft);
        }
        if (this.mCaptureListener == null) {
            this.mCaptureListener = new Visualizer.OnDataCaptureListener() {
                @Override
                public void onWaveFormDataCapture(Visualizer visualizer, byte[] waveform, int samplingRate) {
                    if (VisualizerEntity.this.mDataCaptureListener != null) {
                        VisualizerEntity.this.mDataCaptureListener.onWaveFormDataCapture(waveform, samplingRate);
                    }
                }

                @Override
                public void onFftDataCapture(Visualizer visualizer, byte[] fft, int samplingRate) {
                    if (VisualizerEntity.this.mDataCaptureListener != null) {
                        VisualizerEntity.this.mDataCaptureListener.onFftDataCapture(fft, samplingRate);
                    }
                }
            };
        }
        return this.mVisualizer.setDataCaptureListener(this.mCaptureListener, rate, waveform, fft);
    }

    @Override
    public VisualizerInterface getNewVisualizer() {
        return new VisualizerEntity(this.mAudioSessionId);
    }

    @Override
    public void e() {

    }

}
