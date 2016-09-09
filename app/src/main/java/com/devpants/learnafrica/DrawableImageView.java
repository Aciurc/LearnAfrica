package com.devpants.learnafrica;

/**
 * Created by Pantss on 23/08/2016.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.util.AttributeSet;
import android.widget.ImageView;

import java.util.HashMap;

public class DrawableImageView extends ImageView {
    private Paint paint = new Paint();
    public HashMap<String, Pair<Integer, Integer>> points;

    public DrawableImageView(Context context) {
        super(context);
    }

    public DrawableImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawableImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int color = ContextCompat.getColor(getContext(), R.color.colorPrimaryDark);
        paint.setColor(color);
        canvas.drawCircle(526, 1336, 10, paint);
        canvas.drawCircle(1280, 1768, 10, paint);
        //canvas.drawCircle(908, 502, 10, paint);
        //canvas.drawCircle(1018, 1396, 10, paint);
    }

}