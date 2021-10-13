package com.rtugeek.android.colorseekbar;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.IntRange;

import com.rtugeek.android.colorseekbar.thumb.DefaultThumbDrawer;


public class AlphaSeekBar extends BaseSeekBar {

    private float mGridSize = 30;
    private final RectF mGrid = new RectF(0, 0, mGridSize, mGridSize);

    public enum Direction {LEFT_TO_RIGHT, RIGHT_TO_LEFT, TOP_TO_BOTTOM, BOTTOM_TO_TOP}

    private final int GRID_WHITE = Color.WHITE;
    private final int GRID_GREY = 0xFFEDEDED;

    private OnAlphaChangeListener listener;
    private Direction direction;
    private boolean mShowGrid = true;
    private final Path mClipPath = new Path();

    private Paint mGridRectPaint = new Paint();

    public AlphaSeekBar(Context context) {
        super(context);
    }

    public AlphaSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AlphaSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AlphaSeekBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected void initPaint(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super.initPaint(context, attrs, defStyleAttr, defStyleRes);
        //get attributes
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AlphaSeekBar, defStyleAttr, defStyleRes);

        progress = a.getInteger(R.styleable.AlphaSeekBar_alphaSeekBarProgress, 0);
        vertical = a.getBoolean(R.styleable.AlphaSeekBar_alphaSeekBarVertical, false);
        showThumb = a.getBoolean(R.styleable.AlphaSeekBar_alphaSeekBarShowThumb, true);
        barHeight = a.getDimensionPixelSize(R.styleable.AlphaSeekBar_alphaSeekBarHeight, 12);
        borderColor = a.getColor(R.styleable.AlphaSeekBar_alphaSeekBarBorderColor, Color.BLACK);
        borderRadius = a.getDimensionPixelSize(R.styleable.AlphaSeekBar_alphaSeekBarRadius, barHeight / 2);
        borderSize = a.getDimensionPixelSize(R.styleable.AlphaSeekBar_alphaSeekBarBorderSize, 0);
        mGridSize = a.getDimension(R.styleable.AlphaSeekBar_alphaSeekBarSizeGrid, 30);
        mShowGrid = a.getBoolean(R.styleable.AlphaSeekBar_alphaSeekBarShowGrid, true);
        maxProgress = a.getInteger(R.styleable.AlphaSeekBar_alphaSeekBarMaxProgress, 255);
        direction = Direction.values()[a.getInt(R.styleable.AlphaSeekBar_alphaSeekBarDirection, 1)];
        setBarHeight(a.getDimensionPixelSize(R.styleable.AlphaSeekBar_alphaSeekBarHeight, dp2px(10)));
        a.recycle();

        mGridRectPaint = new Paint();
        mGridRectPaint.setAntiAlias(true);
        if (thumbDrawer == null) {
            int minThumbSize = dp2px(16);
            int defaultThumbSize = barHeight + dp2px(8);
            DefaultThumbDrawer thumbDrawer = new DefaultThumbDrawer(Math.max(minThumbSize, defaultThumbSize), Color.WHITE, Color.BLACK);
            thumbDrawer.setRingSize(dp2px(2));
            thumbDrawer.setRingBorderSize(dp2px(1));
            setThumbDrawer(thumbDrawer);
        }
    }

    @Override
    public void setMaxProgress(int maxProgress) {
        this.maxProgress = 255;
    }

    @Override
    protected void init() {
        super.init();
        Logger.i("init");
        int startColor = Color.argb(0, 0, 0, 0);
        int endColor = Color.argb(255, 0, 0, 0);
        int[] toAlpha = {startColor, endColor};
        LinearGradient mColorGradient;
        if (isVertical()) {
            //init paint
            mColorGradient = new LinearGradient(0, 0, 0, barRect.height(), toAlpha, null, Shader.TileMode.CLAMP);
        } else {
            mColorGradient = new LinearGradient(0, 0, barRect.width(), 0, toAlpha, null, Shader.TileMode.CLAMP);
        }
        barRectPaint.setShader(mColorGradient);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Logger.i("onSizeChanged:w-" + w + " h-" + h);
        init();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        Logger.i("onDraw");
        //clear
        canvas.drawColor(Color.TRANSPARENT);

        if (mShowGrid) {
            canvas.save();
            // draw transparent grid
            mClipPath.reset();
            mClipPath.addRoundRect(barRect, borderRadius, borderRadius, Path.Direction.CW);
            canvas.clipPath(mClipPath);
            int rowCount = 0;
            while (rowCount * mGridSize <= barRect.height() + mGridSize) {
                int columnCount = 0;
                boolean odd = rowCount % 2 == 0;
                mGrid.offsetTo(barRect.left, barRect.top + mGridSize * rowCount);
                mGridRectPaint.setColor(odd ? GRID_GREY : GRID_WHITE);
                while (columnCount * mGridSize < barRect.width() + mGridSize) {
                    canvas.drawRect(mGrid, mGridRectPaint);
                    mGridRectPaint.setColor(mGridRectPaint.getColor() == GRID_WHITE ? GRID_GREY : GRID_WHITE);
                    mGrid.offset(mGridSize, 0);
                    columnCount++;
                }
                rowCount++;
            }
            canvas.restore();
        }
        canvas.save();
        if (isVertical()) {
            if (direction == Direction.BOTTOM_TO_TOP) {
                canvas.scale(1f, -1f, getWidth() / 2f, getHeight() / 2f);
            }
        } else {
            if (direction == Direction.RIGHT_TO_LEFT) {
                canvas.scale(-1f, 1f, getWidth() / 2f, getHeight() / 2f);
            }
        }
        canvas.drawRoundRect(barRect, borderRadius, borderRadius, barRectPaint);
        canvas.restore();
        //
        //draw color bar
        if (borderSize > 0) {
            canvas.drawRoundRect(barRect, borderRadius, borderRadius, borderPaint);
        }

        if (showThumb && thumbDrawer != null) {
            float offsetX = 0f;
            float offsetY = 0f;
            if (isVertical()) {
                float thumbPosition = (float) progress / maxProgress * thumbDragRect.height();
                offsetX = thumbDragRect.left - thumbDrawer.getWidth() / 2f;
                offsetY = thumbPosition + thumbDragRect.top - thumbDrawer.getHeight() / 2f;
                if (offsetY > thumbDragRect.bottom) offsetY = thumbDragRect.bottom;
            } else {
                float thumbPosition = (float) progress / maxProgress * thumbDragRect.width();
                offsetX = thumbPosition + thumbDragRect.left - thumbDrawer.getWidth() / 2f;
                if (offsetX > thumbDragRect.right) offsetX = thumbDragRect.left;
                offsetY = thumbDragRect.top - thumbDrawer.getHeight() / 2f;
            }

            thumbRect.offsetTo(offsetX, offsetY);
            thumbDrawer.onDrawThumb(thumbRect, this, canvas);
        }
        super.onDraw(canvas);
    }

    @Override
    protected void onBarTouch(int progress) {
        setProgress(progress);
        if (listener != null) {
            listener.onAlphaChangeListener(progress, getAlphaValue());
        }
    }

    public int getAlphaValue() {
        float percent = progress / (float) maxProgress;
        if (isVertical()) {
            if (direction == Direction.BOTTOM_TO_TOP) {
                percent = 1 - percent;
            }
        } else {
            if (direction == Direction.RIGHT_TO_LEFT) {
                percent = 1 - percent;
            }
        }
        return (int) (percent * 255);
    }

    public void setAlphaValue(@IntRange(from = 0, to = 255) int alpha) {
        setProgress((int) (alpha / 255f * maxProgress));
        if (listener != null) {
            listener.onAlphaChangeListener(progress, getAlphaValue());
        }
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
        invalidate();
    }

    /**
     * @param listener
     */
    public void setOnAlphaChangeListener(OnAlphaChangeListener listener) {
        this.listener = listener;
    }

}