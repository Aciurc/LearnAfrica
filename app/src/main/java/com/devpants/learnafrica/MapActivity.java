package com.devpants.learnafrica;

import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import java.util.Iterator;
import java.util.LinkedHashMap;

public class MapActivity extends AppCompatActivity {
    private float mx, my;
    private boolean moved = false;
    private ScrollView vScroll;
    private HorizontalScrollView hScroll;
    private String[] rawCountries;
    public LinkedHashMap<String, Pair<Float, Float>> countries = new LinkedHashMap<>();
    private LinearLayout topList;
    private LinearLayout botList;
    private RelativeLayout imageOverlay;
    private Button currentButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        vScroll = (ScrollView)findViewById(R.id.svVertical);
        hScroll = (HorizontalScrollView)findViewById(R.id.hsvHorizontal);
        rawCountries = getResources().getStringArray(R.array.countries);
        topList = (LinearLayout) findViewById(R.id.llTopCountries);
        botList = (LinearLayout) findViewById(R.id.llBottomCountries);
        imageOverlay = (RelativeLayout) findViewById(R.id.rlImageOverlay);

        initializeCountries();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float curX, curY;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                moved = false;
                mx = event.getX()*2;
                my = event.getY()*2;
                break;
            case MotionEvent.ACTION_MOVE:
                curX = event.getX()*2;
                curY = event.getY()*2;
                vScroll.scrollBy((int) (mx - curX), (int) (my - curY));
                hScroll.scrollBy((int) (mx - curX), (int) (my - curY));
                mx = curX;
                my = curY;
                moved = true;
                break;
            case MotionEvent.ACTION_UP:
                curX = event.getX()*2;
                curY = event.getY()*2;
                vScroll.scrollBy((int) (mx - curX), (int) (my - curY));
                hScroll.scrollBy((int) (mx - curX), (int) (my - curY));

                if (!moved) {
                    float screenX = event.getX();
                    float screenY = event.getY();
                    float scrollX = hScroll.getScrollX();
                    float scrollY = vScroll.getScrollY();
                    float canvasX = screenX + scrollX;
                    float canvasY = screenY + scrollY;

                    DisplayMetrics metrics = new DisplayMetrics();
                    getWindowManager().getDefaultDisplay().getMetrics(metrics);
                    float logicalDensity = metrics.density;
                    int dpX = (int) Math.ceil(canvasX / logicalDensity);
                    int dpY = (int) Math.ceil(canvasY / logicalDensity);
                    Log.d("SCREEN TOUCH COORDS", "("+screenX+","+screenY+")");
                    Log.d("SCROLL COORDS", "("+scrollX+","+scrollY+")");
                    Log.d("DP COORDS", "("+dpX+","+dpY+")");
                    Log.d("CANVAS COORDS", "(" + canvasX + "," + canvasY + ")");
                    final RadioButton btnLocation = new RadioButton(this);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(Math.round(canvasX), Math.round(canvasY), 0, 0);
                    btnLocation.setLayoutParams(params);
                    imageOverlay.addView(btnLocation);
                }
                break;
        }

        return true;
    }
    private void initializeCountries() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float logicalDensity = metrics.density;

        // Parse country data from raw country strings
        for (String rawCountry : rawCountries) {
            String[] countryData = rawCountry.split(",");
            countries.put(countryData[0], new Pair<>(Float.parseFloat(countryData[1]), Float.parseFloat(countryData[2])));
        }

        Iterator it = countries.entrySet().iterator();
        int i = 0;
        while (it.hasNext()) {
            LinkedHashMap.Entry pair = (LinkedHashMap.Entry)it.next();
            String countryName = (String)pair.getKey();
            Pair<Float, Float> countryCoords = (Pair<Float, Float>)pair.getValue();

            final Button btnCountry = new Button(this);
            btnCountry.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            btnCountry.setText(countryName);
            btnCountry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    currentButton = btnCountry;
                    //countries.remove("Algeria");
                    //btnCountry.setVisibility(View.GONE);
                }
            });
            if (i % 2 == 0) {
                topList.addView(btnCountry);
            } else {
                botList.addView(btnCountry);
            }
            i++;


            int dpX = (int) Math.ceil(countryCoords.first / logicalDensity);
            int dpY = (int) Math.ceil(countryCoords.second / logicalDensity);

            final RadioButton btnLocation = new RadioButton(this);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(Math.round(countryCoords.first-35), Math.round(countryCoords.second-90), 0, 0);
            btnLocation.setLayoutParams(params);
            btnLocation.setText(countryName);
            imageOverlay.addView(btnLocation);

            it.remove(); // avoids a ConcurrentModificationException
        }
    }
}
