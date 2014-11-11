package com.kapware.spinkit;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

/**
 * An indicator of progress, based on SpinKit animations by Tobias Ahlin:
 * http://tobiasahlin.com/spinkit/
 */
public class SpinKitView extends View {
    private int mSpinColor = 0x00000000;
    private Paint mItemPaint = new Paint();
    private int mSpinSpeed = 2;
    private int mDrawDelay = 0;

    private SpinType mSpinType;

    private float mProgressEased;
    private float mSecondProgressEased;

    private Handler spinHandler = new Handler() {
        /**
         * This is the code that will increment the progress variable
         * and so spin the wheel
         */
        @Override
        public void handleMessage(Message msg) {
            invalidate();
            if (mSpinning) {
                progress = SpinKitUtils.circular(progress + (float) mSpinSpeed / 1000);
                updateState();
                spinHandler.sendEmptyMessageDelayed(0, mDrawDelay);
            }
        }
    };
    float progress = 0;
    boolean mSpinning = false;

    private void updateState() {
        KeySpline keySpline = new KeySpline(0.42f, 0, 0.58f, 1f);
        mProgressEased = SpinKitUtils.ease(keySpline, progress);
        mSecondProgressEased = SpinKitUtils.ease(keySpline, SpinKitUtils.circular(progress + 0.5f));
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

    public SpinKitView(Context context, AttributeSet attrs) { //, int defStyle, int styleRes) {
        super(context, attrs);
        parseAttributes(context.obtainStyledAttributes(attrs, R.styleable.SpinKitView));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        final int size;
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int widthWithoutPadding = width - getPaddingLeft() - getPaddingRight();
        int heigthWithoutPadding = height - getPaddingTop() - getPaddingBottom();

        if (widthWithoutPadding > heigthWithoutPadding) {
            size = heigthWithoutPadding;
        } else {
            size = widthWithoutPadding;
        }

        setMeasuredDimension(size + getPaddingLeft() + getPaddingRight(), size + getPaddingTop() + getPaddingBottom());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        setupPaints();
        invalidate();
    }

    private void setupPaints() {
        mItemPaint.setColor(mSpinColor);
        mItemPaint.setAntiAlias(true);
        mItemPaint.setStyle(Paint.Style.FILL);
    }

    private void parseAttributes(TypedArray a) {
        mSpinSpeed = (int) a.getDimension(R.styleable.SpinKitView_spinSpeed,
                mSpinSpeed);

        mDrawDelay = a.getInteger(R.styleable.SpinKitView_drawDelay,
                mDrawDelay);
        if (mDrawDelay < 0) {
            mDrawDelay = 0;
        }

        mSpinColor = a.getColor(R.styleable.SpinKitView_spinColor, mSpinColor);
        mSpinType = SpinType.findById(a.getInt(R.styleable.SpinKitView_spinType, 0));

        a.recycle();
    }

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

        SpinAlgorithms.drawWanderingCube(canvas, viewWidth, viewHeight, mProgressEased, mItemPaint);
        SpinAlgorithms.drawWanderingCube(canvas, viewWidth, viewHeight, mSecondProgressEased, mItemPaint);
    }

    private void drawWanderingCircles(Canvas canvas) {
        final int viewWidth = getWidth();
        final int viewHeight = getHeight();

        SpinAlgorithms.drawWanderingCircle(canvas, viewWidth, viewHeight, mProgressEased, mItemPaint);
        SpinAlgorithms.drawWanderingCircle(canvas, viewWidth, viewHeight, mSecondProgressEased, mItemPaint);
    }

    public boolean isSpinning() {
        return mSpinning;
    }

    public void stopSpinning() {
        mSpinning = false;
        progress = 0;
        spinHandler.removeMessages(0);
    }

    public void spin() {
        mSpinning = true;
        spinHandler.sendEmptyMessage(0);
    }

    public void setProgress(float p) {
        mSpinning = false;
        progress = p;
        updateState();
        spinHandler.sendEmptyMessage(0);
    }

    public int getSpinColor() {
        return mSpinColor;
    }

    public void setSpinColor(int spinColor) {
        this.mSpinColor = spinColor;
    }

    public int getSpinSpeed() {
        return mSpinSpeed;
    }

    public void setSpinSpeed(int spinSpeed) {
        this.mSpinSpeed = spinSpeed;
    }

    public int getDrawDelay() {
        return mDrawDelay;
    }

    public void setDrawDelay(int drawDelay) {
        this.mDrawDelay = drawDelay;
    }

    public SpinType getSpinType() {
        return mSpinType;
    }

    public void setSpinType(SpinType spinType) {
        mSpinType = spinType;
    }

}

