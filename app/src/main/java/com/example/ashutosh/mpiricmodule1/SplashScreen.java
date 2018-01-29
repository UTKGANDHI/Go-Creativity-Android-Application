package com.example.ashutosh.mpiricmodule1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.Target;


public class SplashScreen extends Activity
{
    SharedPreferences sh1;
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }
    /** Called when the activity is first created. */
    Thread splashTread;
    String gifurl="http://kamleshyadav.com/scripts/themeportal/webimage/preloader.gif";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        checkforLogin();

    }
    public void checkforLogin(){
        sh1= getSharedPreferences("LoginAuth", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sh1.edit();
        Boolean log=sh1.getBoolean("LoginAuth",false);

        if(log==false){
//            Intent i1=new Intent(SplashScreen.this,LoginActivity.class);
//            startActivity(i1);
            StartAnimations();
        }
        else {
            Intent i1=new Intent(SplashScreen.this,Home.class);
           startActivity(i1);
        }

    }

    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        RelativeLayout l=(RelativeLayout) findViewById(R.id.lin_lay);
        l.clearAnimation();

        l.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        ImageView iv = (ImageView) findViewById(R.id.splash);
        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(iv);
        iv.clearAnimation();
        iv.startAnimation(anim);

        Glide.with(getApplicationContext()).load(R.raw.preloader).asGif().priority(Priority.IMMEDIATE).crossFade()
      .diskCacheStrategy(DiskCacheStrategy.SOURCE).animate(android.R.anim.fade_in).into(iv);

        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    // Splash screen pause time

                    while (waited < 4500) {
                        sleep(100);
                        waited += 100;
                    }
                    Intent intent = new Intent(SplashScreen.this,
                            LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    SplashScreen.this.finish();
                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    SplashScreen.this.finish();
                }

            }
        };
        splashTread.start();

    }

}