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
import android.util.Log;
import android.widget.ImageView;

import java.util.Iterator;
import java.util.LinkedHashMap;

public class DrawableImageView extends ImageView {
    private Paint paint = new Paint();
    public LinkedHashMap<String, Pair<Float, Float>> countries = new LinkedHashMap<>();

    public DrawableImageView(Context context) {
        super(context);
    }

    public DrawableImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        String[] rawCountries = getResources().getStringArray(R.array.countries);
        for (String rawCountry : rawCountries) {
            String[] countryData = rawCountry.split(",");
            countries.put(countryData[0], new Pair<>(Float.parseFloat(countryData[1]), Float.parseFloat(countryData[2])));
        }
    }

    public DrawableImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int color = ContextCompat.getColor(getContext(), R.color.colorPrimaryDark);
        paint.setColor(color);

        Iterator it = countries.entrySet().iterator();

        while(it.hasNext()) {
            LinkedHashMap.Entry pair = (LinkedHashMap.Entry)it.next();
            Pair<Float, Float> countryCoords = (Pair<Float, Float>)pair.getValue();
            canvas.drawCircle(countryCoords.first-4, countryCoords.second-60, 20, paint);
            Log.d("Paint coords", countryCoords.first + "|" + countryCoords.second);
        }
        canvas.drawCircle(0, 0, 50, paint);
    }

}