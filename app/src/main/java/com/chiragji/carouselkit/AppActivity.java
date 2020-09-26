package com.chiragji.carouselkit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.chiragji.carouselkit.adapters.ViewAdapter;

/**
 * @author Chirag [apps.chiragji@outlook.com]
 * @version 1
 * @since 1.0.0
 */
public class AppActivity extends AppCompatActivity {
    private static final String TAG = "AppActivity";

    private static AppActivity appActivity;

    private ViewPager2 mainPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

        mainPager = findViewById(R.id.mainPager);

        mainPager.setAdapter(new ViewAdapter(this));
        mainPager.setUserInputEnabled(false);
    }

    public static void toggleGallery(boolean open) {
        appActivity.mainPager.setCurrentItem(open ? 1 : 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        appActivity = this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        appActivity = null;
    }
}