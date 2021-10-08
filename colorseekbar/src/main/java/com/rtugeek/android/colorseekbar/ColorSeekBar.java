package com.rtugeek.android.colorseekbar;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.ArrayRes;

import com.rtugeek.android.colorseekbar.thumb.DefaultThumbDrawer;

import java.util.ArrayList;
import java.util.List;

/**
 * A seek bar that draws gradient color
 *
 * @author ℉ <rtugeek@gmail.com>
 */
public class ColorSeekBar extends BaseSeekBar {
    private int[] mColorSeeds = new int[]{0xFF000000, 0xFF9900FF, 0xFF0000FF, 0xFF00FF00, 0xFF00FFFF,
            0xFFFF0000, 0xFFFF00FF, 0xFFFF6600, 0xFFFFFF00, 0xFFFFFFFF, 0xFF000000};

    private OnColorChangeListener mOnColorChangeLister;
    private Context mContext;
    private boolean mMovingColorBar;
    /**
     * A bitmap to draw gradient color, this bitmap make it easier to get selected color by {@link Bitmap#getPixel(int, int)}.
     * {@link BaseSeekBar#thumbDragRect}
     */
    private Bitmap mCachedBitmap;

    /**
     * Specify {@link #mCachedBitmap}'s rectangle, this rectangle equals to {@link #thumbDragRect}
     * but the coordinate offset to (0,0)
     */
    private final RectF mCachedBitmapRect = new RectF();
    private int mColorsToInvoke = -1;

    private final Paint mBitmapRectPaint = new Paint();

    private Canvas mCachedBitmapCanvas = new Canvas();

    public ColorSeekBar(Context context) {
        super(context);
        applyStyle(context, null, 0, 0);
    }

    public ColorSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyStyle(context, attrs, 0, 0);
    }

    public ColorSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyStyle(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ColorSeekBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        applyStyle(context, attrs, defStyleAttr, defStyleRes);
    }


    private void applyStyle(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        mContext = context;
        //get attributes
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ColorSeekBar, defStyleAttr, defStyleRes);
        int colorsId = a.getResourceId(R.styleable.ColorSeekBar_colorSeekBarColorSeeds, 0);
        maxProgress = a.getInteger(R.styleable.ColorSeekBar_colorSeekBarMaxProgress, 100);
        progress = a.getInteger(R.styleable.ColorSeekBar_colorSeekBarProgress, 0);
        vertical = a.getBoolean(R.styleable.ColorSeekBar_colorSeekBarVertical, false);
        showThumb = a.getBoolean(R.styleable.ColorSeekBar_colorSeekBarShowThumb, true);
        barHeight = a.getDimensionPixelSize(R.styleable.ColorSeekBar_colorSeekBarBarHeight, dp2px(12));
        borderRadius = a.getDimensionPixelSize(R.styleable.ColorSeekBar_colorSeekBarRadius, barHeight / 2);
        borderColor = a.getColor(R.styleable.ColorSeekBar_colorSeekBarBorderColor, Color.BLACK);
        borderSize = a.getDimensionPixelSize(R.styleable.ColorSeekBar_colorSeekBarBorderSize, 1);

        a.recycle();

        barRectPaint.setAntiAlias(true);
        borderPaint.setAntiAlias(true);
        borderPaint.setStyle(Paint.Style.STROKE);
        if (colorsId != 0) {
            mColorSeeds = getColorsById(colorsId);
        }

        if (thumbDrawer == null) {
            setThumbDrawer(new DefaultThumbDrawer(dp2px(16), Color.WHITE, Color.BLACK));
        }

//        setBackgroundColor(backgroundColor);
    }

    /**
     * @param id color array resource
     * @return
     */
    private int[] getColorsById(@ArrayRes int id) {
        if (isInEditMode()) {
            String[] s = mContext.getResources().getStringArray(id);
            int[] colors = new int[s.length];
            for (int j = 0; j < s.length; j++) {
                colors[j] = Color.parseColor(s[j]);
            }
            return colors;
        } else {
            TypedArray typedArray = mContext.getResources().obtainTypedArray(id);
            int[] colors = new int[typedArray.length()];
            for (int j = 0; j < typedArray.length(); j++) {
                colors[j] = typedArray.getColor(j, Color.BLACK);
            }
            typedArray.recycle();
            return colors;
        }
    }

    protected void init() {
        super.init();
        if (isVertical()) {
            mCachedBitmapRect.set(thumbDragRect);
            mCachedBitmapRect.offsetTo(0, 0);

            //init paint
            LinearGradient mColorGradient = new LinearGradient(0, 0, 0, barRect.height(), mColorSeeds, null, Shader.TileMode.CLAMP);
            barRectPaint.setShader(mColorGradient);
            barRectPaint.setAntiAlias(true);

            LinearGradient mBitmapGradient = new LinearGradient(0, 0, 0, mCachedBitmapRect.height(), mColorSeeds, null, Shader.TileMode.CLAMP);
            mBitmapRectPaint.setShader(mBitmapGradient);
            mBitmapRectPaint.setAntiAlias(true);
        } else {
            mCachedBitmapRect.set(thumbDragRect);
            mCachedBitmapRect.offsetTo(0, 0);

            //init paint
            LinearGradient mColorGradient = new LinearGradient(0, 0, barRect.width(), 0, mColorSeeds, null, Shader.TileMode.CLAMP);
            barRectPaint.setShader(mColorGradient);
            barRectPaint.setAntiAlias(true);

            LinearGradient mBitmapGradient = new LinearGradient(0, 0, mCachedBitmapRect.width(), 0, mColorSeeds, null, Shader.TileMode.CLAMP);
            mBitmapRectPaint.setShader(mBitmapGradient);
            mBitmapRectPaint.setAntiAlias(true);
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Logger.i("onSizeChanged:w-" + w + " h-" + h);

        init();
        mCachedBitmap = Bitmap.createBitmap((int) mCachedBitmapRect.width(), (int) mCachedBitmapRect.height(), Bitmap.Config.ARGB_8888);
        mCachedBitmapCanvas = new Canvas(mCachedBitmap);
        if (mColorsToInvoke != -1) {
            setColor(mColorsToInvoke);
            mColorsToInvoke = -1;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Logger.i("onDraw");
        //clear
        canvas.drawColor(Color.TRANSPARENT);

        //draw color bar
        canvas.drawRoundRect(barRect, borderRadius, borderRadius, barRectPaint);
        //draw mCachedBitmapColor bitmap
        mCachedBitmapCanvas.drawRect(mCachedBitmapRect, mBitmapRectPaint);
        // drawBorder
        if (borderSize > 0) {
            canvas.drawRoundRect(barRect, borderRadius, borderRadius, borderPaint);
//            canvas.drawRect(thumbDragRect, borderPaint);
        }

        if (showThumb && thumbDrawer != null) {
            float offsetX;
            float offsetY;
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

//        canvas.drawCircle(getWidth() / 2f, getHeight() / 2f, 10, mBorderPaint);

        super.onDraw(canvas);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled()) {
            return true;
        }
        float x = vertical ? event.getY() : event.getX();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (barRect.contains(event.getX(), event.getY()) || thumbRect.contains(event.getX(), event.getY())) {
                    mMovingColorBar = true;
                    float value = calculateTouchPercent(x);
                    setProgress((int) value);
                    if (mOnColorChangeLister != null) {
                        mOnColorChangeLister.onColorChangeListener(progress, getColor());
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                getParent().requestDisallowInterceptTouchEvent(true);
                if (mMovingColorBar) {
                    float value = calculateTouchPercent(x);
                    setProgress((int) value);
                    if (mOnColorChangeLister != null) {
                        mOnColorChangeLister.onColorChangeListener(progress, getColor());
                    }
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                mMovingColorBar = false;
                break;
            default:
        }
        return true;
    }

    private float calculateTouchPercent(float x) {
        if (isVertical()) {
            return (x - thumbDragRect.top) / thumbDragRect.height() * maxProgress;
        } else {
            return (x - thumbDragRect.left) / thumbDragRect.width() * maxProgress;
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
    }


    /**
     * @param position
     * @return color
     */
    private int pickColor(int position) {
        return pickColor(position / (float) maxProgress);
    }

    /**
     * @param percent
     * @return color
     */
    private int pickColor(float percent) {
        Logger.i("pickColor:" + percent);
        if (percent <= 0.0) {
            return mColorSeeds[0];
        }


        if (percent >= 1) {
            return mColorSeeds[mColorSeeds.length - 1];
        }
        if (isVertical()) {
            return mCachedBitmap.getPixel(mCachedBitmap.getWidth() - 1, (int) (percent * mCachedBitmap.getHeight()));
        } else {
            return mCachedBitmap.getPixel((int) (percent * mCachedBitmap.getWidth()), mCachedBitmap.getHeight() - 1);
        }
    }

    /**
     * @return
     */
    public int getColor() {
        return pickColor(progress);
    }


    /**
     * @param onColorChangeListener
     */
    public void setOnColorChangeListener(OnColorChangeListener onColorChangeListener) {
        this.mOnColorChangeLister = onColorChangeListener;
    }


    public int dp2px(float dpValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * Set colors by resource id. The resource's type must be ArrayRes
     *
     * @param resId
     */
    public void setColorSeeds(@ArrayRes int resId) {
        setColorSeeds(getColorsById(resId));
    }

    public void setColorSeeds(int[] colors) {
        mColorSeeds = colors;
        init();
        invalidate();
        if (mOnColorChangeLister != null) {
            mOnColorChangeLister.onColorChangeListener(progress, getColor());
        }
    }

    /**
     * @return the color's position in the bar, if not in the bar ,return -1;
     * @deprecated use {@link #getColorPosition(int)} instead.
     */
    @Deprecated
    public int getColorIndexPosition(int color) {
        return getColorPosition(color);
    }

    /**
     * @return the color's position in the bar, if not in the bar ,return -1;
     */
    public int getColorPosition(int color) {
        for (int i = 0; i < maxProgress; i++) {
            int c = pickColor(i);
            if (color == c) return i;
        }
        return -1;
    }


    public List<Integer> getColors() {
        List<Integer> colors = new ArrayList<>();
        for (int i = 0; i < maxProgress; i++) {
            colors.add(pickColor(i));
        }
        return colors;
    }


//    public void setOnInitDoneListener(OnInitDoneListener listener) {
//        this.mOnInitDoneListener = listener;
//    }

    /**
     * Set color,the mCachedColors must contains the specified color, if not ,invoke setColorBarPosition(0);
     *
     * @param color
     */
    public void setColor(int color) {
        if (mCachedBitmap == null) {
            mColorsToInvoke = color;
            return;
        }
        int withoutAlphaColor = Color.rgb(Color.red(color), Color.green(color), Color.blue(color));
        List<Integer> colors = getColors();
        int position = colors.indexOf(withoutAlphaColor);
        setProgress(position);
    }


}
