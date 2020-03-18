package com.example.administrator.customviewassamble.views.custom;

/**
 * @author Rui Chaoqun
 * @date :2020/3/18 17:02
 * description:
 */
public class a {
    private c<VisuralData> mVisuralDatac = new c<>();

    public a() { }

    public synchronized VisuralData a(byte[] fft, int length) {
        VisuralData visuralData;
        visuralData = this.mVisuralDatac.a();
        if (visuralData == null) {
            visuralData = new VisuralData(new byte[length]);
        }
        System.arraycopy(fft, 0, visuralData.data, 0, length);
        return visuralData;
    }

    /* access modifiers changed from: package-private */
    public synchronized void a(VisuralData aVar) {
        this.mVisuralDatac.a(aVar);
    }

  
    public static class VisuralData extends b<VisuralData> {

        /* renamed from: b  reason: collision with root package name */
        public final byte[] data;

        VisuralData(byte[] fft) {
            this.data = fft;
        }
    }

}
