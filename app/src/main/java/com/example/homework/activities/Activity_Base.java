package com.example.homework.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.homework.R;
import com.example.homework.utils.Constants;
import com.example.homework.utils.MyScreenUtils;
import com.example.homework.utils.MySignal;

public class Activity_Base extends AppCompatActivity {
    protected boolean isDoubleBackPressToClose = false;
    private long mBackPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            MyScreenUtils.hideSystemUI2(this);
        }
    }

    @Override
    public void onBackPressed() {
        if (isDoubleBackPressToClose) {
            if (mBackPressed + Constants.TIME_INTERVAL > System.currentTimeMillis()) {
                super.onBackPressed();
                return;
            }
            else {
                MySignal.getInstance().toast("Tap back button again to exit");
            }

            mBackPressed = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }
    }

    protected void closeActivity() {
        finish();
    }
}