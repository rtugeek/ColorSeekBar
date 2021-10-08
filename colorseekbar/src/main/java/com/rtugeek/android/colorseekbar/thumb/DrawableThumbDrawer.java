package com.rtugeek.android.colorseekbar.thumb;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.widget.SeekBar;

import com.rtugeek.android.colorseekbar.BaseSeekBar;

/**
 * Draw thumb from drawable
 */
public class DrawableThumbDrawer implements ThumbDrawer {
    private final Drawable drawable;
    private int width;
    private int height;

    public DrawableThumbDrawer(Drawable drawable, int width, int height) {
        this.drawable = drawable;
        this.width = width;
        this.height = height;
    }

    public DrawableThumbDrawer(Drawable drawable) {
        this.drawable = drawable;
        this.width = drawable.getIntrinsicWidth();
        this.height = drawable.getIntrinsicHeight();
    }

    @Override
    public void onDrawThumb(RectF thumbBounds, BaseSeekBar seekBar, Canvas canvas) {
        Rect rect = new Rect((int) thumbBounds.left, (int) thumbBounds.top, (int) thumbBounds.right, (int) thumbBounds.bottom);
        drawable.setBounds(rect);
        drawable.draw(canvas);
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
