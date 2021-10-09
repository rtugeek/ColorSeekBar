package com.rtugeek.android.colorseekbar.thumb;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

import com.rtugeek.android.colorseekbar.BaseSeekBar;
import com.rtugeek.android.colorseekbar.ColorSeekBar;

public class DefaultThumbDrawer implements ThumbDrawer {

    private final Paint thumbStrokePaint = new Paint();
    private final Paint thumbSolidPaint = new Paint();
    private int size;
    private int borderColor = Color.BLACK;
    private int solidColor = Color.BLACK;
    private int borderSize;
    private final Path outerCircle = new Path();
    private final Path innerCircle = new Path();

    public DefaultThumbDrawer(int size, int solidColor, int borderColor) {
        this.size = size;
        this.borderColor = borderColor;
        thumbStrokePaint.setAntiAlias(true);
        thumbSolidPaint.setAntiAlias(true);

        thumbStrokePaint.setStyle(Paint.Style.STROKE);

        setBorderColor(borderColor);
        setSolidColor(solidColor);
        setBorderSize(3);
    }

    public int getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
        thumbStrokePaint.setColor(borderColor);
    }

    public int getBorderSize() {
        return borderSize;
    }

    public int getSolidColor() {
        return solidColor;
    }

    public void setSolidColor(int solidColor) {
        this.solidColor = solidColor;
        thumbSolidPaint.setColor(solidColor);
    }

    public void setBorderSize(int borderSize) {
        this.borderSize = borderSize;
        thumbStrokePaint.setStrokeWidth(borderSize);
    }

    @Override
    public void onDrawThumb(RectF thumbBounds, BaseSeekBar seekBar, Canvas canvas) {
        float centerX = thumbBounds.centerX();
        float centerY = thumbBounds.centerY();
        outerCircle.reset();
        innerCircle.reset();
//        if (seekBar instanceof ColorSeekBar) {
//            thumbSolidPaint.setColor(((ColorSeekBar) seekBar).getColor());
//        }
        float outerRadius = thumbBounds.height() / 2f;
        outerCircle.addCircle(centerX, centerY, outerRadius, Path.Direction.CW);
        innerCircle.addCircle(centerX, centerY, outerRadius - 10, Path.Direction.CW);
        outerCircle.op(innerCircle, Path.Op.DIFFERENCE);
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
