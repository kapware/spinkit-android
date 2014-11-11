package com.kapware.spinkit;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

/**
 * An indicator of progress, based on SpinKit animations by Tobias Ahlin:
 * http://tobiasahlin.com/spinkit/
 * @author Pawel Kapala
 *         <p/>
 *         Licensed under the Creative Commons Attribution 3.0 license see:
 *         http://creativecommons.org/licenses/by/3.0/
 */

 public class SpinKitView extends View
//        implements IProgressBar
{

    private static final PointF TRANSLATION_BUFFER = new PointF();

    //Padding (with defaults)
    private int paddingTop = 5;
    private int paddingBottom = 5;
    private int paddingLeft = 5;
    private int paddingRight = 5;

    //Colors (with defaults)
    private int circleColor = 0x00000000;

    //Paints
    private Paint circlePaint = new Paint();

    //Animation
    //The amount of pixels to move the bar by on each draw
    private int spinSpeed = 2;
    //The number of milliseconds to wait inbetween each draw
    private int delayMillis = 0;

    private SpinType mSpinType;

    private float progressEased;
    private float secondProgressEased;

    private static float circular(float progress) {
        return progress - Math.round(progress + 0.5) + 1;
    }

    private static float ease(KeySpline keySpline, float progress) {
        float progressEased = progress;
        if (progress < 0.25f) {
            progressEased = keySpline.get(progress) * 2;
            // TODO: Investigate why:
            if (progressEased > 0.25f) {
                progressEased = 0.25f;
            }
        } else if (progress < 0.5f) {
            progressEased = 0.25f + keySpline.get(progress - 0.25f) * 2;
            // TODO: Investigate why:
            if (progressEased > 0.5f) {
                progressEased = 0.5f;
            }

        } else if (progress < 0.75f) {
            progressEased = 0.5f + keySpline.get(progress - 0.5f) * 2;
            // TODO: Investigate why:
            if (progressEased > 0.75f) {
                progressEased = 0.75f;
            }

        } else if (progress < 1f) {
            progressEased = 0.75f + keySpline.get(progress - 0.75f) * 2;
            // TODO: Investigate why:
            if (progressEased > 1f) {
                progressEased = 1f;
            }
        }
        return progressEased;
    }

    private Handler spinHandler = new Handler() {
        /**
         * This is the code that will increment the progress variable
         * and so spin the wheel
         */
        @Override
        public void handleMessage(Message msg) {
            invalidate();
            if (isSpinning) {
                progress = circular(progress + (float) spinSpeed / 1000);
                updateState();
                spinHandler.sendEmptyMessageDelayed(0, delayMillis);
            }
        }
    };
    float progress = 0;
    boolean isSpinning = false;

    private void updateState() {
        KeySpline keySpline = new KeySpline(0.42f, 0, 0.58f, 1f);
        progressEased = ease(keySpline, progress);
        secondProgressEased = ease(keySpline, circular(progress + 0.5f));
    }

//    /**
//     * Create a new progress bar with range 0...100 and initial progress of 0.
//     * @param context the application environment
//     */
//    public SpinKitView(Context context) {
//        this(context, null);
//    }
//
//    public SpinKitView(Context context, AttributeSet attrs) {
//        this(context, attrs, com.android.internal.R.attr.progressBarStyle);
//    }
//
//    public SpinKitView(Context context, AttributeSet attrs, int defStyle) {
//        this(context, attrs, defStyle, 0);
//    }

    /**
     * @hide
     */
    public SpinKitView(Context context, AttributeSet attrs) { //, int defStyle, int styleRes) {
//        super(context, attrs, defStyle);
        super(context, attrs);
        parseAttributes(context.obtainStyledAttributes(attrs, R.styleable.SpinKitView));
    }


    //----------------------------------
    //Setting up stuff
    //----------------------------------

    /*
     * When this is called, make the view square.
     * From: http://www.jayway.com/2012/12/12/creating-custom-android-views-part-4-measuring-and-how-to-force-a-view-to-be-square/
     *
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // The first thing that happen is that we call the superclass
        // implementation of onMeasure. The reason for that is that measuring
        // can be quite a complex process and calling the super method is a
        // convenient way to get most of this complexity handled.
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // We can’t use getWidth() or getHight() here. During the measuring
        // pass the view has not gotten its final size yet (this happens first
        // at the start of the layout pass) so we have to use getMeasuredWidth()
        // and getMeasuredHeight().
        final int size;
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int widthWithoutPadding = width - getPaddingLeft() - getPaddingRight();
        int heigthWithoutPadding = height - getPaddingTop() - getPaddingBottom();

        // Finally we have some simple logic that calculates the size of the view
        // and calls setMeasuredDimension() to set that size.
        // Before we compare the width and height of the view, we remove the padding,
        // and when we set the dimension we add it back again. Now the actual content
        // of the view will be square, but, depending on the padding, the total dimensions
        // of the view might not be.
        if (widthWithoutPadding > heigthWithoutPadding) {
            size = heigthWithoutPadding;
        } else {
            size = widthWithoutPadding;
        }

        // If you override onMeasure() you have to call setMeasuredDimension().
        // This is how you report back the measured size.  If you don’t call
        // setMeasuredDimension() the parent will throw an exception and your
        // application will crash.
        // We are calling the onMeasure() method of the superclass so we don’t
        // actually need to call setMeasuredDimension() since that takes care
        // of that. However, the purpose with overriding onMeasure() was to
        // change the default behaviour and to do that we need to call
        // setMeasuredDimension() with our own values.
        setMeasuredDimension(size + getPaddingLeft() + getPaddingRight(), size + getPaddingTop() + getPaddingBottom());
    }

    /**
     * Use onSizeChanged instead of onAttachedToWindow to get the dimensions of the view,
     * because this method is called after measuring the dimensions of MATCH_PARENT & WRAP_CONTENT.
     * Use this dimensions to setup the bounds and paints.
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        setupPaints();
        invalidate();
    }

    /**
     * Set the properties of the paints we're using to
     * draw the progress wheel
     */
    private void setupPaints() {
        circlePaint.setColor(circleColor);
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Paint.Style.FILL);
    }

    /**
     * Parse the attributes passed to the view from the XML
     *
     * @param a the attributes to parse
     */
    private void parseAttributes(TypedArray a) {
        spinSpeed = (int) a.getDimension(R.styleable.SpinKitView_spinSpeed,
                spinSpeed);

        delayMillis = a.getInteger(R.styleable.SpinKitView_delayMillis,
                delayMillis);
        if (delayMillis < 0) {
            delayMillis = 0;
        }

        circleColor = a.getColor(R.styleable.SpinKitView_circleColor, circleColor);

        mSpinType = SpinType.findById(a.getInt(R.styleable.SpinKitView_spinType, 0));

        // Recycle
        a.recycle();
    }

    //----------------------------------
    //Animation stuff
    //----------------------------------

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (mSpinType) {
            case WANDERING_CIRCLES:
                drawWanderingCircles(canvas);
                break;

            default:
            case WANDERING_CUBES:
                drawWanderingCubes(canvas);
        }
    }


    private void drawWanderingCubes(Canvas canvas) {
        final int viewWidth = getWidth();
        final int viewHeight = getHeight();

        drawWanderingCube(canvas, viewWidth, viewHeight, progressEased, circlePaint);
        drawWanderingCube(canvas, viewWidth, viewHeight, secondProgressEased, circlePaint);
    }

    private void drawWanderingCircles(Canvas canvas) {
        final int viewWidth = getWidth();
        final int viewHeight = getHeight();

        drawWanderingCircle(canvas, viewWidth, viewHeight, progressEased, circlePaint);
        drawWanderingCircle(canvas, viewWidth, viewHeight, secondProgressEased, circlePaint);
    }

    private static void drawWanderingCube(Canvas canvas, int viewWidth, int viewHeight, float progress,
                                          Paint paint) {
        final int boxSize = 10 * Math.min(viewWidth, viewHeight) / 32;

        final float scale = Math.abs(Math.abs(2 * progress - 1) - 0.5f) + 0.5f;
        calculateTranslation(TRANSLATION_BUFFER, progress, viewWidth, viewHeight, 0);

        canvas.save();
        canvas.translate(TRANSLATION_BUFFER.x, TRANSLATION_BUFFER.y);
        canvas.scale(scale, scale);
        canvas.rotate(progress * 360);
        canvas.drawRect(0, 0, boxSize, boxSize, paint);
        canvas.restore();
    }

    private static void drawWanderingCircle(Canvas canvas, int viewWidth, int viewHeight, float progress,
                                          Paint paint) {
        final int boxSize = 10 * Math.min(viewWidth, viewHeight) / 32;

        final float scale = Math.abs(Math.abs(2 * progress - 1) - 0.5f) + 0.5f;
        calculateTranslation(TRANSLATION_BUFFER, progress, viewWidth - boxSize,
                viewHeight - boxSize, boxSize);

        canvas.save();
        canvas.translate(TRANSLATION_BUFFER.x, TRANSLATION_BUFFER.y);
        canvas.scale(scale, scale);
        canvas.drawCircle(0, 0, boxSize / 2, paint);
        canvas.restore();
    }

    private static void calculateTranslation(PointF translation, float progress,
                                             int pathWidth, int pathHeight, float boxSize) {
        // TODO: optimize
        float x0 = boxSize / 2;
        float y0 = boxSize / 2;
        final int quarter = (int) (progress * 4);
        switch (quarter) {
            case 0:
                translation.set(x0 + 4 * progress * pathWidth, y0);
                return;
            case 1:
                translation.set(x0 + pathWidth, y0 + 4 * (progress - 0.25f) * pathHeight);
                return;
            case 2:
                translation.set(x0 + pathWidth - 4 * (progress - 0.5f) * pathWidth, y0 + pathHeight);
                return;
            default:
            case 3:
                translation.set(x0, y0 + pathHeight - 4 * (progress - 0.75f) * pathHeight);
                return;
        }
    }

    /**
     *   Check if the wheel is currently spinning
     */
    public boolean isSpinning() {
        if (isSpinning){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Reset the count (in increment mode)
     */
    public void resetCount() {
        progress = 0;
        invalidate();
    }

    /**
     * Turn off spin mode
     */
    public void stopSpinning() {
        isSpinning = false;
        progress = 0;
        spinHandler.removeMessages(0);
    }


    /**
     * Puts the view on spin mode
     */
    public void spin() {
        isSpinning = true;
        spinHandler.sendEmptyMessage(0);
    }

    /**
     * Set the progress to a specific value
     */
    public void setProgress(float p) {
        isSpinning = false;
        progress = p;
        updateState();
        spinHandler.sendEmptyMessage(0);
    }

    //----------------------------------
    //Getters + setters
    //----------------------------------
    public int getPaddingTop() {
        return paddingTop;
    }

    public void setPaddingTop(int paddingTop) {
        this.paddingTop = paddingTop;
    }

    public int getPaddingBottom() {
        return paddingBottom;
    }

    public void setPaddingBottom(int paddingBottom) {
        this.paddingBottom = paddingBottom;
    }

    public int getPaddingLeft() {
        return paddingLeft;
    }

    public void setPaddingLeft(int paddingLeft) {
        this.paddingLeft = paddingLeft;
    }

    public int getPaddingRight() {
        return paddingRight;
    }

    public void setPaddingRight(int paddingRight) {
        this.paddingRight = paddingRight;
    }


    public int getCircleColor() {
        return circleColor;
    }

    public void setCircleColor(int circleColor) {
        this.circleColor = circleColor;
    }

    public int getSpinSpeed() {
        return spinSpeed;
    }

    public void setSpinSpeed(int spinSpeed) {
        this.spinSpeed = spinSpeed;
    }

    public int getDelayMillis() {
        return delayMillis;
    }

    public void setDelayMillis(int delayMillis) {
        this.delayMillis = delayMillis;
    }

    public SpinType getSpinType() {
        return mSpinType;
    }

    public void setSpinType(SpinType spinType) {
        mSpinType = spinType;
    }

    /**
     * Ported from js:
     * http://greweb.me/2012/02/bezier-curve-based-easing-functions-from-concept-to-implementation/
     */
    private static class KeySpline {
        private float x1;
        private float y1;
        private float x2;
        private float y2;

        public KeySpline(float x1, float y1, float x2, float y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }

        public float get(float aX) {
            if (this.x1 == this.y1 && this.x2 == this.y2) {
                return aX; // linear
            }
            return calcBezier(getTForX(aX), this.y1, this.y2);
        }

        private float A(float aA1, float aA2) {
            return 1.0f - 3.0f * aA2 + 3.0f * aA1;
        }
        private float B(float aA1, float aA2) {
            return 3.0f * aA2 - 6.0f * aA1;
        }
        private float C(float aA1) {
            return 3.0f * aA1;
        }

        // Returns x(t) given t, x1, and x2, or y(t) given t, y1, and y2.
        private float calcBezier(float aT, float aA1, float aA2) {
            return ((A(aA1, aA2)*aT + B(aA1, aA2))*aT + C(aA1))*aT;
        }

        // Returns dx/dt given t, x1, and x2, or dy/dt given t, y1, and y2.
        private float getSlope(float aT, float aA1, float aA2) {
            return 3.0f * A(aA1, aA2)*aT*aT + 2.0f * B(aA1, aA2) * aT + C(aA1);
        }

        private float getTForX(float aX) {
            // Newton Raphson iteration
            float aGuessT = aX;
            for (int i = 0; i < 4; i++) {
                float currentSlope = getSlope(aGuessT, this.x1, this.x2);
                if (currentSlope == 0.0) {
                    return aGuessT;
                }
                float currentX = calcBezier(aGuessT, this.x1, this.x2) - aX;
                aGuessT -= currentX / currentSlope;
            }
            return aGuessT;
        }
    }

}

