package com.cavalerie.vlc123.gogogo.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.cavalerie.vlc123.gogogo.Data.UserProfileManager;
import com.cavalerie.vlc123.gogogo.R;
import com.wang.avi.AVLoadingIndicatorView;

public class SplashActivity extends AppCompatActivity {

    private ImageView logo;
    private Animation appear;
    private Animation uptodown;
    private UserProfileManager userProfileManager;
    private AVLoadingIndicatorView AV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        AV = (AVLoadingIndicatorView) findViewById(R.id.AVloading);
        AV.hide();

        userProfileManager = new UserProfileManager(SplashActivity.this);

        logo = (ImageView) findViewById(R.id.logo);
        appear = AnimationUtils.loadAnimation(this, R.anim.appear);
        uptodown = AnimationUtils.loadAnimation(this, R.anim.uptodown);
        logo.setAnimation(appear);
        logo.setAnimation(uptodown);

        Thread timerAV = new Thread() {
            public void run() {
                try {
                    sleep(1000);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    AV.show();
                }
            }
        };

        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(4000);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    userProfileManager.checkUser();
                }
            }
        };

        timerAV.start();
        timer.start();
    }
}
