package com.devpants.learnafrica;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;

public class MapActivity extends AppCompatActivity {
    ScrollView vScroll;
    HorizontalScrollView hScroll;
    private float mx, my;
    private float curX, curY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        vScroll = (ScrollView)findViewById(R.id.svVertical);
        hScroll = (HorizontalScrollView)findViewById(R.id.hsvHorizontal);
    }
    /*
        Used to enable diagonal scrolling. Velocity is not accounted for
        so scrolling does not retain momentum.
    */

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float curX, curY;

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                mx = event.getX();
                my = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                curX = event.getX();
                curY = event.getY();
                vScroll.scrollBy((int) (mx - curX), (int) (my - curY));
                hScroll.scrollBy((int) (mx - curX), (int) (my - curY));
                mx = curX;
                my = curY;
                break;
            case MotionEvent.ACTION_UP:
                curX = event.getX();
                curY = event.getY();
                vScroll.scrollBy((int) (mx - curX), (int) (my - curY));
                hScroll.scrollBy((int) (mx - curX), (int) (my - curY));
                break;
        }

        return true;
    }
    /*
        Easy fix to allow diagonal scrolling but does not allow independent scrolling
        between scroll views. I.e. scrolling the bottom bar also scrolls the map.

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        hScroll.dispatchTouchEvent(event);
        vScroll.onTouchEvent(event);
        return super.dispatchTouchEvent(event);
    }*/
}
