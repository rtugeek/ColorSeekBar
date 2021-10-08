package com.rtugeek.android.colorseekbar;

public interface OnColorChangeListener {
    /**
     * @param progress between 0-maxValue
     * @param color    return the color
     */
    void onColorChangeListener(int progress, int color);
}
