package com.devpants.learnafrica;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class MapActivity extends AppCompatActivity {
    private float mx, my;
    private boolean moved = false;
    private ScrollView vScroll;
    private HorizontalScrollView hScroll;
    private String[] countries;
    private LinearLayout topList;
    private LinearLayout botList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        vScroll = (ScrollView)findViewById(R.id.svVertical);
        hScroll = (HorizontalScrollView)findViewById(R.id.hsvHorizontal);
        countries = getResources().getStringArray(R.array.countries);
        topList = (LinearLayout) findViewById(R.id.llTopCountries);
        botList = (LinearLayout) findViewById(R.id.llBottomCountries);

        updateCountryList();
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
                moved = true;
                curX = event.getX()*2;
                curY = event.getY()*2;
                vScroll.scrollBy((int) (mx - curX), (int) (my - curY));
                hScroll.scrollBy((int) (mx - curX), (int) (my - curY));
                mx = curX;
                my = curY;
                break;
            case MotionEvent.ACTION_UP:
                curX = event.getX()*2;
                curY = event.getY()*2;
                vScroll.scrollBy((int) (mx - curX), (int) (my - curY));
                hScroll.scrollBy((int) (mx - curX), (int) (my - curY));

                if (!moved) {
                    float screenX = curX / 2;
                    float screenY = curY / 2;
                    float scrollX = hScroll.getScrollX();
                    float scrollY = vScroll.getScrollY();
                    float canvasX = screenX + scrollX;
                    float canvasY = screenY + scrollY;

                    //Log.d("SCREEN TOUCH COORDS", "("+screenX+","+screenY+")");
                    //Log.d("SCROLL COORDS", "("+scrollX+","+scrollY+")");
                    Log.d("CANVAS COORDS", "(" + canvasX + "," + canvasY + ")");
                }
                break;
        }

        return true;
    }

    private void updateCountryList() {
        for (int i = 0; i < countries.length; i++) {
            Button btnCountry = new Button(this);
            btnCountry.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            btnCountry.setText(countries[i]);
            btnCountry.setTag(countries[i].toLowerCase());
            if (i % 2 == 1) {
                topList.addView(btnCountry);
            } else {
                botList.addView(btnCountry);
            }
        }
    }
}
