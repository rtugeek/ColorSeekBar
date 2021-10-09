package com.rtugeek.android.colorseekbar;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.rtugeek.android.colorseekbar.thumb.ThumbDrawer;

public abstract class BaseSeekBar extends View {
    /**
     * Rectangle that specified thumb's bounds
     */
    protected final RectF thumbRect = new RectF();
    /**
     * Rectangle that specified seekbar's bounds
     */
    protected final RectF barRect = new RectF();
    /**
     * Rectangle that detect seekbar's touch event
     * in horizontal, the rect's height calculate by Math.max(barHeight,thumbDrawer.getHeight)
     * in vertical, the rect's width calculate by Math.max(barHeight,thumbDrawer.getWidth)
     */
    protected final RectF touchDetectRect = new RectF();
    /**
     * Rectangle that specified thumb's allowed drag bounds
     */
    protected final RectF thumbDragRect = new RectF();

    protected int borderColor;
    protected int borderSize;
    protected int borderRadius;
    protected int progress;
    protected int maxProgress;
    protected ThumbDrawer thumbDrawer;
    protected int barHeight = 10;

    protected boolean showThumb = true;
    protected boolean vertical = false;

    protected final Paint borderPaint = new Paint();
    protected final Paint barRectPaint = new Paint();


    public BaseSeekBar(Context context) {
        super(context);
        initPaint(context, null, 0, 0);
    }

    public BaseSeekBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint(context, attrs, 0, 0);
    }

    public BaseSeekBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint(context, attrs, defStyleAttr, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BaseSeekBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initPaint(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Logger.i("onMeasure:w-" + widthMeasureSpec + " h-" + heightMeasureSpec);
        int mViewWidth = widthMeasureSpec;
        int mViewHeight = heightMeasureSpec;

        int widthSpeMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpeMode = MeasureSpec.getMode(heightMeasureSpec);

        Logger.i("widthSpeMode:");
        Logger.spec(widthSpeMode);
        Logger.i("heightSpeMode:");
        Logger.spec(heightSpeMode);

        if (isVertical()) {
            if (widthSpeMode == MeasureSpec.AT_MOST || widthSpeMode == MeasureSpec.UNSPECIFIED) {
                int thumbWidth = thumbDrawer == null ? 0 : thumbDrawer.getWidth();
                mViewWidth = Math.max(thumbWidth, barHeight) + widthPadding;
                setMeasuredDimension(mViewWidth, mViewHeight);
            }
        } else {
            if (heightSpeMode == MeasureSpec.AT_MOST || heightSpeMode == MeasureSpec.UNSPECIFIED) {
                int thumbHeight = thumbDrawer == null ? 0 : thumbDrawer.getHeight();
                mViewHeight = Math.max(thumbHeight, barHeight) + widthPadding;
                setMeasuredDimension(mViewWidth, mViewHeight);
            }
        }
        init();
    }

    private int widthPadding = 5;

    private int calculatePadding() {
        float thumbRadius = getThumbRadius();
        if (barHeight > thumbRadius) {
            return (int) thumbRadius;
        } else {
            return (int) (thumbRadius - borderRadius);
        }
    }

    /**
     * init {@link #barRect},{@link #thumbDragRect}
     */
    protected void init() {
        Logger.i("init");
        this.borderRadius = (int) Math.min(barHeight / 2f, this.borderRadius);
        int mPaddingSize = calculatePadding() + widthPadding;
        if (isVertical()) {
            //init size
            float barTop = mPaddingSize;
            int barLeft = getWidth() / 2 - barHeight / 2;
            float barRight = barLeft + barHeight;
            float barBottom = getHeight() - mPaddingSize;

            barRect.set(barLeft, barTop, barRight, barBottom);
            thumbDragRect.set(barRect.centerX(), barTop + borderRadius, barRect.centerX() + 1, barBottom - borderRadius);

            int dragWidth = Math.max(barHeight, thumbDrawer.getWidth());
            float dragLeft = thumbDragRect.left - dragWidth / 2f;
            touchDetectRect.set(dragLeft, barTop, dragLeft + dragWidth, barBottom);
        } else {
            //init size
            int viewBottom = getHeight() - mPaddingSize;
            int viewRight = getWidth() - mPaddingSize;
            //init left right top bottom
            int barLeft = mPaddingSize;
            int barRight = viewRight;
            float barTop = getHeight() / 2f - barHeight / 2f;
            float barBottom = barTop + barHeight;

            barRect.set(barLeft, barTop, barRight, barBottom);

            thumbDragRect.set(barLeft + borderRadius, barRect.centerY(), barRect.right - borderRadius, barRect.centerY() + 1);

            int dragHeight = Math.max(barHeight, thumbDrawer.getHeight());
            float dragTop = barRect.centerY() - dragHeight / 2f;
            touchDetectRect.set(barLeft, dragTop, barRight, dragTop + dragHeight);
        }
    }

    protected int getCenterY() {
        return getHeight() / 2;
    }

    protected int getCenterX() {
        return getWidth() / 2;
    }

    protected void initPaint(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        barRectPaint.setAntiAlias(true);
        borderPaint.setAntiAlias(true);
        borderPaint.setStyle(Paint.Style.STROKE);
    }

    public int getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
        borderPaint.setColor(this.borderColor);
        invalidate();
    }

    public int getBorderSize() {
        return borderSize;
    }

    /**
     * Set border size radius to the given value in pixel unit.
     *
     * @param borderSize
     */
    public void setBorderSize(int borderSize) {
        this.borderSize = borderSize;
        borderPaint.setStrokeWidth(borderSize);
        invalidate();
    }

    public int getBorderRadius() {
        return borderRadius;
    }

    /**
     * Set border radius radius to the given value in pixel unit.
     * If the size larger then barHeight/2, it will be reset to barHeight/2
     *
     * @param borderRadius
     */
    public void setBorderRadius(int borderRadius) {
        this.borderRadius = borderRadius;
        requestLayout();
    }

    public boolean isShowThumb() {
        return showThumb;
    }

    public void setShowThumb(boolean showThumb) {
        this.showThumb = showThumb;
        invalidate();
    }

    public int getProgress() {
        return progress;
    }

    /**
     * Set the position of color bar, if out of bounds , it will be 0 or maxProgress;
     */
    public void setProgress(int progress) {
        this.progress = Math.min(progress, maxProgress);
        this.progress = Math.max(this.progress, 0);
        invalidate();
    }

    public ThumbDrawer getThumbDrawer() {
        return thumbDrawer;
    }

    /**
     * set {@link ThumbDrawer}
     *
     * @param thumbDrawer
     */
    public void setThumbDrawer(ThumbDrawer thumbDrawer) {
        this.thumbDrawer = thumbDrawer;
        if (thumbDrawer != null) {
            thumbRect.set(0, 0, thumbDrawer.getWidth(), thumbDrawer.getHeight());
        }
        requestLayout();
    }

    public int getBarHeight() {
        return barHeight;
    }

    /**
     * set bar height in pixels
     *
     * @param barHeight
     */
    public void setBarHeight(int barHeight) {
        this.barHeight = barHeight;
        requestLayout();
    }

    public int getMaxProgress() {
        return maxProgress;
    }

    public void setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
        invalidate();
    }

    protected float getThumbRadius() {
        if (thumbDrawer == null) {
            return 0;
        }
        return Math.min(thumbDrawer.getHeight(), thumbDrawer.getWidth()) / 2f;
    }

    public boolean isVertical() {
        return vertical;
    }

    public void setVertical(boolean vertical) {
        this.vertical = vertical;
        requestLayout();
    }


    protected int dp2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private boolean isMovingColorBar = false;

    protected abstract void onBarTouch(int progress);

    private float calculateTouchProgress(float x) {
        if (isVertical()) {
            return (x - touchDetectRect.top) / touchDetectRect.height() * maxProgress;
        } else {
            return (x - touchDetectRect.left) / touchDetectRect.width() * maxProgress;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled()) {
            return true;
        }
        float x = vertical ? event.getY() : event.getX();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (touchDetectRect.contains(event.getX(), event.getY())) {
                    isMovingColorBar = true;
                    onBarTouch((int) calculateTouchProgress(x));
                }
                break;
            case MotionEvent.ACTION_MOVE:
                getParent().requestDisallowInterceptTouchEvent(true);
                if (isMovingColorBar) {
                    onBarTouch((int) calculateTouchProgress(x));
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                performClick();
                isMovingColorBar = false;
                break;
            default:
        }
        return true;
    }
}

