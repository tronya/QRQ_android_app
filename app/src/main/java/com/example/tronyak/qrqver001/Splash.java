package com.example.tronyak.qrqver001;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Window;
import android.view.WindowManager;

import com.androidquery.AQuery;


/**
 * Created by Yura on 01.07.2015.
 */
public class Splash extends Activity{


    AQuery aq = new AQuery(this);
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.splash);

        aq.id(R.id.find_me).image(R.mipmap.find_me_can).animate(R.anim.bounce);
        aq.id(R.id.splas_logo).image(R.drawable.logo).animate(R.anim.fade_in);

        Thread welcomeThread = new Thread() {

            @Override
            public void run() {
                try {
                    super.run();
                    sleep(3000);  //Delay of 10 seconds
                } catch (Exception e) {

                } finally {

                    Intent i = new Intent(Splash.this,
                            MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        };
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        long[] pattern = {0, 100, 100};
            if(v.hasVibrator()) {
                v.vibrate(pattern, -1);
            }
        welcomeThread.start();
    }
}
