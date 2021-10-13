package com.rtugeek.android.colorseekbar.thumb;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

import com.rtugeek.android.colorseekbar.AlphaSeekBar;
import com.rtugeek.android.colorseekbar.BaseSeekBar;
import com.rtugeek.android.colorseekbar.ColorSeekBar;

public class DefaultThumbDrawer implements ThumbDrawer {

    private final Paint thumbStrokePaint = new Paint();
    private final Paint thumbSolidPaint = new Paint();
    private final Paint thumbColorPaint = new Paint();
    private int size;
    private int ringBorderColor = Color.BLACK;
    private int ringSolidColor = Color.BLACK;
    private int ringBorderSize;
    private final Path outerCircle = new Path();
    private final Path innerCircle = new Path();
    private int ringSize = 10;

    public DefaultThumbDrawer(int size, int ringSolidColor, int ringBorderColor) {
        this.size = size;
        this.ringBorderColor = ringBorderColor;
        thumbStrokePaint.setAntiAlias(true);
        thumbSolidPaint.setAntiAlias(true);
        thumbColorPaint.setAntiAlias(true);

        thumbStrokePaint.setStyle(Paint.Style.STROKE);

        setRingBorderColor(ringBorderColor);
        setRingSolidColor(ringSolidColor);
        setRingBorderSize(3);
    }

    public int getRingBorderColor() {
        return ringBorderColor;
    }

    public void setRingBorderColor(int ringBorderColor) {
        this.ringBorderColor = ringBorderColor;
        thumbStrokePaint.setColor(ringBorderColor);
    }

    public int getRingBorderSize() {
        return ringBorderSize;
    }

    public int getRingSolidColor() {
        return ringSolidColor;
    }

    public void setRingSolidColor(int ringSolidColor) {
        this.ringSolidColor = ringSolidColor;
        thumbSolidPaint.setColor(ringSolidColor);
    }

    public void setRingBorderSize(int ringBorderSize) {
        this.ringBorderSize = ringBorderSize;
        thumbStrokePaint.setStrokeWidth(ringBorderSize);
    }

    public int getRingSize() {
        return ringSize;
    }

    public void setRingSize(int ringSize) {
        this.ringSize = ringSize;
    }

    @Override
    public void onDrawThumb(RectF thumbBounds, BaseSeekBar seekBar, Canvas canvas) {
        float centerX = thumbBounds.centerX();
        float centerY = thumbBounds.centerY();
        outerCircle.reset();
        innerCircle.reset();
        if (seekBar instanceof ColorSeekBar) {
            thumbColorPaint.setColor(((ColorSeekBar) seekBar).getColor());
        } else if (seekBar instanceof AlphaSeekBar) {
            thumbColorPaint.setAlpha(((AlphaSeekBar) seekBar).getAlphaValue());
        }
        float outerRadius = thumbBounds.height() / 2f;
        float innerRadius = outerRadius - ringSize;
        outerCircle.addCircle(centerX, centerY, outerRadius, Path.Direction.CW);
        innerCircle.addCircle(centerX, centerY, innerRadius, Path.Direction.CW);
        outerCircle.op(innerCircle, Path.Op.DIFFERENCE);
        canvas.drawCircle(centerX, centerY, innerRadius, thumbColorPaint);
        canvas.drawPath(outerCircle, thumbSolidPaint);
        canvas.drawPath(outerCircle, thumbStrokePaint);
    }

    @Override
    public int getWidth() {
        return size;
    }

    @Override
    public int getHeight() {
        return size;
    }
}
