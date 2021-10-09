package com.rtugeek.android.colorseekbar;

public interface OnAlphaChangeListener {
    /**
     * @param progress between 0-maxValue
     * @param alpha between 0-255
     */
    void onAlphaChangeListener( int progress, int alpha);
}
