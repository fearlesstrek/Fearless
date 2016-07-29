
package com.example.fearless.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.example.fearless.fearless.R;


/**
 * ImageView基类，可改变ImageView点击时的背景,如果有点击效果的图片要使用
 * 
 * @author Administrator
 */
public class BaseImageView extends ImageView {
    /**
     * The highlight drawable. This generally a
     * {@link android.graphics.drawable.StateListDrawable} that's transparent in
     * the default state, and contains a semi-transparent overlay for the
     * focused and pressed states.
     */
    private Drawable mForegroundDrawable;

    private boolean canBgChange = true;
    
    private boolean isEnableGray=false; //是否支持默认加灰
    /**
     * The cached bounds of the view.
     */
    private Rect mCachedBounds = new Rect();
     

    public BaseImageView(Context context) {
        this(context,null,-1);
//        init();
    }

    public BaseImageView(Context context, AttributeSet attrs) {
        this(context, attrs,-1);
//        init();
    }

    public BaseImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.BaseImageView);

        isEnableGray = a.getBoolean(R.styleable.BaseImageView_enable_gray, false);
        a.recycle();
        init();
    }

    /**
     * General view initialization used common to all constructors of the view.
     */
    private void init() {
        // Reset default ImageButton background and padding.
        setBackgroundColor(0);
        setPadding(0, 0, 0, 0);

        
        
        // Retrieve the drawable resource assigned to the
        // android.R.attr.selectableItemBackground
        // theme attribute from the current theme.
        // TypedArray a = getContext()
        // .obtainStyledAttributes(new
        // int[]{android.R.attr.selectableItemBackground});
        
      
        
        mForegroundDrawable = getResources().getDrawable(R.drawable.image_view_selector);
        
        mForegroundDrawable.setCallback(this);
        // a.recycle();
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();

        
        if (canBgChange && !isEnableGray) {
            // Update the state of the highlight drawable to match
            // the state of the button.
            if (mForegroundDrawable.isStateful()) {
                mForegroundDrawable.setState(getDrawableState());
            }

            // Trigger a redraw.
//            invalidate();
        }else{
//        	if(SettingsPerf.getMobileNight(getContext())){
//        		mForegroundDrawable=getResources().getDrawable(R.drawable.half_transparent);
//        		mForegroundDrawable.setState(getDrawableState());
//        	}else{
        		 if (mForegroundDrawable.isStateful()) {
                     mForegroundDrawable.setState(getDrawableState());
                 }
//        	}
        }
        invalidate();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        // First draw the image.
        super.onDraw(canvas);

        // Then draw the highlight on top of it. If the button is neither
        // focused
        // nor pressed, the drawable will be transparent, so just the image
        // will be drawn.
        mForegroundDrawable.setBounds(mCachedBounds);
        mForegroundDrawable.draw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // Cache the view bounds.
        mCachedBounds.set(0, 0, w, h);
    }

    /*
     * @set canBgChange
     */
    public void setBgCanChange(boolean change) {
        canBgChange = change;
    }
    
    
}
