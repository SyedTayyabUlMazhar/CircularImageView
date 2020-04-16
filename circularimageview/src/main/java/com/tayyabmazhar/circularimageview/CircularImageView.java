package com.tayyabmazhar.circularimageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.ColorInt;
import androidx.cardview.widget.CardView;

public class CircularImageView extends CardView
{

    private String TAG = "CircularImageView";

    private Context mContext;

    private CardView innerCardView;

    private ImageView imageView;
    private Drawable drawable;

    private int borderWidth;
    private int borderColor;


    public CircularImageView(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        Log.i(TAG, "**************  C I R C U L A R   I M A G E   V I E W  ********************");

        mContext = context;
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CircularImageView, 0, 0);

        try
        {
            drawable = a.getDrawable(R.styleable.CircularImageView_drawableImage);
            borderWidth = (int) a.getDimension(R.styleable.CircularImageView_borderWidth, 4);
            borderColor = a.getColor(R.styleable.CircularImageView_borderColor, 0xFFFFFFFF);

            init();
        } finally
        {
            a.recycle();
        }

        Log.i(TAG, "**************************************************");

    }

    private void init()
    {

        //outer card view
        setCardBackgroundColor(borderColor);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            setElevation(0);
        }
        setContentPadding(borderWidth, borderWidth, borderWidth, borderWidth);


        //inner card view
        innerCardView = new CardView(mContext);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            innerCardView.setElevation(0);
        }

        //image view
        imageView = new ImageView(mContext);
        imageView.setImageDrawable(drawable);
        // this is needed, because if an image or one of it's dimension is smaller than inner cardview,
        // then image wont cover the whole inner cardview, rather it might show a blank space
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);


        //add imageView to innerCardView, and then add innerCardView to OuterCardView.(out card view is 'this')
        innerCardView.addView(imageView);
        addView(innerCardView);

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        Log.i(TAG, "**************  O N   D R A W  ********************");

        super.onDraw(canvas);

        setRadius((float) (getWidth() / 2.0));
        innerCardView.setRadius((float) (innerCardView.getWidth() / 2.0));

        displayLogInfo();

        Log.i(TAG, "**************************************************");

    }

    private void displayLogInfo()
    {
        Log.i(TAG, "--------------outer card view---------------");
        Log.i(TAG, "width: " + getWidth());
        Log.i(TAG, "height: " + getHeight());
        Log.i(TAG, "radius: " + getRadius());

        Log.i(TAG, "--------------inner card view---------------");
        Log.i(TAG, "width: " + innerCardView.getWidth());
        Log.i(TAG, "height: " + innerCardView.getHeight());
        Log.i(TAG, "radius: " + innerCardView.getRadius());

        Log.i(TAG, "--------------imageView---------------");
        Log.i(TAG, "width: " + imageView.getWidth());
        Log.i(TAG, "height: " + imageView.getHeight());
    }


    public ImageView getImageView()
    {
        return imageView;
    }

    public void setBorderColor(@ColorInt int borderColor)
    {
        this.borderColor = borderColor;
        setCardBackgroundColor(borderColor);
    }

    public void setBorderWidth(int pixels)
    {
        borderWidth = pixels;
        setContentPadding(borderWidth, borderWidth, borderWidth, borderWidth);
    }

}
