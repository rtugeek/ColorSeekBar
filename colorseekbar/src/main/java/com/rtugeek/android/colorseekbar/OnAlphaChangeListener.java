package com.rtugeek.android.colorseekbar;

public interface OnAlphaChangeListener {
    /**
     * @param alphaBarPosition between 0-maxValue
     * @param alpha between 0-255
     */
    void onAlphaChangeListener( int alphaBarPosition, int alpha);
}
