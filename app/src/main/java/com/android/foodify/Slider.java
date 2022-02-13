package com.android.foodify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.foodify.Adapters.ImageAdapter;
import com.android.foodify.SharedPreferences.SharedPreferences;

public class Slider extends AppCompatActivity {
    ViewPager viewPager;
    int[] layouts;
    ImageAdapter adapter;
    ImageView prev, next, topPrev;
    Button getStarted;
    ImageView imgStart;
    TextView startText;
    Button nextPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_slider);
        viewPager = findViewById(R.id.viewPager);
        prev = findViewById(R.id.prev);
        prev.setVisibility(View.GONE);
        next = findViewById(R.id.next);
        next.setVisibility(View.GONE);
        getStarted = findViewById(R.id.button);
        getStarted.setVisibility(View.GONE);
        topPrev = findViewById(R.id.topPrev);
        topPrev.setVisibility(View.GONE);
        imgStart = findViewById(R.id.imgNext);
        startText = findViewById(R.id.startText);
        nextPage = findViewById(R.id.button);
        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SharedPreferences.getPREF_userId(getApplicationContext()).length() != 0) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                } else {
                    startActivity(new Intent(getApplicationContext(), Login.class));
                }
            }
        });

        layouts = new int[]{
                R.layout.slider1,
                R.layout.slider2,
                R.layout.slider3
        };

        adapter = new ImageAdapter(this, layouts);
        viewPager.setAdapter(adapter);

        imgStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPager.getCurrentItem() + 1 < layouts.length) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                } else {

                }
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            }
        });


        topPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            }
        });

        viewPager.addOnPageChangeListener(viewPagerChangeListener);

    }

    ViewPager.OnPageChangeListener viewPagerChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            if (i == layouts.length - 1) {
                getStarted.setVisibility(View.VISIBLE);
                topPrev.setVisibility(View.VISIBLE);
                prev.setVisibility(View.GONE);
                next.setVisibility(View.GONE);
                imgStart.setVisibility(View.GONE);
                startText.setVisibility(View.GONE);
            } else if (i == layouts.length - 2) {
                getStarted.setVisibility(View.GONE);
                prev.setVisibility(View.VISIBLE);
                next.setVisibility(View.VISIBLE);
                topPrev.setVisibility(View.GONE);
                imgStart.setVisibility(View.GONE);
                startText.setVisibility(View.GONE);
            } else {
                getStarted.setVisibility(View.GONE);
                prev.setVisibility(View.GONE);
                next.setVisibility(View.GONE);
                topPrev.setVisibility(View.GONE);
                imgStart.setVisibility(View.VISIBLE);
                startText.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };
}